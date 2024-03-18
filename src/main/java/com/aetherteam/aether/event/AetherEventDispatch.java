package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class AetherEventDispatch {
	/**
	 * @see EggLayEvent
	 */
	public static EggLayEvent onLayEgg(Entity entity, SoundEvent sound, float volume, float pitch, Item item) {
		EggLayEvent event = new EggLayEvent(entity, sound, volume, pitch, item);
		event.sendEvent();
		return event;
	}

	/**
	 * @see PlacementBanEvent.SpawnParticles
	 */
	public static PlacementBanEvent.SpawnParticles onPlacementSpawnParticles(LevelAccessor level, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
		PlacementBanEvent.SpawnParticles event = new PlacementBanEvent.SpawnParticles(level, pos, face, stack, state);
		event.sendEvent();
		return event;
	}

	/**
	 * @see PlacementBanEvent.CheckItem
	 */
	public static boolean isItemPlacementBanned(LevelAccessor level, BlockPos pos, ItemStack stack) {
		PlacementBanEvent.CheckItem event = new PlacementBanEvent.CheckItem(level, pos, stack.copy());
		event.sendEvent();
		return event.isBanned();
	}

	/**
	 * @see PlacementBanEvent.CheckBlock
	 */
	public static boolean isBlockPlacementBanned(LevelAccessor level, BlockPos pos, BlockState state) {
		PlacementBanEvent.CheckBlock event = new PlacementBanEvent.CheckBlock(level, pos, state);
		event.sendEvent();
		return event.isBanned();
	}

	/**
	 * @see PlacementConvertEvent
	 */
	public static PlacementConvertEvent onPlacementConvert(LevelAccessor level, BlockPos pos, BlockState oldState, BlockState newState)  {
		PlacementConvertEvent event = new PlacementConvertEvent(level, pos, oldState, newState);
		event.sendEvent();
		return event;
	}

	/**
	 * @see FreezeEvent.FreezeFromBlock
	 */
	public static FreezeEvent.FreezeFromBlock onBlockFreezeFluid(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState fluidState, BlockState blockState, BlockState sourceBlock) {
		FreezeEvent.FreezeFromBlock event = new FreezeEvent.FreezeFromBlock(level, pos, origin, fluidState, blockState, sourceBlock);
		event.sendEvent();
		return event;
	}

	/**
	 * @see FreezeEvent.FreezeFromItem
	 */
	public static FreezeEvent.FreezeFromItem onItemFreezeFluid(LevelAccessor level, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack sourceItem) {
		FreezeEvent.FreezeFromItem event = new FreezeEvent.FreezeFromItem(level, pos, fluidState, blockState, sourceItem);
		event.sendEvent();
		return event;
	}

	/**
	 * @see ItemUseConvertEvent
	 */
	public static ItemUseConvertEvent onItemUseConvert(@Nullable Player player, LevelAccessor level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
		ItemUseConvertEvent event = new ItemUseConvertEvent(player, level, pos, stack, oldState, newState, recipeType);
		event.sendEvent();
		return event;
	}

	/**
	 * @see TriggerTrapEvent
	 */
	public static boolean onTriggerTrap(Player player, Level level, BlockPos pos, BlockState state) {
		TriggerTrapEvent event = new TriggerTrapEvent(player, level, pos, state);
		event.sendEvent();
		return !event.isCanceled();
	}

	/**
	 * @see ValkyrieTeleportEvent
	 */
	public static ValkyrieTeleportEvent onValkyrieTeleport(LivingEntity entity, double targetX, double targetY, double targetZ) {
		ValkyrieTeleportEvent event = new ValkyrieTeleportEvent(entity, targetX, targetY, targetZ);
		event.sendEvent();
		return event;
	}
}
