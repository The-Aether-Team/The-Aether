package com.gildedgames.aether.common.recipe.util;

import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class AbstractBlockStateRecipe implements BlockStateRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final BlockStateIngredient ingredient;
    protected final Block resultBlock;
    protected final Map<Property<?>, Comparable<?>> resultProperties;

    public AbstractBlockStateRecipe(RecipeType<?> type, ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.resultBlock = resultBlock;
        this.resultProperties = resultProperties;
    }

    public boolean matches(BlockState state) {
        return this.ingredient.test(state);
    }

    @Override
    public BlockState getResultState(BlockState originalState) {
        BlockState resultState = this.resultBlock.withPropertiesOf(originalState);
//        for (Map.Entry<Property<T>, Comparable<T>> propertyEntry : ((Map<Property<T>, Comparable<T>>) this.resultProperties).entrySet()) { //TODO: generics my abhorred
//            BlockStateRecipeUtil.setHelper(propertyEntry, originalState);
//        }
        return resultState;
    }

    @Override
    public BlockStateIngredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public Block getResultBlock() {
        return this.resultBlock;
    }

    @Override
    public Map<Property<?>, Comparable<?>> getResultProperties() {
        return this.resultProperties;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
