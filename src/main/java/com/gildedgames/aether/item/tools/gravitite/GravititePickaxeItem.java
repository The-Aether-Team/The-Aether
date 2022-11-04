package com.gildedgames.aether.item.tools.gravitite;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.GravititeTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.event.level.BlockEvent;

/**
 * Gravitite block floating behavior is called by {@link com.gildedgames.aether.event.listeners.abilities.ToolAbilityListener#doGravititeAbility(BlockEvent.BlockToolModificationEvent)}.
 */
public class GravititePickaxeItem extends PickaxeItem implements GravititeTool {
    public GravititePickaxeItem() {
        super(AetherItemTiers.GRAVITITE, 1, -2.8F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }
}
