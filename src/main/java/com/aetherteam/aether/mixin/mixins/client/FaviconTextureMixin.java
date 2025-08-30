package com.aetherteam.aether.mixin.mixins.client;

import net.minecraft.client.gui.screens.FaviconTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FaviconTexture.class)
public abstract class FaviconTextureMixin {
    @Inject(method = "checkOpen", at = @At("HEAD"), cancellable = true)
    private void checkOpen(CallbackInfo ci){
        ci.cancel();
    }
}
