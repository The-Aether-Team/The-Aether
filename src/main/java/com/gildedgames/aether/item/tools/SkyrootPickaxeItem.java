package com.gildedgames.aether.item.tools;

import com.gildedgames.aether.registry.AetherItemGroups;
import com.gildedgames.aether.registry.AetherItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class SkyrootPickaxeItem extends PickaxeItem
{
    public SkyrootPickaxeItem() {
        super(AetherItemTier.SKYROOT, 1, -2.8F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}
