package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import com.gildedgames.aether.core.util.LevelUtil;
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
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemBanRecipe extends AbstractPlacementBanRecipe<ItemStack, Ingredient> {
    public ItemBanRecipe(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock, Ingredient ingredient) {
        super(AetherRecipes.RecipeTypes.ITEM_PLACEMENT_BAN, id, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
    }

    public boolean banItem(Level level, BlockPos pos, Direction face, ItemStack stack) {
        if (this.matches(level, pos, stack)) {
            if (AetherEventDispatch.isItemPlacementBanned(stack)) {
                AetherEventDispatch.onPlacementSpawnParticles(level, pos, face, stack);
                return true;
            }
        }
        return false;
    }

//    public boolean banBlock(Level level, BlockPos pos, BlockState state) {
//        if (this.matches(level, pos, null, state)) {
//            if (AetherEventDispatch.isBlockPlacementBanned(state)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean matches(Level level, BlockPos pos, ItemStack stack) { //TODO: I think this is the correct conditions?
        if (this.bypassBlock.isEmpty() || !this.bypassBlock.test(level.getBlockState(pos.below()))) {
            if (this.dimensionTypeKey != null) {
                return level.dimensionTypeRegistration().is(this.dimensionTypeKey) && this.getIngredient().test(stack);
            } else if (this.dimensionTypeTag != null) {
                return LevelUtil.inTag(level, this.dimensionTypeTag) && this.getIngredient().test(stack);
            } else {
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

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ItemBanRecipe> {
        public Serializer() {
            super();
        }

        @Nonnull
        @Override
        public ItemBanRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
            ResourceKey<DimensionType> dimensionTypeKey = null;
            TagKey<DimensionType> dimensionTypeTag = null;
            if (serializedRecipe.has("dimension_type")) {
                String biomeName = GsonHelper.getAsString(serializedRecipe, "dimension_type");
                if (biomeName.startsWith("#")) {
                    dimensionTypeTag = BlockStateRecipeUtil.dimensionTypeTagFromJson(serializedRecipe);
                } else {
                    dimensionTypeKey = BlockStateRecipeUtil.dimensionTypeKeyFromJson(serializedRecipe);
                }
            }

            BlockStateIngredient bypassBlock = BlockStateIngredient.EMPTY;
            if (serializedRecipe.has("bypass")) {
                boolean isBypassArray = GsonHelper.isArrayNode(serializedRecipe, "bypass");
                JsonElement bypassElement = isBypassArray ? GsonHelper.getAsJsonArray(serializedRecipe, "bypass") : GsonHelper.getAsJsonObject(serializedRecipe, "bypass");
                bypassBlock = BlockStateIngredient.fromJson(bypassElement);
            }

            if (!serializedRecipe.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
            JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonElement);

            return new ItemBanRecipe(recipeId, dimensionTypeKey, dimensionTypeTag, bypassBlock, ingredient);
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
            BlockStateRecipeUtil.writeDimensionTypeKey(buf, recipe.getDimensionTypeKey());
            BlockStateRecipeUtil.writeDimensionTypeTag(buf, recipe.getDimensionTypeTag());
            recipe.getBypassBlock().toNetwork(buf);
            recipe.getIngredient().toNetwork(buf);
        }
    }
}
