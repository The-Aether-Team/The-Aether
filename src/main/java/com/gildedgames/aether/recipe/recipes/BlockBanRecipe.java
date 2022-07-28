package com.gildedgames.aether.recipe.recipes;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockBanRecipe extends AbstractPlacementBanRecipe<BlockState, BlockStateIngredient> {
    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient) {
        super(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get(), id, biomeKey, biomeTag, bypassBlock, ingredient);
    }

    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock) {
        this(id, biomeKey, biomeTag, bypassBlock, BlockStateIngredient.EMPTY);
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
        return AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get();
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
            return new BlockBanRecipe(recipeId, recipe.getBiomeKey(), recipe.getBiomeTag(), recipe.getBypassBlock(), ingredient);
        }

        @Nullable
        @Override
        public BlockBanRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            return new BlockBanRecipe(recipeId, biomeKey, biomeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, @Nonnull BlockBanRecipe recipe) {
            super.toNetwork(buf, recipe);
            recipe.getIngredient().toNetwork(buf);
        }
    }
}