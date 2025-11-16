package com.aetherteam.aetherfabric.mixin.client;

import net.minecraft.client.gui.screens.FaviconTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FaviconTexture.class)
public abstract class FaviconTextureMixin {
    // Just suppress the FaviconTexture error if its already been closed
    @Inject(method = "checkOpen", at = @At("HEAD"), cancellable = true)
    private void aetherFabric$preventException(CallbackInfo ci){
        ci.cancel();
    }
}
