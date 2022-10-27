package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.PlacementConvertEvent;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlacementConversionRecipe extends AbstractBiomeParameterRecipe {
    public PlacementConversionRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction mcfunction) {
        super(AetherRecipeTypes.PLACEMENT_CONVERSION.get(), id, biomeKey, biomeTag, ingredient, result, mcfunction);
    }

    public PlacementConversionRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction mcfunction) {
        this(id, null, null, ingredient, result, mcfunction);
    }

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

    @Nonnull
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
