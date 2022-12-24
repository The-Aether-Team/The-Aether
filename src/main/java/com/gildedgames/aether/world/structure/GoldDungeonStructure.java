package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.structurepiece.GoldDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class GoldDungeonStructure extends Structure {
    public static final Codec<GoldDungeonStructure> CODEC = simpleCodec(GoldDungeonStructure::new);

    public GoldDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), 128, chunkpos.getMinBlockZ());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder) -> generatePieces(piecesBuilder, context)));
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        RandomSource randomSource = context.random();
        BlockPos worldPos = context.chunkPos().getWorldPosition();
        BlockPos elevatedPos = new BlockPos(worldPos.getX(), 128, worldPos.getZ());
        ResourceLocation room = new ResourceLocation(Aether.MODID, "gold_dungeon/boss_room");

        Rotation rotation = Rotation.getRandom(randomSource);

        GoldDungeonPieces.BossRoom bossRoom = new GoldDungeonPieces.BossRoom(
                context.structureTemplateManager(),
                room,
                elevatedPos,
                randomSource,
                rotation
        );

        GoldDungeonPieces.MainIsland mainIsland = new GoldDungeonPieces.MainIsland(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID,"gold_dungeon/main_island"),
                elevatedPos.below(19).relative(rotation.rotate(Direction.WEST), 10).relative(rotation.rotate(Direction.NORTH), 11),
                randomSource,
                rotation
        );

        builder.addPiece(mainIsland);
        for (int i = 0; i < randomSource.nextInt(3) + 4; i++) {
            GoldDungeonPieces.SmallIsland smallIsland = new GoldDungeonPieces.SmallIsland(
                    context.structureTemplateManager(),
                    new ResourceLocation(Aether.MODID,"gold_dungeon/small_island"),
                    elevatedPos.west(randomSource.nextInt(32)-16).north(randomSource.nextInt(32)-16).below(randomSource.nextInt(32)-16),
                    randomSource,
                    rotation
            );
            builder.addPiece(smallIsland);
        }
        builder.addPiece(bossRoom);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
