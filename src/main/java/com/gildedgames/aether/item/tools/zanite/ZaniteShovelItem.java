package com.gildedgames.aether.item.tools.zanite;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Zanite mining speed boost behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)}.
 */
public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem() {
        super(AetherItemTiers.ZANITE, 1.5F, -3.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }
}
