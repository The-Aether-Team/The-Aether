package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public abstract class AbstractBiomeParameterRecipe extends AbstractBlockStateRecipe {
    private final Optional<ResourceKey<Biome>> biomeKey;
    private final Optional<TagKey<Biome>> biomeTag;

    public AbstractBiomeParameterRecipe(RecipeType<?> type, Optional<ResourceKey<Biome>> biomeKey, Optional<TagKey<Biome>> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
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
        return this.biomeKey.map(biomeResourceKey -> super.matches(level, pos, state) && level.getBiome(pos).is(biomeResourceKey))
                .orElseGet(() -> this.biomeTag.map(biomeTagKey -> super.matches(level, pos, state) && level.getBiome(pos).is(biomeTagKey))
                        .orElseGet(() -> super.matches(level, pos, state)));
    }

    public Optional<ResourceKey<Biome>> getBiomeKey() {
        return this.biomeKey;
    }

    public Optional<TagKey<Biome>> getBiomeTag() {
        return this.biomeTag;
    }
}
