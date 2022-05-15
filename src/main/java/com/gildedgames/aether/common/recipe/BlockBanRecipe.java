package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.PlacementBanRecipeSerializer;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeUtil;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BlockBanRecipe extends AbstractPlacementBanRecipe<BlockState, BlockStateIngredient> {
    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient) {
        super(AetherRecipes.RecipeTypes.BLOCK_PLACEMENT_BAN, id, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
    }

    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock) {
        this(id, dimensionTypeKey, dimensionTypeTag, bypassBlock, BlockStateIngredient.EMPTY);
    }

    public boolean banBlock(Level level, BlockPos pos, BlockState state) {
        if (this.matches(level, pos.below(), state)) {
            if (AetherEventDispatch.isBlockPlacementBanned(level, pos, state)) {
                AetherEventDispatch.onPlacementSpawnParticles(level, pos, null, null, state);
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.BLOCK_PLACEMENT_BAN.get();
    }

    public static class Serializer extends PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> {
        public Serializer() {
            super(BlockBanRecipe::new);
        }

        @Nonnull
        @Override
        public BlockBanRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
            BlockBanRecipe recipe = super.fromJson(recipeId, serializedRecipe);
            if (!serializedRecipe.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
            JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
            BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);
            return new BlockBanRecipe(recipeId, recipe.getDimensionTypeKey(), recipe.getDimensionTypeTag(), recipe.getBypassBlock(), ingredient);
        }

        @Nullable
        @Override
        public BlockBanRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<DimensionType> dimensionTypeKey = BlockStateRecipeUtil.readDimensionTypeKey(buf);
            TagKey<DimensionType> dimensionTypeTag = BlockStateRecipeUtil.readDimensionTypeTag(buf);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            return new BlockBanRecipe(recipeId, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, @Nonnull BlockBanRecipe recipe) {
            super.toNetwork(buf, recipe);
            recipe.getIngredient().toNetwork(buf);
        }
    }
}
