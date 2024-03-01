package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.event.PlacementConvertEvent;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class PlacementConversionRecipe extends AbstractBiomeParameterRecipe {
    public PlacementConversionRecipe(Optional<Either<ResourceKey<Biome>, TagKey<Biome>>> biome, BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        super(AetherRecipeTypes.PLACEMENT_CONVERSION.get(), biome, ingredient, result, function);
    }

    public PlacementConversionRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, Optional<ResourceLocation> function) {
        this(Optional.empty(), ingredient, result, function);
    }

    /**
     * Replaces an old {@link BlockState} with a new one from {@link com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe#getResultState(BlockState)}, if {@link PlacementConvertEvent} isn't cancelled.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param oldState The original {@link BlockState} being used that is being checked.
     * @return Whether the new {@link BlockState} was set.
     */
    public boolean convert(Level level, BlockPos pos, BlockState oldState) {
        if (this.matches(level, pos, oldState)) {
            BlockState newState = this.getResultState(oldState);
            PlacementConvertEvent event = AetherEventDispatch.onPlacementConvert(level, pos, oldState, newState);
            if (!event.isCanceled()) {
                level.setBlockAndUpdate(pos, newState);
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.PLACEMENT_CONVERSION.get();
    }

    public static class Serializer extends BiomeParameterRecipeSerializer<PlacementConversionRecipe> {
        public Serializer() {
            super(PlacementConversionRecipe::new, PlacementConversionRecipe::new);
        }
    }
}
