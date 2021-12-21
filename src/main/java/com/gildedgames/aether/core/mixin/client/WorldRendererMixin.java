package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin
{
    @Shadow
    private ClientLevel level;

    /**
     * {@link net.minecraft.client.renderer.LevelRenderer#renderSky(PoseStack, Matrix4f, float, Runnable)}
     * Code injection to fix Minecraft's issue of turning the horizon black when the player is below y=63.
     * The method checks if the world key is the same as the Aether's, and if it is, it returns 1.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "renderSky", ordinal = 0) //TODO: Figure out what replaced this.
    private double onRenderSky(double d0) {
        if (this.level.dimension() == AetherDimensions.AETHER_WORLD) {
            return 1.0D;
        }
        return d0;
    }
}