package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    // TODO: DUE TO FUTURE CHANGES MOST LIKELY WON'T NEED SUCH AS KEYMAPPINGS ARE BETTER IN HIGHER VERSIONS
    @Inject(method = "onPress", at = @At("TAIL"))
    private void aetherFabric$onPostPress(long windowPointer, int button, int action, int modifiers, CallbackInfo ci) {
        if (windowPointer != this.minecraft.getWindow().getWindow()) return;

        AetherPlayerClientListener.onClick(button);
    }
}
