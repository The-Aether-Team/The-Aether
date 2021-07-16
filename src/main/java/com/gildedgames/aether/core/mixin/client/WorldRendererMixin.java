package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin
{
    @Shadow
    private ClientWorld level;

    /**
     * {@link net.minecraft.client.renderer.WorldRenderer#renderSky(MatrixStack, float)}
     * Code injection to fix Minecraft's issue of turning the horizon black when the player is below y=63.
     * The method checks if the world key is the same as the Aether's, and if it is, it returns 1.
     */
    @ModifyVariable(at = @At(value = "STORE"), method = "renderSky(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V", ordinal = 0)
    private double onRenderSky(double d0) {
        if (this.level.dimension() == AetherDimensions.AETHER_WORLD) {
            return 1.0D;
        }
        return d0;
    }
}