package com.gildedgames.aether.core.mixin.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PiglinTasks.class)
public class PiglinTasksMixin
{
    @Inject(at = @At("HEAD"), method = "isWearingGold", cancellable = true)
    private static void isWearingGold(LivingEntity player, CallbackInfoReturnable<Boolean> cir) {
        IItemHandlerModifiable curiosHandler = CuriosApi.getCuriosHelper().getEquippedCurios(player).orElse(null);
        for (int i = 0; i < curiosHandler.getSlots(); i++) {
            ItemStack stack = curiosHandler.getStackInSlot(i);
            if (stack.makesPiglinsNeutral(player)) {
               cir.setReturnValue(true);
            }
        }
    }
}
