package com.gildedgames.aether.core.mixin.mixins.common;

import com.gildedgames.aether.common.item.combat.abilities.weapon.GravititeWeapon;
import com.gildedgames.aether.core.capability.cape.CapeEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        CapeEntity.get(livingEntity).ifPresent(CapeEntity::moveCloak);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V", shift = At.Shift.AFTER), method = "hurt")
    private void hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        GravititeWeapon.launchEntity(livingEntity, damageSource);
    }
}
