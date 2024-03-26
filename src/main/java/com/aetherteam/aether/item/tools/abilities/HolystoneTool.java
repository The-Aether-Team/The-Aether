package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface HolystoneTool {
    /**
     * Drops an Ambrosium Shard at a block's position with a 1/50 chance if the call isn't clientside, the destroy speed is greater than 0, and the tool is correct for the block.
     *
     * @param player The {@link Player} that mined the block.
     * @param level  The {@link Level} of the block.
     * @param pos    The {@link BlockPos} of the block.
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack, BlockState)
     */
    default void dropAmbrosium(Player player, Level level, BlockPos pos, ItemStack stack, BlockState state) {
        if (!level.isClientSide() && state.getDestroySpeed(level, pos) > 0 && stack.isCorrectToolForDrops(state) && player.getRandom().nextInt(50) == 0) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
            level.addFreshEntity(itemEntity);
        }
    }
}
