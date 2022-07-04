package com.gildedgames.aether.world.feature;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.configuration.AercloudConfiguration;
import com.gildedgames.aether.world.configuration.AetherLakeConfiguration;
import com.gildedgames.aether.world.configuration.SimpleDiskConfiguration;
import com.gildedgames.aether.world.feature.AercloudFeature;
import com.gildedgames.aether.world.feature.AetherLakeFeature;
import com.gildedgames.aether.world.feature.CrystalIslandFeature;
import com.gildedgames.aether.world.feature.SimpleDiskFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Aether.MODID);

    public static RegistryObject<Feature<SimpleDiskConfiguration>> SIMPLE_DISK = FEATURES.register("simple_disk", () -> new SimpleDiskFeature(SimpleDiskConfiguration.CODEC));
    public static RegistryObject<Feature<AercloudConfiguration>> AERCLOUD = FEATURES.register("aercloud", () -> new AercloudFeature(AercloudConfiguration.CODEC));
    public static RegistryObject<Feature<NoneFeatureConfiguration>> CRYSTAL_ISLAND = FEATURES.register("crystal_island", () -> new CrystalIslandFeature(NoneFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<AetherLakeConfiguration>> LAKE = FEATURES.register("lake", () -> new AetherLakeFeature(AetherLakeConfiguration.CODEC));

    //public static Feature<NoneFeatureConfiguration> HOLYSTONE_SPHERE = new HolystoneSphereFeature(NoneFeatureConfiguration.CODEC); // This is for Gold Dungeons
}