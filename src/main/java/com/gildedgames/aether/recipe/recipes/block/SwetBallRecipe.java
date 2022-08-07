package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.SwetBallConvertEvent;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SwetBallRecipe extends AbstractBiomeParameterRecipe {
    public SwetBallRecipe(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result) {
        super(AetherRecipeTypes.SWET_BALL_CONVERSION.get(), id, biomeKey, biomeTag, ingredient, result);
    }

    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient,BlockPropertyPair result) {
        this(id, null, null, ingredient, result);
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

    public static class Serializer extends BiomeParameterRecipeSerializer<SwetBallRecipe> {
        public Serializer() {
            super(SwetBallRecipe::new, SwetBallRecipe::new);
        }
    }
}
