package com.gildedgames.aether.item.accessories.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

public interface ZaniteAccessory {
    /**
     * Damages the Zanite accessory for 1 durability every 400 ticks when the accessory is equipped.
     * @param context The {@link SlotContext} of the Curio.
     * @param stack The Curio {@link ItemStack}.
     */
    default void damageZaniteAccessory(SlotContext context, ItemStack stack) {
        LivingEntity livingEntity = context.entity();
        if (!livingEntity.getLevel().isClientSide() && livingEntity.tickCount % 400 == 0) {
            stack.hurtAndBreak(1, livingEntity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(context));
        }
    }

    /**
     * Calculates damage increase based on a base value, the amount of damage taken (maximum durability - current durability), and the stack's maximum durability.<br><br>
     * <a href="https://www.desmos.com/calculator/drohdchhsx">See math visually.</a>
     * @param speed A base {@link Float} value.
     * @param slotResult The {@link SlotResult} of the Curio.
     * @return The buffed {@link Float} value.
     */
    static float handleMiningSpeed(float speed, SlotResult slotResult) { //todo: document math
        ItemStack stack = slotResult.stack();
        return speed * (1.0F + (((float) stack.getDamageValue()) / (((float) stack.getMaxDamage()) * 3.0F)));
    }
}
