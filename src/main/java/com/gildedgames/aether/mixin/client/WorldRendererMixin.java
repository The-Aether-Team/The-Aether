package com.gildedgames.aether.mixin.client;

import com.gildedgames.aether.registry.AetherDimensions;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    
    @Shadow private ClientWorld world;

    /**
     * {@link net.minecraft.client.renderer.WorldRenderer#renderSky(MatrixStack, float)}
     * Code injection to fix Minecraft's issue of turning the horizon black when the player is below y=63.
     * The method checks if the world key is the same as the Aether's, and if it is, it returns 1.
     */
    @ModifyVariable(
            method = "renderSky(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private double onRenderSky(double d0) {
        if(world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
            return 1.0D;
        }
        return d0;
    }

}
