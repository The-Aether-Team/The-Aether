package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {
    /**
     * Used by the world preview system.<br>
     * Prevents the {@link net.minecraft.client.gui.screens.TitleScreen} panorama from rendering when a world preview is active.
     * @param deltaTick The {@link Float} for the delta tick of the game.
     * @param alpha The {@link Float} for the alpha of the panorama renderer.
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     */
    @Inject(at = @At(value = "HEAD"), method = "render(FF)V", cancellable = true)
    public void render(float deltaTick, float alpha, CallbackInfo ci) {
        if (Minecraft.getInstance().level != null && WorldDisplayHelper.isActive()) {
            ci.cancel();
        }
    }
}
