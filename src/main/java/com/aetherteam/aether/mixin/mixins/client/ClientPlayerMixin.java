package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.api.PostTickEntity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerMixin implements PostTickEntity {
    @Shadow
    protected abstract void sendPosition();

    @Unique
    boolean incubus_core$sendMovement = false;

    /**
     * Since the player can be moved by FloatingBlockEntity after ClientPlayerEntity.tick()
     * the call to sendMovementPackets() needs to be delayed till after all FloatingBlockEntities have ticked
     */
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;sendPosition()V"))
    void redirectSendMovementPackets(LocalPlayer clientPlayerEntity) {
        incubus_core$sendMovement = true;
    }

    @Override
    public void postTick() {
        if (incubus_core$sendMovement) {
            sendPosition();
            incubus_core$sendMovement = false;
        }
    }
}
