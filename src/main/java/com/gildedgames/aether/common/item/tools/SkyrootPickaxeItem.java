package com.gildedgames.aether.common.item.tools;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class SkyrootPickaxeItem extends PickaxeItem
{
    public SkyrootPickaxeItem() {
        super(AetherItemTiers.SKYROOT, 1, -2.8F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}
