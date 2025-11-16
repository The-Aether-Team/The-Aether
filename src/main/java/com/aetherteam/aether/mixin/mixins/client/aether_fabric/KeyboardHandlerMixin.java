package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    // TODO: DUE TO FUTURE CHANGES MOST LIKELY WON'T NEED SUCH AS KEYMAPPINGS ARE BETTER IN HIGHER VERSIONS
    @Inject(method = "keyPress", at = {
        @At(value = "RETURN", ordinal = 4),
        @At(value = "RETURN", ordinal = 5)
    })
    private void aetherFabric$onPostKeyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer != this.minecraft.getWindow().getWindow()) return;

        AetherPlayerClientListener.onPress(key);
    }
}
