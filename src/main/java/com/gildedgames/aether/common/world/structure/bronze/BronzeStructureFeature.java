package com.gildedgames.aether.common.world.structure.bronze;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.world.gen.configuration.BronzeDungeonConfiguration;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureFeature;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;
import java.util.Optional;

public class BronzeStructureFeature extends NoiseAffectingStructureFeature<BronzeDungeonConfiguration> {
    public static final RuleProcessor SENTRY_PROCESSOR = new RuleProcessor(List.of(new ProcessorRule(new RandomBlockMatchTest(AetherBlocks.CARVED_STONE.get(), 0.1f), AlwaysTrueTest.INSTANCE, AetherBlocks.SENTRY_STONE.get().defaultBlockState())));

    public BronzeStructureFeature() {
        super(BronzeDungeonConfiguration.CODEC, BronzeStructureFeature::placePieces);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    private static boolean shouldPlace(PieceGeneratorSupplier.Context<BronzeDungeonConfiguration> context) {
        return context.chunkGenerator().getFirstOccupiedHeight(context.chunkPos().getMiddleBlockX(), context.chunkPos().getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor()) > 60;
    }

    private static Optional<PieceGenerator<BronzeDungeonConfiguration>> placePieces(PieceGeneratorSupplier.Context<BronzeDungeonConfiguration> placementContext) {
        if (!shouldPlace(placementContext))
            return Optional.empty();

        return Optional.of((pieceBuilder, context) -> pieceBuilder.addPiece(new SliderBossRoom(context.structureManager(), context.config().recursionDepth(), new StructurePlaceSettings(), context.chunkPos().getMiddleBlockPosition(39))));
    }
}
