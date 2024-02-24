package com.aetherteam.aether.recipe.recipes.ban;

import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
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
    private final Optional<ResourceKey<Biome>> biomeKey;
    private final Optional<TagKey<Biome>> biomeTag;
    protected final BlockStateIngredient bypassBlock;
    protected final S ingredient;

    public AbstractPlacementBanRecipe(RecipeType<?> type, Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, BlockStateIngredient bypassBlock, S ingredient) {
        this.type = type;
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
        this.bypassBlock = bypassBlock;
        this.ingredient = ingredient;
    }

    /**
     * Tests if the given object matches with the recipe.<br><br>
     * First it checks if there is no {@link AbstractPlacementBanRecipe#bypassBlock} or it doesn't match the interacted block.
     * Then if there is a {@link AbstractPlacementBanRecipe#biomeKey} or a {@link AbstractPlacementBanRecipe#biomeTag} it will test one of those alongside {@link BlockStateIngredient#test(BlockState)}.
     * Otherwise, it will only test {@link BlockStateIngredient#test(BlockState)}.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param object The object being used that is being checked.
     * @return Whether the given object is banned from placement.
     */
    public boolean matches(Level level, BlockPos pos, T object) {
        if (this.bypassBlock.isEmpty() || !this.bypassBlock.test(level.getBlockState(pos))) {
            return this.biomeKey.map(biomeResourceKey -> this.getIngredient().test(object) && level.getBiome(pos).is(biomeResourceKey))
                    .orElseGet(() -> this.biomeTag.map(biomeTagKey -> this.getIngredient().test(object) && level.getBiome(pos).is(biomeTagKey))
                            .orElseGet(() -> this.getIngredient().test(object)));
        }
        return false;
    }

    public Optional<ResourceKey<Biome>> getBiomeKey() {
        return this.biomeKey;
    }

    public Optional<TagKey<Biome>> getBiomeTag() {
        return this.biomeTag;
    }

    public BlockStateIngredient getBypassBlock() {
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
