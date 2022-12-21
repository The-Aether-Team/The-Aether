package com.gildedgames.aether.item.tools.zanite;

import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Zanite mining speed boost behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#modifyBreakSpeed(PlayerEvent.BreakSpeed)}.
 */
public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem() {
        super(AetherItemTiers.ZANITE, 6.0F, -3.1F, new Item.Properties());
    }
}
