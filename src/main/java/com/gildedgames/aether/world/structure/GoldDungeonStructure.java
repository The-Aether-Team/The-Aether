package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.GoldDungeonPieces;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
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
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), 128, chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, piecesBuilder -> this.generatePieces(piecesBuilder, context)));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        RandomSource random = context.random();
        BlockPos elevatedPos = context.chunkPos().getBlockAt(2, 128, 2);
        StructureTemplateManager templateManager = context.structureTemplateManager();

        GoldDungeonPieces.Island island = new GoldDungeonPieces.Island(
                templateManager,
                "island",
                elevatedPos
        );
        builder.addPiece(island);

        BlockPos centerPos = island.getBoundingBox().getCenter();
        Vec3i stubOffset = this.getStubOffset(templateManager);
        this.addIslandStubs(templateManager, builder, random, centerPos, stubOffset);

        /*GoldDungeonPieces.BossRoom bossRoom = new GoldDungeonPieces.BossRoom(
                templateManager,
                "boss_room",
                elevatedPos,
//                Rotation.getRandom(random)
                Rotation.NONE
        );*/
//        builder.addPiece(bossRoom);
    }

    private void addIslandStubs(StructureTemplateManager templateManager, StructurePiecesBuilder builder, RandomSource random, BlockPos center, Vec3i stubOffset) {
        int stubCount = this.stubIslandCount + random.nextInt(5);

        for (int stubIslands = 0; stubIslands < stubCount; ++stubIslands) {
            float angle = random.nextFloat() * 360F * Mth.DEG_TO_RAD;
            float distance = ((random.nextFloat() * 0.125F) + 0.7F) * 24.0F;

            int xOffset = Mth.floor(Math.cos(angle) * distance);
            int yOffset = -Mth.floor(24.0D * random.nextFloat() * 0.3D);
            int zOffset = Mth.floor(-Math.sin(angle) * distance);

            BlockPos stubPos = center.offset(xOffset, yOffset, zOffset);
            stubPos = stubPos.offset(stubOffset);
            GoldDungeonPieces.Stub stub = new GoldDungeonPieces.Stub(
                    templateManager,
                    "stub",
                    stubPos
            );
            builder.addPiece(stub);

        }
    }

    private Vec3i getStubOffset(StructureTemplateManager templateManager) {
        StructureTemplate template = templateManager.getOrCreate(new ResourceLocation(Aether.MODID, "gold_dungeon/stub"));
        Vec3i size = template.getSize();
        return new Vec3i(size.getX() / -2, size.getY() / -2, size.getZ() / -2);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
