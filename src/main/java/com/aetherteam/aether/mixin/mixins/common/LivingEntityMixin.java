package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * Handles vertical swimming for Phoenix Armor in lava without being affected by the upwards speed debuff from lava.
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see PhoenixArmor#boostVerticalLavaSwimming(LivingEntity)
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidJumpThreshold()D", shift = At.Shift.AFTER), method = "travel(Lnet/minecraft/world/phys/Vec3;)V")
    private void travel(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        PhoenixArmor.boostVerticalLavaSwimming(livingEntity);
    }
}
