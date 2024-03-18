package com.aetherteam.aether.item.tools.holystone;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.HolystoneTool;
import io.github.fabricators_of_create.porting_lib.event.common.BlockEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

/**
 * Ambrosium dropping behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvents.BreakEvent)}.
 */
public class HolystonePickaxeItem extends PickaxeItem implements HolystoneTool {
    public HolystonePickaxeItem() {
        super(AetherItemTiers.HOLYSTONE, 1, -2.8F, new Item.Properties());
    }
}
