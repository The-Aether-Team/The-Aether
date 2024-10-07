package com.aetherteam.aether.item.tools.skyroot;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

/**
 * Double drops behavior is called by {@link com.aetherteam.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootHoeItem extends HoeItem implements SkyrootTool {
    public SkyrootHoeItem() {
        super(AetherItemTiers.SKYROOT, new Item.Properties().attributes(HoeItem.createAttributes(AetherItemTiers.SKYROOT, 0.0F, -3.0F)));
    }
}
