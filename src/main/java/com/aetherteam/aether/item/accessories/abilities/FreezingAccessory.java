package com.aetherteam.aether.item.accessories.abilities;

import com.aetherteam.aether.block.FreezingBehavior;
import com.aetherteam.aether.event.AetherEventDispatch;
import com.aetherteam.aether.event.FreezeEvent;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.block.AccessoryFreezableRecipe;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    /**
     * Freezes blocks around the wearer in a radius of 1.9 as long as they aren't flying or in spectator. This also damages the Ice accessory for every 3 blocks frozen.
     * @param context The {@link SlotReference} of the Trinket.
     * @param stack The Trinket {@link ItemStack}.
     * @param livingEntity The {@link LivingEntity} of the Trinket.
     */
    default void freezeTick(SlotReference context, ItemStack stack, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player) || (!player.getAbilities().flying && !player.isSpectator())) {
            int damage = this.freezeBlocks(livingEntity.level(), livingEntity.blockPosition(), stack, 1.9F);
            stack.hurtAndBreak(damage / 3, livingEntity, wearer -> TrinketsApi.onTrinketBroken(stack, context, livingEntity));
        }
    }

    /**
     * Freezes blocks from one block to another using the {@link AetherRecipeTypes#ACCESSORY_FREEZABLE} recipe type.
     * @param level The {@link Level} to freeze the blocks in.
     * @param pos The {@link BlockPos} the freezing occurred at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param flag The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, BlockPos origin, ItemStack source, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ACCESSORY_FREEZABLE.get())) {
                if (recipe instanceof AccessoryFreezableRecipe freezableRecipe) {
                    if (fluidState.isEmpty() || oldBlockState.is(fluidState.createLegacyBlock().getBlock())) { // Default freezing behavior.
                        if (freezableRecipe.matches(level, pos, oldBlockState)) {
                            BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                            CommandFunction.CacheableFunction function = freezableRecipe.getFunction();
                            return this.freezeBlockAt(level, pos, origin, oldBlockState, newBlockState, function, source, flag);
                        }
                    } else if (!oldBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) { // Breaks a block before freezing if it has a FluidState attached by default (this is different from waterlogging for blocks like Kelp and Seagrass).
                        oldBlockState = fluidState.createLegacyBlock();
                        if (freezableRecipe.matches(level, pos, oldBlockState)) {
                            level.destroyBlock(pos, true);
                            BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                            CommandFunction.CacheableFunction function = freezableRecipe.getFunction();
                            return this.freezeBlockAt(level, pos, origin, oldBlockState, newBlockState, function, source, flag);
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(level, pos, oldBlockState, newBlockState, source);
    }
}
