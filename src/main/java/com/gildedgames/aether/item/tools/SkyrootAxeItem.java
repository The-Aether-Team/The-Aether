package com.gildedgames.aether.item.tools;

import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SkyrootAxeItem extends AxeItem
{
    public SkyrootAxeItem() {
        super(AetherItemTier.SKYROOT, 6.0F, -3.2F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}
