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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public interface FreezingAccessory extends FreezingBehavior<ItemStack> {
    default void freezeTick(SlotContext context, ItemStack stack) { //todo: documentation
        LivingEntity livingEntity = context.entity();
        if (!(livingEntity instanceof Player player) || (!player.getAbilities().flying && !player.isSpectator())) {
            int damage = this.freezeBlocks(livingEntity.getLevel(), livingEntity.blockPosition(), stack, 1.9F);
            stack.hurtAndBreak(damage / 3, livingEntity, wearer -> CuriosApi.getCuriosHelper().onBrokenCurio(context));
        }
    }

    @Override
    default int freezeFromRecipe(Level level, ItemStack source, BlockPos pos, int flag) {
        if (!level.isClientSide()) {
            BlockState oldBlockState = level.getBlockState(pos);
            for (Recipe<?> recipe : level.getRecipeManager().getAllRecipesFor(AetherRecipeTypes.ACCESSORY_FREEZABLE.get())) {
                if (recipe instanceof AccessoryFreezableRecipe freezableRecipe) {
                    if (freezableRecipe.matches(level, pos, oldBlockState)) {
                        BlockState newBlockState = freezableRecipe.getResultState(oldBlockState);
                        return this.freezeBlockAt(level, source, oldBlockState, newBlockState, pos, flag);
                    }
                }
            }
        }
        return 0;
    }

    @Override
    default FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack source) {
        return AetherEventDispatch.onItemFreezeFluid(world, pos, fluidState, blockState, source);
    }
}
