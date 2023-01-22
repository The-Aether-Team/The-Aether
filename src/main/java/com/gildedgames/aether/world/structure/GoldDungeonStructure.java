package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.GoldDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class GoldDungeonStructure extends Structure {
    public static final Codec<GoldDungeonStructure> CODEC = simpleCodec(GoldDungeonStructure::new);

    public GoldDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), 128, chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder) -> generatePieces(piecesBuilder, context)));
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        RandomSource randomSource = context.random();
        BlockPos worldPos = context.chunkPos().getWorldPosition();
        BlockPos elevatedPos = new BlockPos(worldPos.getX(), 80, worldPos.getZ());
        StructureTemplateManager templateManager = context.structureTemplateManager();

        GoldDungeonPieces.BossRoom bossRoom = new GoldDungeonPieces.BossRoom(
                templateManager,
                new ResourceLocation(Aether.MODID, "gold_dungeon/boss_room"),
                elevatedPos,
                randomSource
        );
        builder.addPiece(bossRoom);

        /*GoldDungeonPieces.Island mainIsland = new GoldDungeonPieces.Island(
                templateManager,
                new ResourceLocation(Aether.MODID, "gold_dungeon/island"),
                elevatedPos
        );*/

        /*GoldDungeonPieces.LegacyIslandPiece legacyPiece = new GoldDungeonPieces.LegacyIslandPiece(BoundingBox.fromCorners(elevatedPos.offset(-24, -24, -24), elevatedPos.offset(24, 24, 24)));
        builder.addPiece(legacyPiece);*/
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
