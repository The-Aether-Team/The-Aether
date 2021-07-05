package com.gildedgames.aether.common.item.combat.loot;

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
        if (target.getMobType() == CreatureAttribute.UNDEAD || target.isInvertedHealAndHarm()) {
            float damageAmount = 15.0F;
            int smiteModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, stack);
            if (smiteModifier > 0) {
                damageAmount += (smiteModifier * 2.5);
            }
            target.hurt(DamageSource.DROWN, damageAmount);
            stack.hurtAndBreak(10, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
