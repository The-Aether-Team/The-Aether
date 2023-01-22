package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.GoldDungeonPieces;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
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
        BlockPos elevatedPos = context.chunkPos().getBlockAt(2, 80, 2);
        StructureTemplateManager templateManager = context.structureTemplateManager();

        GoldDungeonPieces.LegacyIslandPiece legacyPiece = new GoldDungeonPieces.LegacyIslandPiece(BoundingBox.fromCorners(elevatedPos.offset(-24, -24, -24), elevatedPos.offset(24, 24, 24)));
        builder.addPiece(legacyPiece);

        int stubCount = this.stubIslandCount + random.nextInt(5);
        for (int stubIslands = 0; stubIslands < stubCount; ++stubIslands) {
            float angle = random.nextFloat() * 360F * Mth.DEG_TO_RAD;
            float distance = ((random.nextFloat() * 0.125F) + 0.7F) * 24.0F;

            int xOffset = Mth.floor(Math.cos(angle) * (double)distance);
            int yOffset = -Mth.floor(24.0D * (double)random.nextFloat() * 0.3D);
            int zOffset = Mth.floor(-Math.sin(angle) * (double)distance);

            BlockPos stubPos = elevatedPos.offset(xOffset, yOffset, zOffset);

            GoldDungeonPieces.LegacyStubPiece stub = new GoldDungeonPieces.LegacyStubPiece(new BoundingBox(stubPos).inflatedBy(8));
            builder.addPiece(stub);

            //this.generateStubIsland(l4, k5, i6, 8);
//            builder.addPiece(new ComponentGoldenIslandStub((chunkX << 4) + 2, (chunkZ << 4) + 2, l4, k5, i6, 8));
        }

        /*GoldDungeonPieces.BossRoom bossRoom = new GoldDungeonPieces.BossRoom(
                templateManager,
                new ResourceLocation(Aether.MODID, "gold_dungeon/boss_room"),
                elevatedPos,
                random
        );
        builder.addPiece(bossRoom);*/
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
