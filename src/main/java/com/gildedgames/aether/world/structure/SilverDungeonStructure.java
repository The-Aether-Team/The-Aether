package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.gildedgames.aether.world.structurepiece.SilverDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Optional;

public class SilverDungeonStructure extends Structure {
    public static final Codec<SilverDungeonStructure> CODEC = simpleCodec(SilverDungeonStructure::new);
    public SilverDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        int height = context.chunkGenerator().getBaseHeight(chunkpos.getMiddleBlockX(), chunkpos.getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos blockpos = new BlockPos(chunkpos.getMiddleBlockX(), Math.max(height, 30) + 20 + context.random().nextInt(30), chunkpos.getMiddleBlockZ());
        return Optional.of(new GenerationStub(blockpos, (piecesBuilder) -> generatePieces(piecesBuilder, context, blockpos)));
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos elevatedPos) {
        RandomSource randomSource = context.random();
        Rotation rotation = Rotation.getRandom(randomSource);
        Direction direction = rotation.rotate(Direction.SOUTH);
        SilverDungeonPieces.BossRoom bossRoom = new SilverDungeonPieces.BossRoom(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID, "silver_dungeon/back"),
                elevatedPos,
                rotation
        );
        builder.addPiece(bossRoom);
        int xOffset = direction.getStepX() * bossRoom.getBoundingBox().getXSpan();
        int zOffset = direction.getStepZ() * bossRoom.getBoundingBox().getZSpan();
        SilverDungeonPieces.SilverDungeonPiece front = new SilverDungeonPieces.SilverDungeonPiece(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID, "silver_dungeon/front"),
                elevatedPos.offset(xOffset, 0, zOffset),
                rotation
        );
        builder.addPiece(front);
    }

    /**
     * Sets the valkyrie queen's dungeon bounds.
     */
    @Override
    public void afterPlace(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, PiecesContainer piecesContainer) {
        AABB dungeonBounds = new AABB(box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ());
        level.getEntitiesOfClass(ValkyrieQueen.class, dungeonBounds).forEach(valkyrie ->
                valkyrie.setDungeon(new DungeonTracker<>(valkyrie, valkyrie.position(), dungeonBounds, new ArrayList<>()))
        );
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.SILVER_DUNGEON.get();
    }
}
