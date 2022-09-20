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

import javax.annotation.Nonnull;

public class PhoenixBowItem extends BowItem {
    public PhoenixBowItem() {
        super(new Item.Properties().durability(384).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    /**
     * Marks any Arrow shot from the bow as a Phoenix Arrow with a default fire infliction time of 20 seconds, and 40 seconds if the bow has Flame.<br><br>
     * This uses {@link com.gildedgames.aether.capability.arrow.PhoenixArrowCapability#setPhoenixArrow(boolean)} and {@link com.gildedgames.aether.capability.arrow.PhoenixArrowCapability#setFireTime(int)} to track these values.
     * @param arrow The {@link AbstractArrow} created by the Bow.
     * @return The original {@link AbstractArrow} (the Phoenix Bow doesn't modify it).
     */
    @Nonnull
    @Override
    public AbstractArrow customArrow(@Nonnull AbstractArrow arrow) {
        PhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
            phoenixArrow.setPhoenixArrow(true);
            int defaultTime = 20;
            if (arrow.getOwner() instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, livingEntity) > 0) {
                defaultTime = 40;
            }
            phoenixArrow.setFireTime(defaultTime);
        });
        return super.customArrow(arrow);
    }
}
