package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoxEntity.class)
public class FoxEntityMixin
{
    @Inject(at = @At("HEAD"), method = "isFood", cancellable = true)
    private void isFood(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() == AetherItems.BLUE_BERRY.get() || stack.getItem() == AetherItems.ENCHANTED_BERRY.get()) {
            cir.setReturnValue(true);
        }
    }
}
