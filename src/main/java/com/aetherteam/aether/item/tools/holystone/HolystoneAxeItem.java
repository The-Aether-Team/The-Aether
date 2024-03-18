package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvents.BreakEvent)}.
 */
public class HolystoneAxeItem extends AxeItem implements HolystoneTool {
    public HolystoneAxeItem() {
        super(AetherItemTiers.HOLYSTONE, 7.0F, -3.2F, new Item.Properties());
    }
}
