package com.aetherteam.aether.client.renderer.level;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.renderer.DimensionSpecialEffects;

public class AetherRenderEffects {
    public final static DimensionSpecialEffects AETHER_EFFECTS = new AetherSkyRenderEffects();

    public static void registerRenderEffects() {
        DimensionRenderingRegistry.registerDimensionEffects(AetherDimensions.AETHER_DIMENSION_TYPE.location(), AETHER_EFFECTS);
    }
}
