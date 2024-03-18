package com.aetherteam.aether.item.tools.zanite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

/**
 * Zanite mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvents.BreakSpeed)}.
 */
public class ZaniteShovelItem extends ShovelItem implements ZaniteTool {
    public ZaniteShovelItem() {
        super(AetherItemTiers.ZANITE, 1.5F, -3.0F, new Item.Properties());
    }
}
