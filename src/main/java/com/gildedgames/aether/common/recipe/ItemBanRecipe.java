package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.PlacementBanRecipeSerializer;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemBanRecipe extends AbstractPlacementBanRecipe<ItemStack, Ingredient> {
    public ItemBanRecipe(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, Ingredient ingredient) {
        super(AetherRecipes.RecipeTypes.ITEM_PLACEMENT_BAN, id, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
    }

    public ItemBanRecipe(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock) {
        this(id, dimensionTypeKey, dimensionTypeTag, bypassBlock, Ingredient.EMPTY);
    }

    public boolean banItem(Level level, BlockPos pos, Direction face, ItemStack stack) {
        if (this.matches(level, pos, stack)) {
            if (AetherEventDispatch.isItemPlacementBanned(level, pos, stack)) {
                AetherEventDispatch.onPlacementSpawnParticles(level, pos, face, stack, null);
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.ITEM_PLACEMENT_BAN.get();
    }

    public static class Serializer extends PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> {
        public Serializer() {
            super(ItemBanRecipe::new);
        }

        @Nonnull
        @Override
        public ItemBanRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
            ItemBanRecipe recipe = super.fromJson(recipeId, serializedRecipe);
            if (!serializedRecipe.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
            JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonElement);
            return new ItemBanRecipe(recipeId, recipe.getDimensionTypeKey(), recipe.getDimensionTypeTag(), recipe.getBypassBlock(), ingredient);
        }

        @Nullable
        @Override
        public ItemBanRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<DimensionType> dimensionTypeKey = BlockStateRecipeUtil.readDimensionTypeKey(buf);
            TagKey<DimensionType> dimensionTypeTag = BlockStateRecipeUtil.readDimensionTypeTag(buf);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            return new ItemBanRecipe(recipeId, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, @Nonnull ItemBanRecipe recipe) {
            super.toNetwork(buf, recipe);
            recipe.getIngredient().toNetwork(buf);
        }
    }
}
