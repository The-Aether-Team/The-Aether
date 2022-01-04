package com.gildedgames.aether.common.event.hooks;

import javax.annotation.Nullable;

import com.gildedgames.aether.common.event.events.AetherBannedItemEvent;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.events.SwetBallConvertEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;

public class AetherEventHooks
{
	public static void onItemBanned(LevelAccessor world, BlockPos pos, @Nullable Direction face, ItemStack itemStack) {
		AetherBannedItemEvent.SpawnParticles event = new AetherBannedItemEvent.SpawnParticles(world, pos, face, itemStack);
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static boolean isItemBanned(ItemStack itemStack) {
		AetherBannedItemEvent.Check event = new AetherBannedItemEvent.Check(itemStack.copy());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isBanned();
	}

	public static FreezeEvent.FreezeFromBlock onBlockFreezeFluid(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, BlockState sourceBlock) {
		FreezeEvent.FreezeFromBlock event = new FreezeEvent.FreezeFromBlock(world, pos, fluidState, blockState, sourceBlock);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static FreezeEvent.FreezeFromItem onItemFreezeFluid(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack sourceItem) {
		FreezeEvent.FreezeFromItem event = new FreezeEvent.FreezeFromItem(world, pos, fluidState, blockState, sourceItem);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static SwetBallConvertEvent onSwetBallConvert(Player player, LevelAccessor world, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
		SwetBallConvertEvent event = new SwetBallConvertEvent(player, world, pos, stack, oldState, newState);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}
}
