package com.aetherteam.aether.world;

import com.aetherteam.aether.AetherConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class LevelUtil {
    /**
     * Used to determine a destination dimension for Aether-related teleportation. By default, this is "aether:the_aether".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherConfig.Server#portal_destination_dimension_ID}.
     * @see com.aetherteam.aether.block.portal.AetherPortalBlock
     * @see com.aetherteam.aether.block.portal.AetherPortalForcer
     * @see com.aetherteam.aether.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> destinationDimension() {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace(AetherConfig.SERVER.portal_destination_dimension_ID.get()));
    }

    /**
     * Used to determine a return dimension for Aether-related teleportation. By default, this is "minecraft:overworld".
     *
     * @return A {@link ResourceKey ResourceKey&lt;Level&gt;} retrieved from {@link AetherConfig.Server#portal_return_dimension_ID}.
     * @see com.aetherteam.aether.block.portal.AetherPortalBlock
     * @see com.aetherteam.aether.block.portal.AetherPortalForcer
     * @see com.aetherteam.aether.event.hooks.DimensionHooks
     */
    public static ResourceKey<Level> returnDimension() {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace(AetherConfig.SERVER.portal_return_dimension_ID.get()));
    }
}
