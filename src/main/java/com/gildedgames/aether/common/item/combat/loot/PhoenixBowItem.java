package com.gildedgames.aether.common.item.combat.loot;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;

public class PhoenixBowItem extends BowItem
{
    public PhoenixBowItem() {
        super(new Item.Properties().durability(384).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
        IPhoenixArrow.get(arrow).ifPresent(phoenixArrow -> {
            phoenixArrow.setPhoenixArrow(true);
            phoenixArrow.setFireTime(5);
        });
        return super.customArrow(arrow);
    }
}
