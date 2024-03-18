package com.aetherteam.aether.item.tools.zanite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

/**
 * Zanite mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvents.BreakSpeed)}.
 */
public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem() {
        super(AetherItemTiers.ZANITE, 6.0F, -3.1F, new Item.Properties());
    }
}
