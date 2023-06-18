package com.aetherteam.aether.event.dispatch;

import javax.annotation.Nullable;

import com.aetherteam.aether.event.events.*;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;

public class AetherEventDispatch {
	public static EggLayEvent onLayEgg(Entity entity, SoundEvent sound, float volume, float pitch, Item item) {
		EggLayEvent event = new EggLayEvent(entity, sound, volume, pitch, item);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static PlacementBanEvent.SpawnParticles onPlacementSpawnParticles(LevelAccessor world, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
		PlacementBanEvent.SpawnParticles event = new PlacementBanEvent.SpawnParticles(world, pos, face, stack, state);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static boolean isItemPlacementBanned(LevelAccessor world, BlockPos pos, ItemStack stack) {
		PlacementBanEvent.CheckItem event = new PlacementBanEvent.CheckItem(world, pos, stack.copy());
		MinecraftForge.EVENT_BUS.post(event);
		return event.isBanned();
	}

	public static boolean isBlockPlacementBanned(LevelAccessor world, BlockPos pos, BlockState state) {
		PlacementBanEvent.CheckBlock event = new PlacementBanEvent.CheckBlock(world, pos, state);
		MinecraftForge.EVENT_BUS.post(event);
		return event.isBanned();
	}

	public static PlacementConvertEvent onPlacementConvert(LevelAccessor world, BlockPos pos, BlockState oldState, BlockState newState)  {
		PlacementConvertEvent event = new PlacementConvertEvent(world, pos, oldState, newState);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
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

	public static ItemUseConvertEvent onItemUseConvert(Player player, LevelAccessor world, BlockPos pos, ItemStack stack, BlockState oldState, BlockState newState, RecipeType recipeType) {
		ItemUseConvertEvent event = new ItemUseConvertEvent(player, world, pos, stack, oldState, newState, recipeType);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}

	public static boolean onTriggerTrap(Player player, Level level, BlockPos pos, BlockState state) {
		TriggerTrapEvent event = new TriggerTrapEvent(player, level, pos, state);
		MinecraftForge.EVENT_BUS.post(event);
		return !event.isCanceled();
	}

	public static ValkyrieTeleportEvent onValkyrieTeleport(LivingEntity entity, double targetX, double targetY, double targetZ) {
		ValkyrieTeleportEvent event = new ValkyrieTeleportEvent(entity, targetX, targetY, targetZ);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
	}
}
