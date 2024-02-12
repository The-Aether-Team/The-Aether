package com.aetherteam.aether.recipe.recipes.ban;

import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockBanRecipe extends AbstractPlacementBanRecipe<BlockState, BlockStateIngredient> {
    public BlockBanRecipe(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, BlockStateIngredient ingredient) {
        super(AetherRecipeTypes.BLOCK_PLACEMENT_BAN.get(), biomeKey, biomeTag, bypassBlock, ingredient);
    }

    public BlockBanRecipe(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock) {
        this(biomeKey, biomeTag, bypassBlock, BlockStateIngredient.EMPTY);
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

        @Nullable
        @Override
        public BlockBanRecipe fromNetwork(FriendlyByteBuf buffer) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buffer);
            BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
            return new BlockBanRecipe(biomeKey, biomeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BlockBanRecipe recipe) {
            super.toNetwork(buffer, recipe);
            recipe.getIngredient().toNetwork(buffer);
        }
    }
}