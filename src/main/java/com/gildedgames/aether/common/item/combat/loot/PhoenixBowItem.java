package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
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
        IPhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
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
