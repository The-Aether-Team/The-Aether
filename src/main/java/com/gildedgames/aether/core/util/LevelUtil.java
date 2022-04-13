package com.gildedgames.aether.core.util;

import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class LevelUtil {
    public static ResourceKey<Level> destinationDimension() { //Default: aether:the_aether
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portalDestinationDimensionID.get()));
    }

    public static ResourceKey<Level> returnDimension() { //Default: minecraft:overworld
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portalReturnDimensionID.get()));
    }

    public static boolean contains(ForgeConfigSpec.ConfigValue<List<String>> config, ResourceKey<Level> dimension) {
        return toResourceKeyArray(config).contains(dimension);
    }

    private static ArrayList<ResourceKey<Level>> toResourceKeyArray(ForgeConfigSpec.ConfigValue<List<String>> locations) {
        ArrayList<ResourceKey<Level>> resourceKeys = new ArrayList<>();
        for (String dimension : locations.get()) {
            ResourceKey<Level> dimensionKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dimension));
            resourceKeys.add(dimensionKey);
        }
        return resourceKeys;
    }
}
