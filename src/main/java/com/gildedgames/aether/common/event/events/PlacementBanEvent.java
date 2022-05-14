package com.gildedgames.aether.common.event.events;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class PlacementBanEvent extends Event {
	public static class CheckItem extends PlacementBanEvent {
		private boolean banned = true;
		private final ItemStack itemStack;

		public CheckItem(ItemStack itemStack) {
			this.itemStack = Preconditions.checkNotNull(itemStack, "Null itemStack in PlacementBanEvent");
		}

		public ItemStack getItemStack() {
			return this.itemStack;
		}

		public boolean isBanned() {
			return this.banned;
		}

		public void setBanned(boolean banned) {
			this.banned = banned;
		}
	}
	
	public static class CheckBlock extends PlacementBanEvent {
		private boolean banned = true;
		private final BlockState blockState;
		
		public CheckBlock(BlockState blockState) {
			this.blockState = Preconditions.checkNotNull(blockState, "Null blockState in PlacementBanEvent");
		}

		public BlockState getBlockState() {
			return this.blockState;
		}

		public boolean isBanned() {
			return this.banned;
		}
		
		public void setBanned(boolean banned) {
			this.banned = banned;
		}
	}
	
	@Cancelable
	public static class SpawnParticles extends PlacementBanEvent {
		private final LevelAccessor world;
		private final BlockPos pos;
		@Nullable
		private final Direction face;
		@Nullable
		private final ItemStack itemStack;
		
		public SpawnParticles(LevelAccessor worldIn, BlockPos pos, Direction face, @Nullable ItemStack itemStack) {
			this.world = Preconditions.checkNotNull(worldIn, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.face = face;
			this.itemStack = itemStack;
		}
		
		public LevelAccessor getWorld() {
			return this.world;
		}
		
		public BlockPos getPos() {
			return this.pos;
		}
		
		@Nullable
		public Direction getFace() {
			return this.face;
		}

		@Nullable
		public ItemStack getItemStack() {
			return this.itemStack;
		}
	}
}
