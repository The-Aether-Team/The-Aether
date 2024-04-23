package com.aetherteam.aether.utils;

import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class FabricUtils {

    public static boolean isInFluidType(LivingEntity livingEntity) {
        return ((EntityAccessor) livingEntity).getFluidHeight().size() > 0;
    }

    public static boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (stack.getItem() instanceof CustomEnchantingBehaviorItem enchantingItem)
            return enchantingItem.canApplyAtEnchantingTable(stack, enchantment);
        return enchantment.category.canEnchant(stack.getItem());
    }

    public static Map<Enchantment, Integer> getAllEnchantments(ItemStack stack) {
        return EnchantmentHelper.deserializeEnchantments(stack.getEnchantmentTags());
    }
}
