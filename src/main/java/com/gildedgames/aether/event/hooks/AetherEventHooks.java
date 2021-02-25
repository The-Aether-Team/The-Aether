package com.gildedgames.aether.event.hooks;

import javax.annotation.Nullable;

import com.gildedgames.aether.event.AetherBannedItemEvent;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.MinecraftForge;

public class AetherEventHooks
{
	public static void firePlayerEnchantedEvent(PlayerEntity entity, ItemStack stack) {
		// TODO
	}
	
	public static boolean isItemBanned(ItemStack itemStack) {
		AetherBannedItemEvent.Check event = new AetherBannedItemEvent.Check(itemStack.copy());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isBanned();
	}
	
	public static void onItemBanned(IWorld world, BlockPos pos, @Nullable Direction face, ItemStack itemStack) {
		AetherBannedItemEvent.SpawnParticles event = new AetherBannedItemEvent.SpawnParticles(world, pos, face, itemStack);
		MinecraftForge.EVENT_BUS.post(event);
	}
}
