package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.Optional;
import java.util.Random;

public class GoldDungeonStructure extends NoiseAffectingStructureFeature<JigsawConfiguration> {
    public GoldDungeonStructure() {
        super(JigsawConfiguration.CODEC, GoldDungeonStructure::placePieces);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static Optional<PieceGenerator<JigsawConfiguration>> placePieces(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        JigsawConfiguration newConfig = new JigsawConfiguration(
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(Aether.MODID, "gold_dungeon/start_pool")),
                10
        );

        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.heightAccessor(),
                context.validBiome(),
                context.structureManager(),
                context.registryAccess()
        );

        return JigsawPlacement.addPieces(newContext, PoolElementStructurePiece::new, context.chunkPos().getMiddleBlockPosition(120 + new Random(context.seed()).nextInt(30)), false, false);
    }
}