package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.resources.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class AetherRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, AetherConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, AetherPlacedFeatures::bootstrap)
            .add(Registries.BIOME, AetherBiomes::bootstrap)
            .add(Registries.DENSITY_FUNCTION, AetherDensityFunctions::bootstrap)
            .add(Registries.NOISE, AetherNoises::bootstrap)
            .add(Registries.NOISE_SETTINGS, AetherNoiseSettings::bootstrap)
            .add(Registries.DIMENSION_TYPE, AetherDimensions::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, AetherDimensions::bootstrapLevelStem)
            .add(Registries.STRUCTURE, AetherStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, AetherStructureSets::bootstrap)
            .add(Registries.DAMAGE_TYPE, AetherDamageTypes::bootstrap)
            .add(Registries.TRIM_MATERIAL, AetherTrimMaterials::bootstrap);

    public AetherRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Collections.singleton(Aether.MODID));
    }

    public static HolderLookup.Provider createLookup() {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
    }
}
