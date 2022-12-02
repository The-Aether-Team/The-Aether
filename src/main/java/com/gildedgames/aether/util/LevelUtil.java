package com.gildedgames.aether.util;

import com.gildedgames.aether.AetherConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class LevelUtil {
    public static ResourceKey<Level> destinationDimension() { // Default: aether:the_aether
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portal_destination_dimension_ID.get()));
    }

    public static ResourceKey<Level> returnDimension() { // Default: minecraft:overworld
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AetherConfig.COMMON.portal_return_dimension_ID.get()));
    }
}
