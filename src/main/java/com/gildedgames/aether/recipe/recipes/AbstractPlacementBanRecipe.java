package com.gildedgames.aether.recipe.recipes;

import com.gildedgames.aether.recipe.BlockStateIngredient;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipe<T, S extends Predicate<T>> implements Recipe<Container> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    private final ResourceKey<Biome> biomeKey;
    private final TagKey<Biome> biomeTag;
    protected final BlockStateIngredient bypassBlock;
    protected final S ingredient;

    public AbstractPlacementBanRecipe(RecipeType<?> type, ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, S ingredient) {
        this.type = type;
        this.id = id;
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
        this.bypassBlock = bypassBlock;
        this.ingredient = ingredient;
    }

    public boolean matches(Level level, BlockPos pos, T object) {
        if (this.bypassBlock.isEmpty() || !this.bypassBlock.test(level.getBlockState(pos))) {
            if (this.biomeKey != null) {
                return level.getBiome(pos).is(this.biomeKey) && this.getIngredient().test(object);
            } else if (this.biomeTag != null) {
                return level.getBiome(pos).is(this.biomeTag) && this.getIngredient().test(object);
            } else {
                return this.getIngredient().test(object);
            }
        }
        return false;
    }

    @Nullable
    public ResourceKey<Biome> getBiomeKey() {
        return this.biomeKey;
    }

    @Nullable
    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
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
