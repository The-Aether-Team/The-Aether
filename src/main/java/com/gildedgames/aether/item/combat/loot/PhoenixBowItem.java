package com.gildedgames.aether.item.combat.loot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.arrow.PhoenixArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;

import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public class PhoenixBowItem extends BowItem
{
    public PhoenixBowItem() {
        super(new Item.Properties().durability(384).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        PhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
            phoenixArrow.setPhoenixArrow(true);
            int defaultTime = 20;
            if (arrow.getOwner() instanceof LivingEntity && EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, (LivingEntity) arrow.getOwner()) > 0) {
                defaultTime = 40;
            }
            phoenixArrow.setFireTime(defaultTime);
        });
        return super.customArrow(arrow);
    }
}
