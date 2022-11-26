package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class AmbrosiumRecipe extends AbstractBlockStateRecipe implements MatchEventRecipe {
    public AmbrosiumRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        super(AetherRecipeTypes.AMBROSIUM_ENCHANTING.get(), id, ingredient, result, function);
    }

    public boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        return MatchEventRecipe.super.matches(player, level, pos, stack, oldState, newState) && this.matches(level, pos, oldState);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.AMBROSIUM_ENCHANTING.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<AmbrosiumRecipe> {
        public Serializer() {
            super(AmbrosiumRecipe::new);
        }
    }
}