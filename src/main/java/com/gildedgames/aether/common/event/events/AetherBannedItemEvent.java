package com.gildedgames.aether.common.event.events;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class AetherBannedItemEvent extends Event
{
	private final ItemStack itemStack;
	
	protected AetherBannedItemEvent(ItemStack itemStack) {
		this.itemStack = Preconditions.checkNotNull(itemStack, "Null item stack in AetherBannedItemEvent");
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	public static class Check extends AetherBannedItemEvent
	{
		private boolean banned = true;
		
		public Check(ItemStack itemStack) {
			super(itemStack);
		}
		
		public boolean isBanned() {
			return this.banned;
		}
		
		public void setBanned(boolean banned) {
			this.banned = banned;
		}
	}
	
	@Cancelable
	public static class SpawnParticles extends AetherBannedItemEvent
	{
		private final LevelAccessor world;
		private final BlockPos pos;
		@Nullable
		private final Direction face;
		
		public SpawnParticles(LevelAccessor worldIn, BlockPos pos, Direction face, ItemStack itemStack) {
			super(itemStack);
			this.world = Preconditions.checkNotNull(worldIn, "Null world in AetherBannedItemEvent");
			this.pos = Preconditions.checkNotNull(pos, "Null position in AetherBannedItemEvent");
			this.face = face;
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
	}
}
