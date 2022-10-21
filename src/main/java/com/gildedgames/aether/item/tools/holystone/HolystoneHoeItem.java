package com.gildedgames.aether.item.tools.holystone;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.level.BlockEvent;

/**
 * Ambrosium dropping behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvent.BreakEvent)}.
 */
public class HolystoneHoeItem extends HoeItem implements HolystoneTool {
    public HolystoneHoeItem() {
        super(AetherItemTiers.HOLYSTONE, -1, -2.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }
}
