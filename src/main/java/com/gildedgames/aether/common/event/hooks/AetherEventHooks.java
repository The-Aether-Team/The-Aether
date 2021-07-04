package com.gildedgames.aether.common.event.hooks;

import javax.annotation.Nullable;

import com.gildedgames.aether.common.event.events.AetherBannedItemEvent;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.events.SwetBallConvertEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.MinecraftForge;

public class AetherEventHooks
{
	public static void onItemBanned(IWorld world, BlockPos pos, @Nullable Direction face, ItemStack itemStack) {
		AetherBannedItemEvent.SpawnParticles event = new AetherBannedItemEvent.SpawnParticles(world, pos, face, itemStack);
		MinecraftForge.EVENT_BUS.post(event);
	}

	public static boolean isItemBanned(ItemStack itemStack) {
		AetherBannedItemEvent.Check event = new AetherBannedItemEvent.Check(itemStack.copy());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isBanned();
	}

	public static FreezeEvent.FreezeFromBlock onBlockFreezeFluid(IWorld world, BlockPos pos, FluidState fluidState, BlockState blockState, BlockState sourceBlock) {
		FreezeEvent.FreezeFromBlock event = new FreezeEvent.FreezeFromBlock(world, pos, fluidState, blockState, sourceBlock);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static FreezeEvent.FreezeFromItem onItemFreezeFluid(IWorld world, BlockPos pos, FluidState fluidState, BlockState blockState, ItemStack sourceItem) {
		FreezeEvent.FreezeFromItem event = new FreezeEvent.FreezeFromItem(world, pos, fluidState, blockState, sourceItem);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static SwetBallConvertEvent onSwetBallConvert(PlayerEntity player, IWorld world, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState) {
		SwetBallConvertEvent event = new SwetBallConvertEvent(player, world, pos, stack, oldState, newState);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}
}
