package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.entity.passive.MountableEntity;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;rideTick()V", shift = At.Shift.AFTER), method = "rideTick")
    private void rideTick(CallbackInfo ci) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;
        if (localPlayer.isPassenger() && localPlayer.getVehicle() instanceof MountableEntity mountableEntity) {
            mountableEntity.setPlayerTriedToCrouch(localPlayer.input.shiftKeyDown);
        }
    }
}
