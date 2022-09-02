package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.SilverDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class SilverDungeonStructure extends Structure {
    public static final Codec<SilverDungeonStructure> CODEC = simpleCodec(SilverDungeonStructure::new);
    public SilverDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), 64, chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder) -> generatePieces(piecesBuilder, context)));
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        RandomSource randomSource = context.random();
        BlockPos worldPos = context.chunkPos().getWorldPosition();
        BlockPos elevatedPos = new BlockPos(worldPos.getX(), 64, worldPos.getZ());
        SilverDungeonPieces.BossRoom bossRoom = new SilverDungeonPieces.BossRoom(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID, "silver_dungeon/boss_room"),
                elevatedPos,
                randomSource
        );
        builder.addPiece(bossRoom);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.SILVER_DUNGEON.get();
    }
}
