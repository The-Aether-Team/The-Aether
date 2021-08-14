package com.gildedgames.aether.common.event.events;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class SwetBallConvertEvent extends PlayerEvent
{
	private final IWorld world;
	private final BlockPos pos;
	private final ItemStack itemStack;
	private final BlockState oldBlockState;
	@Nonnull
	private BlockState newBlockState;
	
	public SwetBallConvertEvent(PlayerEntity player, IWorld world, BlockPos pos, ItemStack itemStack, BlockState oldBlockState, @Nonnull BlockState newBlockState) {
		super(player);
		this.world = world;
		this.pos = pos;
		this.itemStack = itemStack;
		this.oldBlockState = oldBlockState;
		this.newBlockState = newBlockState;
	}

	public IWorld getWorld() {
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
