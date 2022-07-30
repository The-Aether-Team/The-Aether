package com.gildedgames.aether.recipe.recipes;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.SwetBallConvertEvent;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SwetBallRecipe extends AbstractBiomeParameterRecipe {
    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockPropertyPair result) {
        super(AetherRecipeTypes.SWET_BALL_CONVERSION.get(), id, ingredient, biomeKey, biomeTag, result);
    }

    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient,BlockPropertyPair result) {
        this(id, ingredient, null, null, result);
    }

    public boolean convert(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState) {
        BlockState newState = this.getResultState(oldState);
        if (this.matches(player, level, pos, stack, oldState, newState)) {
            level.setBlockAndUpdate(pos, newState);
            return true;
        }
        return false;
    }

    public boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        SwetBallConvertEvent event = AetherEventDispatch.onSwetBallConvert(player, level, pos, stack, oldState, newState);
        if (!event.isCanceled()) {
            return this.matches(level, pos, oldState);
        } else {
            return false;
        }
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.SWET_BALL_CONVERSION.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<SwetBallRecipe> {
        public Serializer() {
            super(SwetBallRecipe::new);
        }

        @Nonnull
        @Override
        public SwetBallRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
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
            SwetBallRecipe recipe = super.fromJson(recipeId, serializedRecipe);
            return new SwetBallRecipe(recipeId, recipe.getIngredient(), biomeKey, biomeTag, recipe.getResult());
        }

        @Nullable
        @Override
        public SwetBallRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            BlockPropertyPair result = BlockStateRecipeUtil.readPair(buf);
            return new SwetBallRecipe(recipeId, ingredient, biomeKey, biomeTag, result);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, SwetBallRecipe recipe) {
            BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getBiomeKey());
            BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getBiomeTag());
            super.toNetwork(buf, recipe);
        }
    }
}
