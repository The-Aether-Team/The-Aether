package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItemTiers;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class VampireBladeItem extends SwordItem
{
    public VampireBladeItem() {
        super(AetherItemTiers.VAMPIRE, 3, -2.4f, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getHealth() < attacker.getMaxHealth()) {
            attacker.heal(1.0F);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
