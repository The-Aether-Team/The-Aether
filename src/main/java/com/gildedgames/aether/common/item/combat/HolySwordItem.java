package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.DamageSource;

public class HolySwordItem extends SwordItem
{
    public HolySwordItem() {
        super(ItemTier.DIAMOND, 3, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if ((target.isInvertedHealAndHarm() || target.getMobType() == CreatureAttribute.UNDEAD)) {
            float damage = 15.0F;
            int level = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, stack);
            if (level > 0) {
                damage += (level * 2.5);
            }
            target.hurt(DamageSource.DROWN, damage);
            stack.hurtAndBreak(10, attacker, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
            });
            return true;
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
