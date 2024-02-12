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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ItemBanRecipe extends AbstractPlacementBanRecipe<ItemStack, Ingredient> {
    public ItemBanRecipe(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock, Ingredient ingredient) {
        super(AetherRecipeTypes.ITEM_PLACEMENT_BAN.get(), biomeKey, biomeTag, bypassBlock, ingredient);
    }

    public ItemBanRecipe(@Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient bypassBlock) {
        this(biomeKey, biomeTag, bypassBlock, Ingredient.EMPTY);
    }

    /**
     * Checks if the recipe matches the given parameters using {@link AbstractPlacementBanRecipe#matches(Level, BlockPos, Object)}.<br><br>
     * Then checks an event hook through {@link AetherEventDispatch#isItemPlacementBanned(LevelAccessor, BlockPos, ItemStack)}.<br><br>
     * Before calling {@link AetherEventDispatch#onPlacementSpawnParticles(LevelAccessor, BlockPos, Direction, ItemStack, BlockState)} to spawn particles on item ban.
     * @param level The {@link Level} the recipe is performed in.
     * @param pos The {@link BlockPos} the recipe is performed at.
     * @param direction The {@link Direction} face that is interacted with.
     * @param stack The {@link ItemStack} being used that is being checked.
     * @param spawnParticles A {@link Boolean} for whether to spawn particles.
     * @return Whether the given {@link ItemStack} is banned from placement.
     */
    public boolean banItem(Level level, BlockPos pos, Direction direction, ItemStack stack, boolean spawnParticles) {
        if (this.matches(level, pos, stack)) {
            if (AetherEventDispatch.isItemPlacementBanned(level, pos, stack)) {
                if (spawnParticles) {
                    AetherEventDispatch.onPlacementSpawnParticles(level, pos, direction, stack, null);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.ITEM_PLACEMENT_BAN.get();
    }

    public static class Serializer extends PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe> {
        public Serializer() {
            super(ItemBanRecipe::new);
        }

        @Nullable
        @Override
        public ItemBanRecipe fromNetwork(FriendlyByteBuf buffer) {
            ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
            TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
            BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buffer);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            return new ItemBanRecipe(biomeKey, biomeTag, bypassBlock, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ItemBanRecipe recipe) {
            super.toNetwork(buffer, recipe);
            recipe.getIngredient().toNetwork(buffer);
        }
    }
}
