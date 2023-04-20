package com.aetherteam.aether.data.resources.builders;

import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class AetherPlacedFeatureBuilders {
    public static List<PlacementModifier> aercloudPlacement(int above, int range, int chance) {
        return List.of(
                HeightRangePlacement.uniform(VerticalAnchor.absolute(above), VerticalAnchor.absolute(above + range)),
                RarityFilter.onAverageOnceEvery(chance),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                new DungeonBlacklistFilter());
    }

    /**
     * Copy of {@link net.minecraft.data.worldgen.placement.VegetationPlacements#treePlacement(PlacementModifier)}
     */
    public static List<PlacementModifier> treePlacement(PlacementModifier count) {
        return treePlacementBase(count).build();
    }

    /**
     * Based on {@link net.minecraft.data.worldgen.placement.VegetationPlacements#treePlacementBase(PlacementModifier)}
     */
    private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier count) {
        return ImmutableList.<PlacementModifier>builder()
                .add(count)
                .add(SurfaceWaterDepthFilter.forMaxDepth(0))
                .add(ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, UniformInt.of(0, 1), 4))
                .add(BiomeFilter.biome())
                .add(new DungeonBlacklistFilter());
    }

    /**
     * Copy of {@link net.minecraft.data.worldgen.placement.OrePlacements#commonOrePlacement(int, PlacementModifier)}.
     */
    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    /**
     * Copy of {@link net.minecraft.data.worldgen.placement.OrePlacements#orePlacement(PlacementModifier, PlacementModifier)}.
     */
    private static List<PlacementModifier> orePlacement(PlacementModifier count, PlacementModifier heightRange) {
        return List.of(count, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }
}
