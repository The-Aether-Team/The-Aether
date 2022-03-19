package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.world.biome.AetherBiomeKeys;
import com.gildedgames.aether.common.world.feature.FeatureBuilders;
import com.gildedgames.aether.common.world.biome.AetherBiomeBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class AetherBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Aether.MODID);

    // No fancy variations with trees are required, so inline these different tree decoration patterns instead
    public static final RegistryObject<Biome> SKYROOT_GROVE = register(AetherBiomeKeys.SKYROOT_GROVE, () -> AetherBiomeBuilder.makeDefaultBiome(new BiomeGenerationSettings.Builder()
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2)))
    ));
    public static final RegistryObject<Biome> SKYROOT_FOREST = register(AetherBiomeKeys.SKYROOT_FOREST, () -> AetherBiomeBuilder.makeDefaultBiome(new BiomeGenerationSettings.Builder()
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2)))
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                    CountOnEveryLayerPlacement.of(1),
                    FeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get())
            ))))
    ));
    public static final RegistryObject<Biome> SKYROOT_THICKET = register(AetherBiomeKeys.SKYROOT_THICKET, () -> AetherBiomeBuilder.makeDefaultBiome(new BiomeGenerationSettings.Builder()
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                    CountOnEveryLayerPlacement.of(1),
                    FeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get())
            ))))
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(3)))
    ));
    public static final RegistryObject<Biome> GOLDEN_FOREST = register(AetherBiomeKeys.GOLDEN_FOREST, () -> AetherBiomeBuilder.makeDefaultBiome(0xb1_ff_cb, new BiomeGenerationSettings.Builder()
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE), List.of(
                    CountOnEveryLayerPlacement.of(2),
                    FeatureBuilders.copyBlockSurvivability(AetherBlocks.GOLDEN_OAK_SAPLING.get())
            ))))
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2)))
    ));

    private static RegistryObject<Biome> register(ResourceKey<Biome> biomeResourceKey, Supplier<Biome> biome) {
        return BIOMES.register(biomeResourceKey.location().getPath(), biome);
    }
}
