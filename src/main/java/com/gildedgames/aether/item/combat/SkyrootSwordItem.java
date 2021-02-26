package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class SkyrootSwordItem extends SwordItem
{
    public SkyrootSwordItem() {
        super(AetherItemTier.SKYROOT, 3, -2.4F, new Item.Properties().group(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}
