package com.aetherteam.aether.item.tools.zanite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Zanite mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)}.
 */
public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem() {
        super(AetherItemTiers.ZANITE, new Item.Properties().attributes(ShovelItem.createAttributes(AetherItemTiers.ZANITE, 1.5F, -3.0F)));
    }
}
