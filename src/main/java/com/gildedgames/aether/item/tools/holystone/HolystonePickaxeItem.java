package com.gildedgames.aether.item.tools.holystone;

import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.event.level.BlockEvent;

/**
 * Ambrosium dropping behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#doHolystoneAbility(BlockEvent.BreakEvent)}.
 */
public class HolystonePickaxeItem extends PickaxeItem implements HolystoneTool {
    public HolystonePickaxeItem() {
        super(AetherItemTiers.HOLYSTONE, 1, -2.8F, new Item.Properties());
    }
}
