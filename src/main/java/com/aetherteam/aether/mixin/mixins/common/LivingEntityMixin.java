package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.entity.passive.MountableAnimal;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidJumpThreshold()D", shift = At.Shift.AFTER), method = "travel")
    private void travel(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        PhoenixArmor.boostVerticalLavaSwimming(livingEntity);
    }

    @Inject(at = @At(value = "HEAD"), method = "stopRiding", cancellable = true)
    private void stopRiding(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Entity entity = livingEntity.getVehicle();
        if (((entity instanceof MountableAnimal && !entity.isOnGround() && !entity.isPassenger() && !entity.isInFluidType()) || (entity instanceof Swet swet && !swet.isFriendly())) && entity.isAlive()) {
            ci.cancel();
        }
    }
}
