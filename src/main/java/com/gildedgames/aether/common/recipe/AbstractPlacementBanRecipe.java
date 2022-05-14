package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipe<T, S extends Predicate<T>> implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final ResourceKey<DimensionType> dimensionTypeKey;
    protected final TagKey<DimensionType> dimensionTypeTag;
    protected final BlockStateIngredient bypassBlock;
    protected final S ingredient;

    public AbstractPlacementBanRecipe(RecipeType<?> type, ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, S ingredient) {
        this.type = type;
        this.id = id;
        this.dimensionTypeKey = dimensionTypeKey;
        this.dimensionTypeTag = dimensionTypeTag;
        this.bypassBlock = bypassBlock;
        this.ingredient = ingredient;
    }

    public ResourceKey<DimensionType> getDimensionTypeKey() {
        return this.dimensionTypeKey;
    }

    public TagKey<DimensionType> getDimensionTypeTag() {
        return this.dimensionTypeTag;
    }

    public BlockStateIngredient getBypassBlock() {
        return this.bypassBlock;
    }

    public S getIngredient() {
        return this.ingredient;
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

    @Override
    public boolean matches(@Nonnull Container container, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }
}
