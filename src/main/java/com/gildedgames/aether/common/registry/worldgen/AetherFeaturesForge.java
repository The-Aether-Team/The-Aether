package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.gen.configuration.AercloudConfiguration;
import com.gildedgames.aether.common.world.gen.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.common.world.gen.feature.AercloudFeature;
import com.gildedgames.aether.common.world.gen.feature.HolystoneSphereFeature;
import com.gildedgames.aether.common.world.gen.feature.SimpleDiskFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherFeaturesForge {
    // Forge Entrypoint
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aether.MODID);

    public static final RegistryObject<Feature<SimpleDiskConfiguration>> SIMPLE_DISK = FEATURES.register("simple_disk", () -> new SimpleDiskFeature(SimpleDiskConfiguration.CODEC));
    public static final RegistryObject<Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HOLYSTONE_SPHERE = FEATURES.register("holystone_sphere", () -> new HolystoneSphereFeature(NoneFeatureConfiguration.CODEC)); // This is for Gold Dungeons
}
