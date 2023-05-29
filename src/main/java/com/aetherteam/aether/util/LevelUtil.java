package com.aetherteam.aether.util;

import com.aetherteam.aether.AetherConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class LevelUtil {
    public static ResourceKey<Level> destinationDimension() { // Default: aether:the_aether
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AetherConfig.SERVER.portal_destination_dimension_ID.get()));
    }

    public static ResourceKey<Level> returnDimension() { // Default: minecraft:overworld
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AetherConfig.SERVER.portal_return_dimension_ID.get()));
    }
}
