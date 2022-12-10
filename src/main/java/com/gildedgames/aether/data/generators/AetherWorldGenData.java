package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.resources.*;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class AetherWorldGenData extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, AetherConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, AetherPlacedFeatures::bootstrap)
            .add(Registries.BIOME, AetherBiomes::bootstrap)
            .add(Registries.NOISE_SETTINGS, AetherNoiseSettings::bootstrap)
            .add(Registries.DIMENSION_TYPE, AetherDimensions::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, AetherDimensions::bootstrapLevelStem);
            //.add(Registries.STRUCTURE, AetherStructures::bootstrap)
            //.add(Registries.STRUCTURE_SET, AetherStructureSets::bootstrap);

    public AetherWorldGenData(PackOutput output) {
        super(output, BUILDER);
    }
}
