package com.gildedgames.aether.common.recipe.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import java.util.Map;

public interface BlockStateRecipe extends Recipe<Container> {
    BlockStateIngredient getIngredient();

    Block getResultBlock();

    Map<Property<?>, Comparable<?>> getResultProperties();

    BlockState getResultState(BlockState originalState);

    @Override
    default boolean matches(@Nonnull Container container, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    default ItemStack assemble(@Nonnull Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    default ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    default NonNullList<ItemStack> getRemainingItems(@Nonnull Container container) {
        return NonNullList.create();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }
}
