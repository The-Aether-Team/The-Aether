package com.aether.item.tools;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class SkyrootPickaxeItem extends PickaxeItem
{
    public SkyrootPickaxeItem() {
        super(AetherItemTier.SKYROOT, 1, -2.8F, new Item.Properties().group(AetherItemGroups.AETHER_TOOLS));
    }
}
