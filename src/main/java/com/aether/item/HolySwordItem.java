package com.aether.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

public class HolySwordItem extends SwordItem {
    public HolySwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if ((target.isEntityUndead() || target.getCreatureAttribute() == CreatureAttribute.UNDEAD)) {
            float damage = 15.0F;
            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack);
            if (level > 0) {
                damage += (level * 2.5);
            }
            target.attackEntityFrom(DamageSource.DROWN, damage);
            stack.damageItem(10, attacker, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            return true;
        }
        return super.hitEntity(stack, target, attacker);
    }
}
