package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
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

    public boolean set(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState) {
        if (this.matches(level, pos, oldState)) {
            BlockState newState = this.getResultState(oldState);
            level.setBlockAndUpdate(pos, newState);
            return true;
        }
        return false;
    }

    public boolean matches(Level level, BlockPos pos, BlockState state) {
        return this.getIngredient().test(state);
    }

    @Override
    public BlockState getResultState(BlockState originalState) {
        BlockState resultState = this.getResultBlock().withPropertiesOf(originalState);
        for (Map.Entry<Property<?>, Comparable<?>> propertyEntry : this.getResultProperties().entrySet()) {
            resultState = BlockStateRecipeUtil.setHelper(propertyEntry, resultState);
        }
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
