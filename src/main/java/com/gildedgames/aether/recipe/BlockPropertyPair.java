package com.gildedgames.aether.recipe;

import com.google.common.collect.Sets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashSet;
import java.util.Map;

public record BlockPropertyPair(Block block, Map<Property<?>, Comparable<?>> properties) {
    public static BlockPropertyPair of(Block block, Map<Property<?>, Comparable<?>> properties) {
        return new BlockPropertyPair(block, properties);
    }

    public static boolean matches(BlockState state, Block block, Map<Property<?>, Comparable<?>> properties) {
        if (state.is(block)) {
            return propertiesMatch(state, properties);
        }
        return false;
    }

    public static boolean propertiesMatch(BlockState state, Map<Property<?>, Comparable<?>> properties) {
        if (!properties.isEmpty()) {
            HashSet<Map.Entry<Property<?>, Comparable<?>>> stateProperties = new HashSet<>();
            stateProperties.addAll(state.getValues().entrySet());
            return stateProperties.containsAll(properties.entrySet());
        }
        return true;
    }

    public boolean matches(BlockState state) {
        return BlockPropertyPair.matches(state, this.block(), this.properties());
    }
}
