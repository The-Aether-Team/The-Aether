package com.aetherteam.aether.item.tools.zanite;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ZaniteTool;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

/**
 * Zanite mining speed boost behavior is called by {@link com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvents.BreakSpeed)}.
 */
public class ZanitePickaxeItem extends PickaxeItem implements ZaniteTool {
    public ZanitePickaxeItem() {
        super(AetherItemTiers.ZANITE, 1, -2.8F, new Item.Properties());
    }
}
