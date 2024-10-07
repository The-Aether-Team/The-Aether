package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.neoforge.event.level.BlockEvent;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvent.BreakEvent)}.
 */
public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem() {
        super(AetherItemTiers.HOLYSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(AetherItemTiers.HOLYSTONE, 1.5F, -3.0F)));
    }
}
