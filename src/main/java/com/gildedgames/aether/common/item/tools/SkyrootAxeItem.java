package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SkyrootAxeItem extends AxeItem
{
    public SkyrootAxeItem() {
        super(AetherItemTiers.SKYROOT, 6.0F, -3.2F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}
