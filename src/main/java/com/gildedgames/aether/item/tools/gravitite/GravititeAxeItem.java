package com.gildedgames.aether.item.tools.gravitite;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.GravititeTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.level.BlockEvent;

/**
 * Gravitite block floating behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#doGravititeAbility(BlockEvent.BlockToolModificationEvent)}.
 */
public class GravititeAxeItem extends AxeItem implements GravititeTool {
    public GravititeAxeItem() {
        super(AetherItemTiers.GRAVITITE, 5.0F, -3.0F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }
}
