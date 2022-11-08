package com.gildedgames.aether.world.structure;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.resources.AetherConfiguredFeatures;
import com.gildedgames.aether.world.structurepiece.GoldDungeonPieces;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
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
        RegistryAccess access = context.registryAccess();
        BlockPos elevatedPos = new BlockPos(worldPos.getX(), 128, worldPos.getZ());
        ResourceLocation room = new ResourceLocation(Aether.MODID, "gold_dungeon/boss_room");

        Rotation rotation = Rotation.getRandom(randomSource);

        BoundingBox box = context.structureTemplateManager().get(room).get().getBoundingBox(elevatedPos, rotation, elevatedPos, Mirror.NONE);
        int offsetX = (box.maxX() - box.minX()) / 2;
        int offsetZ = (box.maxZ() - box.minZ()) / 2;

        BlockPos islandPos = elevatedPos.above(24).north(offsetZ).west(offsetX);

        GoldDungeonPieces.Island island = new GoldDungeonPieces.Island(
                AetherConfiguredFeatures.HOLYSTONE_SPHERE_CONFIGURATION,
                access,
                islandPos
        );

        GoldDungeonPieces.BossRoom bossRoom = new GoldDungeonPieces.BossRoom(
                context.structureTemplateManager(),
                room,
                elevatedPos,
                randomSource,
                rotation
        );

        GoldDungeonPieces.TemplateIsland templateIsland = new GoldDungeonPieces.TemplateIsland(
                context.structureTemplateManager(),
                new ResourceLocation(Aether.MODID,"gold_dungeon/main_sphere"),
                elevatedPos.below(19).south(offsetZ).east(offsetX),
                randomSource,
                rotation
        );

        //builder.addPiece(island);
        builder.addPiece(templateIsland);
        builder.addPiece(bossRoom);
    }

    @Override
    public StructureType<?> type() {
        return AetherStructureTypes.GOLD_DUNGEON.get();
    }
}
