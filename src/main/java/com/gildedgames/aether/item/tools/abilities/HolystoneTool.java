package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface HolystoneTool {
    /**
     * Drops an Ambrosium Shard at a block's position if the call isn't clientside with a 1/50 chance.
     * @param player The {@link Player} that mined the block.
     * @param level The {@link Level} of the block.
     * @param pos The {@link BlockPos} of the block.
     * @see com.gildedgames.aether.event.hooks.AbilityHooks.ToolHooks#handleHolystoneToolAbility(Player, Level, BlockPos, ItemStack)
     */
    default void dropAmbrosium(Player player, Level level, BlockPos pos) {
        if (!level.isClientSide() && player.getRandom().nextInt(50) == 0) {
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(AetherItems.AMBROSIUM_SHARD.get()));
            level.addFreshEntity(itemEntity);
        }
    }
}
