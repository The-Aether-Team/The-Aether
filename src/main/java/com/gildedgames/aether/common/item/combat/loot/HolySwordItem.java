package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.damagesource.DamageSource;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class HolySwordItem extends SwordItem
{
    public HolySwordItem() {
        super(Tiers.DIAMOND, 4, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getMobType() == MobType.UNDEAD || target.isInvertedHealAndHarm()) {
            float damageAmount = 15.0F;
            int smiteModifier = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, stack);
            if (smiteModifier > 0) {
                damageAmount += (smiteModifier * 2.5);
            }
            target.hurt(DamageSource.DROWN, damageAmount);
            stack.hurtAndBreak(10, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
