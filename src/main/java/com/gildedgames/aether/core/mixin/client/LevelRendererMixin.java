package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Shadow
    private ClientLevel level;

    @ModifyVariable(at = @At(value = "STORE"), method = "renderSky", ordinal = 0)
    private double renderSky(double d0) {
        if (this.level.dimension() == AetherDimensions.AETHER_WORLD) {
            return 1.0D;
        }
        return d0;
    }
}