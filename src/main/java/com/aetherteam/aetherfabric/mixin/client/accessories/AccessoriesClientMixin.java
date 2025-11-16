package com.aetherteam.aetherfabric.mixin.client.accessories;

import io.wispforest.accessories.client.AccessoriesClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AccessoriesClient.class)
public abstract class AccessoriesClientMixin {
    // Attempts to resolve issues where certain hooks will run even when resource reload has failed causing more errors
    @Inject(method = "lambda$init$13", at = @At("HEAD"), cancellable = true)
    private static void aetherFabric$fixSomethingPossibly(Minecraft client, boolean success, CallbackInfo ci) {
        if (!success) ci.cancel();
    }
}
