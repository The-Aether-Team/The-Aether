package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.mojang.datafixers.util.Either;
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
    private final Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome;

    public AbstractBiomeParameterRecipe(RecipeType<?> type, Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        super(type, ingredient, result, function);
        this.biome = biome;
    }

    /**
     * Tests if the given object matches with the recipe.<br><br>
     * Checks if there is a {@link Biome} {@link ResourceKey} or a {@link Biome} {@link TagKey} it will test one of those alongside {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     * Otherwise, it will only test {@link AbstractBlockStateRecipe#matches(Level, BlockPos, BlockState)}.
     *
     * @param level The {@link Level} the recipe is performed in.
     * @param pos   The {@link BlockPos} the recipe is performed at.
     * @param state The {@link BlockState} being used that is being checked.
     * @return Whether the given {@link BlockState} matches.
     */
    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.biome.isPresent() && this.biome.get().left().isPresent()) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biome.get().left().get());
        } else if (this.biome.isPresent() && this.biome.get().right().isPresent()) {
            return super.matches(level, pos, state) && level.getBiome(pos).is(this.biome.get().right().get());
        } else {
            return super.matches(level, pos, state);
        }
    }

    public Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> getBiome() {
        return this.biome;
    }
}
