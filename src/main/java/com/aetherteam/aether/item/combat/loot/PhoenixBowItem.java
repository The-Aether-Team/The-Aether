package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public class PhoenixBowItem extends BowItem {
    public PhoenixBowItem() {
        super(new Item.Properties().durability(384).rarity(AetherItems.AETHER_LOOT));
    }

    /**
     * Marks any Arrow shot from the bow as a Phoenix Arrow with a default fire infliction time of 20 seconds, and 40 seconds if the bow has Flame.<br><br>
     * This uses {@link com.aetherteam.aether.capability.arrow.PhoenixArrowCapability#setPhoenixArrow(boolean)} and {@link com.aetherteam.aether.capability.arrow.PhoenixArrowCapability#setFireTime(int)} to track these values.
     * @param arrow The {@link AbstractArrow} created by the Bow.
     * @return The original {@link AbstractArrow} (the Phoenix Bow doesn't modify it).
     */
    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        PhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
            phoenixArrow.setPhoenixArrow(true);
            int defaultTime = 20;
            if (phoenixArrow.getArrow().getOwner() instanceof LivingEntity livingEntity && EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, livingEntity) > 0) {
                defaultTime = 40;
            }
            phoenixArrow.setFireTime(defaultTime);
        });
        return super.customArrow(arrow);
    }
}
