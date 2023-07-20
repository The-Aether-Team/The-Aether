package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.WorldDisplayHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {
    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void render(float deltaTick, float alpha, CallbackInfo info) {
        if (AetherConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().level != null && WorldDisplayHelper.loadedLevel != null) {
            info.cancel();
        }
    }
}
