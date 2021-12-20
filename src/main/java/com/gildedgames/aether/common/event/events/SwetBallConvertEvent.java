package com.gildedgames.aether.common.event.events;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class SwetBallConvertEvent extends PlayerEvent
{
	private final LevelAccessor world;
	private final BlockPos pos;
	private final ItemStack itemStack;
	private final BlockState oldBlockState;
	@Nonnull
	private BlockState newBlockState;
	
	public SwetBallConvertEvent(Player player, LevelAccessor world, BlockPos pos, ItemStack itemStack, BlockState oldBlockState, @Nonnull BlockState newBlockState) {
		super(player);
		this.world = world;
		this.pos = pos;
		this.itemStack = itemStack;
		this.oldBlockState = oldBlockState;
		this.newBlockState = newBlockState;
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

	public BlockState getOldBlockState() {
		return this.oldBlockState;
	}

	@Nonnull
	public BlockState getNewBlockState() {
		return this.newBlockState;
	}
	
	public void setNewBlockState(@Nonnull BlockState newBlockState) {
		this.newBlockState = newBlockState;
	}
	
}
