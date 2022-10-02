package com.gildedgames.aether.item.accessories.abilities;

import com.gildedgames.aether.block.FreezingBehavior;
import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.recipes.block.AccessoryFreezableRecipe;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    /**
     * Freezes blocks around the wearer in a radius of 1.9 as long as they aren't flying or in spectator. This also damages the Ice accessory for every 3 blocks frozen.
     * @param context The {@link SlotContext} of the Curio.
     * @param stack The Curio {@link ItemStack}.
     */
    default void freezeTick(SlotContext context, ItemStack stack) {
        LivingEntity livingEntity = context.entity();
        if (!(livingEntity instanceof Player player) || (!player.getAbilities().flying && !player.isSpectator())) {
            int damage = this.freezeBlocks(livingEntity.getLevel(), livingEntity.blockPosition(), stack, 1.9F);
            stack.hurtAndBreak(damage / 3, livingEntity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(context));
        }
    }

    /**
     * Freezes blocks from one block to another using the {@link AetherRecipeTypes#ACCESSORY_FREEZABLE} recipe type.
     * @param level The {@link Level} to freeze the blocks in.
     * @param source The {@link ItemStack} that was the source of the freezing.
     * @param pos The {@link BlockPos} the freezing occurred at.
     * @param flag The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    @Override
    default int freezeFromRecipe(Level level, BlockPos pos, ItemStack source, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ACCESSORY_FREEZABLE.get())) {
                if (recipe instanceof AccessoryFreezableRecipe freezableRecipe) {
                    if (!fluidState.isEmpty() && fluidState.createLegacyBlock() != oldBlockState && oldBlockState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        if (freezableRecipe.matches(level, pos, fluidState.createLegacyBlock())) {
                            level.setBlock(pos, oldBlockState.setValue(BlockStateProperties.WATERLOGGED, false), flag);
                        }
                    } else {
                        if (oldBlockState.getFluidState().isEmpty()) {
                            if (freezableRecipe.matches(level, pos, oldBlockState)) {
                                BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                                return this.freezeBlockAt(level, pos, oldBlockState, newBlockState, source, flag);
                            }
                        } else {
                            oldBlockState = oldBlockState.getFluidState().createLegacyBlock();
                            if (freezableRecipe.matches(level, pos, oldBlockState)) {
                                level.destroyBlock(pos, true);
                                BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                                return this.freezeBlockAt(level, pos, oldBlockState, newBlockState, source, flag);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockState oldBlockState, BlockState newBlockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(level, pos, oldBlockState, newBlockState, source);
    }
}
