package com.aether.item.combat;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class SkyrootSwordItem extends SwordItem
{
    public SkyrootSwordItem() {
        super(AetherItemTier.SKYROOT, 3, -2.4F, new Item.Properties().group(AetherItemGroups.AETHER_COMBAT));
    }
}
