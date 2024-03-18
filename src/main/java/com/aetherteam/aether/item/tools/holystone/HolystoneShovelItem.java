package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvents.BreakEvent)}.
 */
public class HolystoneShovelItem extends ShovelItem implements HolystoneTool {
    public HolystoneShovelItem() {
        super(AetherItemTiers.HOLYSTONE, 1.5F, -3.0F, new Item.Properties());
    }
}
