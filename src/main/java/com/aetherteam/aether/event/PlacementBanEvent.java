package com.aetherteam.aether.event;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;

public class PlacementBanEvent extends Event {
	public static class CheckItem extends PlacementBanEvent {
		private boolean banned = true;
		private final LevelAccessor world;
		private final BlockPos pos;
		private final ItemStack itemStack;

		public CheckItem(LevelAccessor worldIn, BlockPos pos, ItemStack itemStack) {
			this.world = Preconditions.checkNotNull(worldIn, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.itemStack = Preconditions.checkNotNull(itemStack, "Null itemStack in PlacementBanEvent");
		}

		public LevelAccessor getWorld() {
			return this.world;
		}

		public BlockPos getPos() {
			return this.pos;
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
		private final LevelAccessor world;
		private final BlockPos pos;
		private final BlockState blockState;

		public CheckBlock(LevelAccessor worldIn, BlockPos pos, BlockState blockState) {
			this.world = Preconditions.checkNotNull(worldIn, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.blockState = Preconditions.checkNotNull(blockState, "Null blockState in PlacementBanEvent");
		}

		public LevelAccessor getWorld() {
			return this.world;
		}

		public BlockPos getPos() {
			return this.pos;
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
		@Nullable
		private final BlockState blockState;

		public SpawnParticles(LevelAccessor worldIn, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
			this.world = Preconditions.checkNotNull(worldIn, "Null world in PlacementBanEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
			this.face = face;
			this.itemStack = stack;
			this.blockState = state;
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

		@Nullable
		public BlockState getBlockState() {
			return this.blockState;
		}
	}
}
