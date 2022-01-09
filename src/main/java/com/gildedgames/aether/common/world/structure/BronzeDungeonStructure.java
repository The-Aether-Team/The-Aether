package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureFeature;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.Optional;

public class BronzeDungeonStructure extends NoiseAffectingStructureFeature<JigsawConfiguration> {
    public BronzeDungeonStructure() {
        super(JigsawConfiguration.CODEC, BronzeDungeonStructure::placePieces);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    private static boolean shouldPlace(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        NoiseColumn column = context.chunkGenerator().getBaseColumn(context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), context.heightAccessor());
        return !column.getBlock(40).isAir() && !column.getBlock(80).isAir();
    }

    private static Optional<PieceGenerator<JigsawConfiguration>> placePieces(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Uncomment below to generate because terrain cannot go above y80 yet FIXME Remove once terrain can go above y80
        //if (false)
        if (!shouldPlace(context))
            return Optional.empty();

        JigsawConfiguration newConfig = new JigsawConfiguration(
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(Aether.MODID, "bronze_dungeon/start_pool")),
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

        return JigsawPlacement.addPieces(newContext, PoolElementStructurePiece::new, context.chunkPos().getMiddleBlockPosition(60), false, false);
    }
}
