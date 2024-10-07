package com.aetherteam.aether.item.tools.skyroot;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

/**
 * Double drops behavior is called by {@link com.aetherteam.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootPickaxeItem extends PickaxeItem implements SkyrootTool {
    public SkyrootPickaxeItem() {
        super(AetherItemTiers.SKYROOT, new Item.Properties().attributes(PickaxeItem.createAttributes(AetherItemTiers.SKYROOT, 1.0F, -2.8F)));
    }
}
