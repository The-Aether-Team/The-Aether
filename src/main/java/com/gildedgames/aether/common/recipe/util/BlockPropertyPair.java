package com.gildedgames.aether.common.recipe.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public record BlockPropertyPair(Block block, Map<Property<?>, Comparable<?>> properties) {
    public static BlockPropertyPair of(Block block, Map<Property<?>, Comparable<?>> properties) {
        return new BlockPropertyPair(block, properties);
    }
}
