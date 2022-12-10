package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.resources.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class AetherWorldGenData extends DatapackBuiltinEntriesProvider {
    private static final HolderLookup.Provider VANILLA = VanillaRegistries.createLookup();
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, AetherConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, AetherPlacedFeatures::bootstrap)
            .add(Registries.BIOME, (context) -> AetherBiomes.bootstrap(context, VANILLA))
            .add(Registries.NOISE_SETTINGS, (context) -> AetherNoiseSettings.bootstrap(context, VANILLA))
            .add(Registries.DIMENSION_TYPE, AetherDimensions::bootstrap);
            //.add(Registries.STRUCTURE, AetherStructures::bootstrap)
            //.add(Registries.STRUCTURE_SET, AetherStructureSets::bootstrap);

    public AetherWorldGenData(PackOutput output) {
        super(output, BUILDER);
    }
}
