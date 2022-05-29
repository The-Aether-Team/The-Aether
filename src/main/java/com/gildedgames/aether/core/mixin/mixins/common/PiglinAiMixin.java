package com.gildedgames.aether.core.mixin.mixins.common;

import com.gildedgames.aether.core.mixin.AetherMixinHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin
{
    @Inject(at = @At("HEAD"), method = "isWearingGold", cancellable = true)
    private static void isWearingGold(LivingEntity player, CallbackInfoReturnable<Boolean> cir) {
        boolean bool = AetherMixinHooks.piglinAiMixin(player);
        if (bool) {
            cir.setReturnValue(true);
        }
    }
}
