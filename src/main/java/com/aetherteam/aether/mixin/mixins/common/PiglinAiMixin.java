package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.integration.AccessoryUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(at = @At(value = "HEAD"), method = "isWearingSafeArmor(Lnet/minecraft/world/entity/LivingEntity;)Z", cancellable = true)
    private static void isWearingSafeArmor(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (AccessoryUtil.hasItem(entity, (stack) -> stack.makesPiglinsNeutral(entity))) {
            cir.setReturnValue(true);
        }
    }
}
