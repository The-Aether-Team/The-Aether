package com.aetherteam.aether.mixin.mixins.client.aether_fabric;

import com.aetherteam.aether.client.event.listeners.AudioListener;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin {
    @WrapOperation(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;canPlaySound()Z"))
    private boolean aetherFabric$adjustSoundInstance(SoundInstance instance, Operation<Boolean> original) {
        var callback = new CancellableCallbackImpl();
        AudioListener.onPlaySound((SoundEngine)(Object)this, instance, callback);
        return original.call(instance) && !callback.isCanceled();
    }
}
