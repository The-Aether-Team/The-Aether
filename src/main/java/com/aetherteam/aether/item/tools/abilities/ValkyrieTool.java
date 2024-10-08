package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.Aether;
import net.minecraft.resources.ResourceLocation;

public interface ValkyrieTool {
    /**
     * The default reach modifier value as a {@link Double}.
     */
    double RANGE_MODIFER = 3.5;
    /**
     * The unique identifier for the item's mining reach distance in the main hand.
     */
    ResourceLocation BLOCK_INTERACTION_RANGE_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "valkyrie_tool_block_interaction_range");
    /**
     * The unique identifier for the item's attack range distance in the main hand.
     */
    ResourceLocation ENTITY_INTERACTION_RANGE_MODIFIER_UUID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "valkyrie_tool_entity_interaction_range");
}
