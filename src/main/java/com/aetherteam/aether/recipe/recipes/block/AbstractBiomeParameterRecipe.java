package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class AbstractBiomeParameterRecipe extends AbstractBlockStateRecipe {
    @Nullable
    private final ResourceKey<Biome> biomeKey;
    @Nullable
    private final TagKey<Biome> biomeTag;

    public AbstractBiomeParameterRecipe(RecipeType<?> type, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable String function) {
        super(type, ingredient, result, function);
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
    }

    /**
     * Tests if the given object matches with the recipe.<br><br>
     * Checks if there is a {@link AbstractBiomeParameterRecipe#biomeKey} or a {@link AbstractBiomeParameterRecipe#biomeTag} it will test one of those alongside {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     * Otherwise, it will only test {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param state The {@link BlockState} being used that is being checked.
     * @return Whether the given {@link BlockState} matches.
     */
    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.biomeKey != null) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biomeKey);
        } else if (this.biomeTag != null) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biomeTag);
        } else {
            return super.matches(level, pos, state);
        }
    }

    @Nullable
    public ResourceKey<Biome> getBiomeKey() {
        return this.biomeKey;
    }

    @Nullable
    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
    }
}
