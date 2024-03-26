package com.aetherteam.aether.recipe.recipes.ban;

import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractPlacementBanRecipe<T, S extends Predicate<T>> implements Recipe<Container> {
    protected final RecipeType<?> type;
    private final Either<ResourceKey<Biome>, TagKey<Biome>> biome;
    protected final Optional<BlockStateIngredient> bypassBlock;
    protected final S ingredient;

    public AbstractPlacementBanRecipe(RecipeType<?> type, Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, S ingredient) {
        this.type = type;
        this.biome = biome;
        this.bypassBlock = bypassBlock;
        this.ingredient = ingredient;
    }

    /**
     * Tests if the given object matches with the recipe.<br><br>
     * First it checks if there is no {@link AbstractPlacementBanRecipe#bypassBlock} or it doesn't match the interacted block.
     * Then if there is a {@link Biome} {@link ResourceKey} or a {@link Biome} {@link TagKey} it will test one of those alongside {@link BlockStateIngredient#test(BlockState)}.
     * Otherwise, it will only test {@link BlockStateIngredient#test(BlockState)}.
     *
     * @param level  The {@link Level} the recipe is performed in.
     * @param pos    The {@link BlockPos} the recipe is performed at.
     * @param object The object being used that is being checked.
     * @return Whether the given object is banned from placement.
     */
    public boolean matches(Level level, BlockPos pos, T object) {
        if (this.bypassBlock.isEmpty() || this.bypassBlock.get().isEmpty() || !this.bypassBlock.get().test(level.getBlockState(pos))) {
            if (this.biome.left().isPresent()) {
                return this.getIngredient().test(object) && level.getBiome(pos).is(this.biome.left().get());
            } else if (this.biome.right().isPresent()) {
                return this.getIngredient().test(object) && level.getBiome(pos).is(this.biome.right().get());
            } else {
                return this.getIngredient().test(object);
            }
        }
        return false;
    }

    public Either<ResourceKey<Biome>, TagKey<Biome>> getBiome() {
        return this.biome;
    }

    public Optional<BlockStateIngredient> getBypassBlock() {
        return this.bypassBlock;
    }

    public S getIngredient() {
        return this.ingredient;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }
}
