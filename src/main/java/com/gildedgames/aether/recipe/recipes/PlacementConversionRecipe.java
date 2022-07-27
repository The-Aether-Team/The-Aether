package com.gildedgames.aether.recipe.recipes;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.PlacementConvertEvent;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlacementConversionRecipe extends AbstractBiomeParameterRecipe {
    public PlacementConversionRecipe(ResourceLocation id, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPropertyPair result) {
        super(AetherRecipeTypes.PLACEMENT_CONVERSION.get(), id, ingredient, biomeKey, biomeTag, result);
    }

    public PlacementConversionRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result) {
        this(id, ingredient, null, null, result);
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

    public static class Serializer extends BlockStateRecipeSerializer<PlacementConversionRecipe> {
        public Serializer() {
            super(PlacementConversionRecipe::new);
        }

        @Nonnull
        @Override
        public PlacementConversionRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
            ResourceKey<Biome> biomeKey = null;
            TagKey<Biome> biomeTag = null;
            if (serializedRecipe.has("biome")) {
                String biomeName = GsonHelper.getAsString(serializedRecipe, "biome");
                if (biomeName.startsWith("#")) {
                    biomeTag = BlockStateRecipeUtil.biomeTagFromJson(serializedRecipe);
                } else {
                    biomeKey = BlockStateRecipeUtil.biomeKeyFromJson(serializedRecipe);
                }
            }
            PlacementConversionRecipe recipe = super.fromJson(recipeId, serializedRecipe);
            return new PlacementConversionRecipe(recipeId, recipe.getIngredient(), biomeKey, biomeTag, recipe.getResult());
        }

        @Nullable
        @Override
        public PlacementConversionRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            BlockPropertyPair result = BlockStateRecipeUtil.readPair(buf);
            return new PlacementConversionRecipe(recipeId, ingredient, biomeKey, biomeTag, result);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, PlacementConversionRecipe recipe) {
            BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getBiomeKey());
            BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getBiomeTag());
            super.toNetwork(buf, recipe);
        }
    }
}
