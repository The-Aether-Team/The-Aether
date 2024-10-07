package com.aetherteam.aether.item.tools.skyroot;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

/**
 * Double drops behavior is called by {@link com.aetherteam.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootAxeItem extends AxeItem implements SkyrootTool {
    public SkyrootAxeItem() {
        super(AetherItemTiers.SKYROOT, new Item.Properties().attributes(AxeItem.createAttributes(AetherItemTiers.SKYROOT, 6.0F, -3.2F)));
    }
}
