package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.data.resources.registries.AetherConfiguredFeatures;
import com.gildedgames.aether.util.BlockLogicUtil;
import com.gildedgames.aether.world.structurepiece.golddungeon.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class GoldDungeonStructure extends Structure {
    public static final Codec<GoldDungeonStructure> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            settingsCodec(builder),
            Codec.INT.fieldOf("stubcount").forGetter(o -> o.stubIslandCount)
    ).apply(builder, GoldDungeonStructure::new));

    private final int stubIslandCount;

    public GoldDungeonStructure(StructureSettings settings, int stubIslandCount) {
        super(settings);
        this.stubIslandCount = stubIslandCount;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        RandomSource random = context.random();
        ChunkPos chunkpos = context.chunkPos();
        int x = chunkpos.getMiddleBlockX();
        int z = chunkpos.getMiddleBlockZ();
        int terrainHeight = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()) - 20;
        int height = 40 + random.nextInt(60);
        height = Math.max(terrainHeight, height);
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), height, chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, piecesBuilder -> this.generatePieces(piecesBuilder, context, blockpos)));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos elevatedPos) {
        RandomSource random = context.random();
        StructureTemplateManager templateManager = context.structureTemplateManager();

        GoldIsland island = new GoldIsland(
                templateManager,
                "island",
                elevatedPos
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
                rotation
        );
        this.tunnelFromBossRoom(templateManager, builder, bossRoom);
        builder.addPiece(bossRoom);

    }

    /**
     * Decorate the island with smaller spheres on the edges.
     */
    private void addIslandStubs(StructureTemplateManager templateManager, StructurePiecesBuilder builder, RandomSource random, BlockPos center, Vec3i stubOffset) {
        int stubCount = this.stubIslandCount + random.nextInt(5);

        for (int stubIslands = 0; stubIslands < stubCount; ++stubIslands) {
            float angle = random.nextFloat() * Mth.TWO_PI;
            float distance = ((random.nextFloat() * 0.125F) + 0.7F) * 24.0F;

            int xOffset = Mth.floor(Math.cos(angle) * distance);
            int yOffset = -Mth.floor(24.0D * random.nextFloat() * 0.3D);
            int zOffset = Mth.floor(-Math.sin(angle) * distance);

            BlockPos stubPos = center.offset(xOffset, yOffset, zOffset);
            stubPos = stubPos.offset(stubOffset);
            GoldStub stub = new GoldStub(
                    templateManager,
                    "stub",
                    stubPos
            );
            builder.addPiece(stub);

        }
    }

    /**
     * Place small caves around the island.
     */
    private void placeGumdropCaves(StructurePiecesBuilder builder, RandomSource random, BlockPos centerPos) {
        for(int count = 0; count < 18; ++count) {
            int x = centerPos.getX() + random.nextInt(24) - random.nextInt(24);
            int y = centerPos.getY() + random.nextInt(24) - random.nextInt(24);
            int z = centerPos.getZ() + random.nextInt(24) - random.nextInt(24);
            builder.addPiece(new GumdropCave(new BoundingBox(new BlockPos(x, y, z))));
        }
    }

    /**
     * Place the tunnel so that it connects to the boss room's door.
     */
    private void tunnelFromBossRoom(StructureTemplateManager templateManager, StructurePiecesBuilder builder, StructurePiece room) {
        StructureTemplate template = templateManager.getOrCreate(new ResourceLocation(Aether.MODID, "gold_dungeon/tunnel"));
        int width = template.getSize().getX();
        Rotation rotation = room.getRotation();
        Direction direction = rotation.rotate(Direction.SOUTH);

        BlockPos startPos = BlockLogicUtil.tunnelFromOddSquareRoom(room.getBoundingBox(), direction, width);
        startPos = startPos.offset(direction.getStepX() * 3, 1, direction.getStepZ() * 3);
        GoldTunnel tunnel = new GoldTunnel(templateManager, "tunnel", startPos, rotation);
        builder.addPiece(tunnel);
    }

    private Vec3i getStubOffset(StructureTemplateManager templateManager) {
        StructureTemplate template = templateManager.getOrCreate(new ResourceLocation(Aether.MODID, "gold_dungeon/stub"));
        Vec3i size = template.getSize();
        return new Vec3i(size.getX() / -2, size.getY() / -2, size.getZ() / -2);
    }

    private Vec3i getBossRoomOffset(StructureTemplateManager templateManager, Direction direction) {
        StructureTemplate template = templateManager.getOrCreate(new ResourceLocation(Aether.MODID, "gold_dungeon/boss_room"));
        Vec3i size = template.getSize();
        Vec3i offset = new Vec3i(size.getX() / -2, size.getY() / -2, (size.getZ()) / -2);
        return offset.relative(direction, -1);
    }

    /**
     * Place the trees after caves have been generated
     */
    @Override
    public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, PiecesContainer pieces) {
        for (StructurePiece piece : pieces.pieces()) {
            if (piece instanceof GoldIsland island) {
                placeGoldenOaks(level, generator, random, island.getBoundingBox(), chunkBox, 48, 2, 1);
            } else if (piece instanceof GoldStub stub) {
                placeGoldenOaks(level, generator, random, stub.getBoundingBox(), chunkBox, 64, 1, 0);
            }
        }
    }

    /**
     * Randomly place golden oak trees and flowers on top of a structure piece.
     * @param boundingBox - The structure piece's bounding box
     * @param chunkBox - The current chunk's bounding box
     * @param randomBounds - The parameter for random.nextInt()
     * @param treeWeight - The chance out of randomBounds of placing a tree
     * @param flowerWeight - The chance out of randomBounds of placing a flower
     */
    private static void placeGoldenOaks(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BoundingBox boundingBox, BoundingBox chunkBox, int randomBounds, int treeWeight, int flowerWeight) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int minX = Math.max(chunkBox.minX(), boundingBox.minX());
        int minZ = Math.max(chunkBox.minZ(), boundingBox.minZ());
        int maxX = Math.min(chunkBox.maxX(), boundingBox.maxX());
        int maxZ = Math.min(chunkBox.maxZ(), boundingBox.maxZ());
        int minY = boundingBox.minY() + Mth.floor((boundingBox.maxY() - boundingBox.minY()) * 0.75);
        int maxY = boundingBox.maxY();

        for (int x = minX; x < maxX; ++x) {
            for (int z = minZ; z < maxZ; ++z) {
                int featureType = random.nextInt(randomBounds);
                if (featureType < treeWeight + flowerWeight) {
                    mutable.set(x, maxY, z);
                    if (iterateColumn(level, mutable, minY, maxY)) {
                        if (featureType < treeWeight) {
                            PlacedFeature tree = PlacementUtils.inlinePlaced(level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolderOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION)).get();
                            tree.place(level, generator, random, mutable);
                        } else {
                            Block flower = random.nextBoolean() ? Blocks.DANDELION : Blocks.POPPY;
                            level.setBlock(mutable, flower.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns true if there is a solid block in the column. MutableBlockPos is set to the first empty block.
     * @param level - The level to check for blocks.
     * @param pos - This MutableBlockPos is set to the first empty block in the column.
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
