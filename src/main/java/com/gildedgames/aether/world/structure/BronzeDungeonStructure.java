package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.BronzeDungeonPieces;
import com.gildedgames.aether.world.structurepiece.SilverDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class BronzeDungeonStructure extends Structure {
    public static final Codec<BronzeDungeonStructure> CODEC = simpleCodec(BronzeDungeonStructure::new);
    public BronzeDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        RandomSource random = context.random();
        BlockPos blockPos = new BlockPos(context.chunkPos().getMiddleBlockX(), 60, context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockPos, (builder -> generatePieces(builder, context))));
    }

    private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
        RandomSource randomSource = context.random();
        BlockPos worldPos = context.chunkPos().getWorldPosition();
        BlockPos elevatedPos = new BlockPos(worldPos.getX(), 50, worldPos.getZ());
        BronzeDungeonPieces.BossRoom bossRoom = new BronzeDungeonPieces.BossRoom(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID, "bronze_dungeon/boss_room"),
                elevatedPos,
                randomSource
        );
        builder.addPiece(bossRoom);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.BRONZE_DUNGEON.get();
    }
}
