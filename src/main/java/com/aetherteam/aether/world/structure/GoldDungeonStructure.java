package com.aetherteam.aether.world.structure;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.world.BlockLogicUtil;
import com.aetherteam.aether.world.structurepiece.golddungeon.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import javax.xml.catalog.CatalogFeatures.Feature;
import java.util.Optional;

public class GoldDungeonStructure extends Structure {
    public static final MapCodec<GoldDungeonStructure> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("stubcount").forGetter(o -> o.stubIslandCount),
            Codec.INT.fieldOf("belowTerrain").forGetter(o -> o.belowTerrain),
            Codec.INT.fieldOf("minY").forGetter(o -> o.minY),
            Codec.INT.fieldOf("rangeY").forGetter(o -> o.rangeY),
            PlacedFeature.CODEC.fieldOf("islandFoliage").forGetter(p_204928_ -> p_204928_.islandFoliage),
            PlacedFeature.CODEC.fieldOf("stubFoliage").forGetter(p_204928_ -> p_204928_.stubFoliage),
            GoldProcessorSettings.CODEC.fieldOf("processor_settings").forGetter(o -> o.processors)
        ).apply(builder, GoldDungeonStructure::new));

    private final int stubIslandCount;
    private final int belowTerrain;
    private final int minY;
    private final int rangeY;
    private final Holder<PlacedFeature> islandFoliage;
    private final Holder<PlacedFeature> stubFoliage;
    private final GoldProcessorSettings processors;

    public GoldDungeonStructure(StructureSettings settings, int stubIslandCount, int belowTerrain, int minY, int rangeY, Holder<PlacedFeature> islandFoliage, Holder<PlacedFeature> stubFoliage, GoldProcessorSettings processors) {
        super(settings);
        this.stubIslandCount = stubIslandCount;
        this.belowTerrain = belowTerrain;
        this.minY = minY;
        this.rangeY = rangeY;
        this.islandFoliage = islandFoliage;
        this.stubFoliage = stubFoliage;
        this.processors = processors;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        RandomSource random = context.random();
        ChunkPos chunkpos = context.chunkPos();
        int x = chunkpos.getMiddleBlockX();
        int z = chunkpos.getMiddleBlockZ();
        // We want the Gold Dungeon to sometimes blend in with the terrain, and sometimes be floating in the sky. However, we never want it to be fully buried.
        int terrainHeight = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()) - this.belowTerrain;
        int height = this.minY + random.nextInt(this.rangeY);
        height = Math.max(terrainHeight, height);
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), height, chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, piecesBuilder -> this.generatePieces(piecesBuilder, context, blockpos)));
    }

    /**
     * Warning for "deprecation" is suppressed because vanilla uses {@link StructurePiecesBuilder#offsetPiecesVertically(int)} just fine.
     */
    @SuppressWarnings("deprecation")
    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos elevatedPos) {
        RandomSource random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();

        GoldIsland island = new GoldIsland(
                templateManager,
                "island",
                elevatedPos,
                this.processors.islandSettings()
        );
        builder.addPiece(island);

        BlockPos centerPos = island.getBoundingBox().getCenter();
        Vec3i stubOffset = this.getStubOffset(templateManager);
        this.addIslandStubs(templateManager, builder, random, centerPos, stubOffset);
        this.placeGumdropCaves(builder, random, centerPos);

        Rotation rotation = Rotation.getRandom(random);
        BlockPos bossPos = centerPos.offset(this.getBossRoomOffset(templateManager, rotation.rotate(Direction.SOUTH)));
        GoldBossRoom bossRoom = new GoldBossRoom(
                templateManager,
                "boss_room",
                bossPos,
                rotation,
                this.processors.bossSettings()
        );
        // We need the tunnel to always be exposed.
        int verticalOffset = this.tunnelFromBossRoom(templateManager, builder, bossRoom, context.chunkGenerator(), context.heightAccessor(), context.randomState());
        builder.addPiece(bossRoom);
        if (verticalOffset > 0) {
            builder.offsetPiecesVertically(verticalOffset);
        }
    }

    /**
     * Decorate the island with smaller spheres on the edges.
     *
     * @param templateManager The {@link StructureTemplateManager}.
     * @param builder         The {@link StructurePiecesBuilder}.
     * @param random          The {@link RandomSource} for the structure.
     * @param center          The center {@link BlockPos} to place from.
     * @param stubOffset      The {@link Vec3i} offset for the stub piece.
     */
    private void addIslandStubs(StructureTemplateManager templateManager, StructurePiecesBuilder builder, RandomSource random, BlockPos center, Vec3i stubOffset) {
        int stubCount = this.stubIslandCount + random.nextInt(5);

        for (int stubIslands = 0; stubIslands < stubCount; ++stubIslands) {
            float angle = random.nextFloat() * Mth.TWO_PI;
            float distance = ((random.nextFloat() * 0.125F) + 0.7F) * 24.0F;

            int xOffset = Mth.floor(Math.cos(angle) * distance);
            int yOffset = -Mth.floor(24.0 * random.nextFloat() * 0.3);
            int zOffset = Mth.floor(-Math.sin(angle) * distance);

            BlockPos stubPos = center.offset(xOffset, yOffset, zOffset);
            stubPos = stubPos.offset(stubOffset);
            GoldStub stub = new GoldStub(
                    templateManager,
                    "stub",
                    stubPos,
                    this.processors.islandSettings()
            );
            builder.addPiece(stub);
        }
    }

    /**
     * Place small caves around the island.
     *
     * @param builder The {@link StructurePiecesBuilder}.
     * @param random  The {@link RandomSource} for the structure.
     * @param center  The center {@link BlockPos} to place from.
     */
    private void placeGumdropCaves(StructurePiecesBuilder builder, RandomSource random, BlockPos center) {
        for (int count = 0; count < 18; ++count) {
            int x = center.getX() + random.nextInt(24) - random.nextInt(24);
            int y = center.getY() + random.nextInt(24) - random.nextInt(24);
            int z = center.getZ() + random.nextInt(24) - random.nextInt(24);
            builder.addPiece(new GoldStubCave(new BoundingBox(new BlockPos(x, y, z))));
        }
    }

    /**
     * Place the tunnel so that it connects to the boss room's door.
     * Returns the difference between the height of the world's surface and the tunnel.
     *
     * @param templateManager The {@link StructureTemplateManager}.
     * @param builder         The {@link StructurePiecesBuilder}.
     * @param room            The {@link StructurePiece} for the boss room.
     * @param chunkGenerator  The {@link ChunkGenerator} for generation.
     * @param heightAccessor  The {@link LevelHeightAccessor} to place in.
     * @param randomState     The {@link RandomState} for the structure.
     * @return The {@link Integer} for the first available height to place at.
     */
    private int tunnelFromBossRoom(StructureTemplateManager templateManager, StructurePiecesBuilder builder, StructurePiece room, ChunkGenerator chunkGenerator, LevelHeightAccessor heightAccessor, RandomState randomState) {
        StructureTemplate template = templateManager.getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold_dungeon/tunnel"));
        int width = template.getSize().getX();
        Rotation rotation = room.getRotation();
        Direction direction = rotation.rotate(Direction.SOUTH);

        BlockPos startPos = BlockLogicUtil.tunnelFromOddSquareRoom(room.getBoundingBox(), direction, width);
        startPos = startPos.offset(direction.getStepX() * 3, 1, direction.getStepZ() * 3);
        GoldTunnel tunnel = new GoldTunnel(templateManager, "tunnel", startPos, rotation, this.processors.tunnelSettings());
        builder.addPiece(tunnel);
        BlockPos endPos = BlockLogicUtil.tunnelFromEvenSquareRoom(tunnel.getBoundingBox(), direction, tunnel.template().getSize().getX());

        return chunkGenerator.getFirstFreeHeight(endPos.getX(), endPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, heightAccessor, randomState) - startPos.getY();
    }

    private Vec3i getStubOffset(StructureTemplateManager templateManager) {
        StructureTemplate template = templateManager.getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold_dungeon/stub"));
        Vec3i size = template.getSize();
        return new Vec3i(size.getX() / -2, size.getY() / -2, size.getZ() / -2);
    }

    private Vec3i getBossRoomOffset(StructureTemplateManager templateManager, Direction direction) {
        StructureTemplate template = templateManager.getOrCreate(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "gold_dungeon/boss_room"));
        Vec3i size = template.getSize();
        Vec3i offset = new Vec3i(size.getX() / -2, size.getY() / -2, (size.getZ()) / -2);
        return offset.relative(direction, -1);
    }

    /**
     * Place the trees after caves have been generated
     *
     * @param level            The {@link WorldGenLevel} to place in.
     * @param structureManager The {@link StructureManager}.
     * @param generator        The {@link ChunkGenerator} for generation.
     * @param random           The {@link RandomSource} for the structure.
     * @param chunkBox         The {@link BoundingBox} for chunk bounds.
     * @param chunkPos         The {@link ChunkPos}.
     * @param pieces           The {@link PiecesContainer} holding structure pieces.
     */
    @Override
    public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, PiecesContainer pieces) {
        for (StructurePiece piece : pieces.pieces()) {
            if (piece instanceof GoldIsland island) {
                GoldDungeonStructure.placeGoldenOaks(level, generator, random, island.getBoundingBox(), chunkBox, this.islandFoliage);
            } else if (piece instanceof GoldStub stub) {
                GoldDungeonStructure.placeGoldenOaks(level, generator, random, stub.getBoundingBox(), chunkBox, this.stubFoliage);
            }
        }
    }

    /**
     * Randomly place Golden Oak trees and flowers on top of a structure piece.
     *
     * @param level        The {@link WorldGenLevel} to place in.
     * @param generator    The {@link ChunkGenerator} for generation.
     * @param random       The {@link RandomSource} for the structure.
     * @param boundingBox  The {@link BoundingBox} for the structure piece.
     * @param chunkBox     The {@link BoundingBox} for chunk bounds.
     * @param feature      The {@link PlacedFeature} holder for the vegetation to place.
     */
    private static void placeGoldenOaks(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BoundingBox boundingBox, BoundingBox chunkBox, Holder<PlacedFeature> feature) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int minX = Math.max(chunkBox.minX(), boundingBox.minX());
        int minZ = Math.max(chunkBox.minZ(), boundingBox.minZ());
        int maxX = Math.min(chunkBox.maxX(), boundingBox.maxX());
        int maxZ = Math.min(chunkBox.maxZ(), boundingBox.maxZ());
        int minY = boundingBox.minY() + Mth.floor((boundingBox.maxY() - boundingBox.minY()) * 0.75);
        int maxY = boundingBox.maxY();
        PlacedFeature placement = feature.value();

        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                mutable.set(x, maxY, z);
                if (GoldDungeonStructure.iterateColumn(level, mutable, minY, maxY)) {
                    placement.place(level, generator, random, mutable);
                }
            }
        }
    }

    /**
     * Returns true if there is a solid block in the column. MutableBlockPos is set to the first empty block.
     *
     * @param level The {@link WorldGenLevel} to check for blocks.
     * @param pos   This {@link net.minecraft.core.BlockPos.MutableBlockPos} is set to the first empty block in the column.
     * @param minY  The minimum {@link Integer} y-level for the column.
     * @param maxY  The maximum {@link Integer} y-level for the column.
     * @return Whether a dirt position was found in the column, as a {@link Boolean}.
     */
    private static boolean iterateColumn(WorldGenLevel level, BlockPos.MutableBlockPos pos, int minY, int maxY) {
        int y;
        for (y = maxY; y > minY; --y) {
            pos.setY(y);
            if (level.getBlockState(pos).is(AetherTags.Blocks.AETHER_DIRT)) {
                pos.setY(++y);
                return true;
            }
        }
        return false;
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
