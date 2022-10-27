package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.event.events.ItemUseConvertEvent;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface MatchEventRecipe {
    default boolean convert(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState, CommandFunction.CacheableFunction mcfunction) {
        if (this.matches(player, level, pos, stack, oldState, newState)) {
            level.setBlockAndUpdate(pos, newState);
            var serverLevel = (ServerLevel) level;
            var server = serverLevel.getServer();
            mcfunction.get(server.getFunctions()).ifPresent(command -> {
                var context = server.getFunctions().getGameLoopSender()
                        .withPosition(Vec3.atBottomCenterOf(pos))
                        .withLevel(serverLevel);
                server.getFunctions().execute(command, context);
            });
            return true;
        }
        return false;
    }

    default boolean matches(Player player, Level level, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
        ItemUseConvertEvent event = AetherEventDispatch.onItemUseConvert(player, level, pos, stack, oldState, newState);
        return !event.isCanceled();
    }
}
