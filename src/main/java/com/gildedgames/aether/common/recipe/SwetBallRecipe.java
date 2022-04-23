package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.common.event.events.SwetBallConvertEvent;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

public class SwetBallRecipe extends AbstractBlockStateRecipe {
    private final ResourceKey<Biome> biomeKey;
    private final TagKey<Biome> biomeTag;

    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        super(AetherRecipes.RecipeTypes.SWET_BALL_CONVERSION, id, ingredient, resultBlock, resultProperties);
        this.biomeKey = biomeKey;
        this.biomeTag = biomeTag;
    }

    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        this(id, ingredient, null, null, resultBlock, resultProperties);
    }

    @Override
    public boolean set(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState) {
        if (this.matches(level, pos, oldState)) {
            BlockState newState = this.getResultState(oldState);
            SwetBallConvertEvent event = AetherEventDispatch.onSwetBallConvert(player, level, pos, stack, oldState, newState);
            if (!event.isCanceled()) {
                level.setBlockAndUpdate(pos, newState);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(Level level, BlockPos pos, BlockState state) {
        if (this.biomeKey != null) {
            return level.getBiome(pos).is(this.biomeKey) && super.matches(level, pos, state);
        } else if (this.biomeTag != null) {
            return level.getBiome(pos).is(this.biomeTag) && super.matches(level, pos, state);
        } else {
            return super.matches(level, pos, state);
        }
    }

    @Nullable
    public ResourceKey<Biome> getBiomeKey() {
        return this.biomeKey;
    }

    @Nullable
    public TagKey<Biome> getBiomeTag() {
        return this.biomeTag;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.SWET_BALL_CONVERSION.get();
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
            return new SwetBallRecipe(recipeId, recipe.getIngredient(), biomeKey, biomeTag, recipe.getResultBlock(), recipe.getResultProperties());
        }

        @Nullable
        @Override
        public SwetBallRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
            Block blockResult = BlockStateRecipeUtil.readBlock(buf);
            Map<Property<?>, Comparable<?>> propertiesResult = BlockStateRecipeUtil.readProperties(buf, blockResult);
            return new SwetBallRecipe(recipeId, ingredient, biomeKey, biomeTag, blockResult, propertiesResult);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buf, SwetBallRecipe recipe) {
            if (recipe.getBiomeKey() != null) {
                BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getBiomeKey());
            }
            if (recipe.getBiomeTag() != null) {
                BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getBiomeTag());
            }
            super.toNetwork(buf, recipe);
        }
    }
}
