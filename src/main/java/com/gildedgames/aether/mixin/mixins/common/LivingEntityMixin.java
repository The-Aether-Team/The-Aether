package com.gildedgames.aether.mixin.mixins.common;

import com.gildedgames.aether.capability.cape.CapeEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;aiStep()V", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        CapeEntity.get(livingEntity).ifPresent(CapeEntity::moveCloak);
    }
}
