package com.aetherteam.aether.recipe.recipes.ban;

import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class BlockBanRecipe extends AbstractPlacementBanRecipe<BlockState, BlockStateIngredient> {
    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient) {
        super(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get(), id, biomeKey, biomeTag, bypassBlock, ingredient);
    }

    public BlockBanRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock) {
        this(id, biomeKey, biomeTag, bypassBlock, BlockStateIngredient.EMPTY);
    }

    /**
     * Checks if the recipe matches the given parameters using {@link AbstractPlacementBanRecipe#matches(Level, BlockPos, Object)}.<br><br>
     * Then checks an event hook through {@link AetherEventDispatch#isBlockPlacementBanned(LevelAccessor, BlockPos, BlockState)}.<br><br>
     * Before calling {@link AetherEventDispatch#onPlacementSpawnParticles(LevelAccessor, BlockPos, Direction, ItemStack, BlockState)} to spawn particles on block ban.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param state The {@link BlockState} being used that is being checked.
     * @return Whether the given {@link BlockState} is banned from placement.
     */
    public boolean banBlock(Level level, BlockPos pos, BlockState state) {
        if (this.matches(level, pos.below(), state)) {
            if (AetherEventDispatch.isBlockPlacementBanned(level, pos, state)) {
                AetherEventDispatch.onPlacementSpawnParticles(level, pos, null, null, state);
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.BLOCK_PLACEMENT_BAN.get();
    }

    public static class Serializer extends PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe> {
        public Serializer() {
            super(BlockBanRecipe::new);
        }

        @Override
        public BlockBanRecipe fromJson(ResourceLocation id, JsonObject json) {
            BlockBanRecipe recipe = super.fromJson(id, json);
            if (!json.has("ingredient")) {
                throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
            }
            JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
            BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);
            return new BlockBanRecipe(id, recipe.getBiomeKey(), recipe.getBiomeTag(), recipe.getBypassBlock(), ingredient);
        }

        @Nullable
        @Override
        public BlockBanRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buffer);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
            return new BlockBanRecipe(id, biomeKey, biomeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BlockBanRecipe recipe) {
            super.toNetwork(buffer, recipe);
            recipe.getIngredient().toNetwork(buffer);
        }
    }
}