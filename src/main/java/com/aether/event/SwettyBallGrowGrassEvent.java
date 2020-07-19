package com.aether.event;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class SwettyBallGrowGrassEvent extends PlayerEvent {
	@Nonnull
	private final BlockState oldBlockState;
	@Nonnull
	private BlockState newBlockState;
	@Nonnull
	private final ItemStack itemStack;
	@Nonnull
	private final BlockPos pos;
	
	public SwettyBallGrowGrassEvent(@Nonnull ItemStack itemStack, @Nonnull BlockState oldBlockState, @Nonnull BlockState newBlockState, @Nonnull PlayerEntity player, @Nonnull BlockPos pos) {
		super(player);
		this.itemStack = itemStack;
		this.oldBlockState = oldBlockState;
		this.newBlockState = newBlockState;
		this.pos = pos;
	}
	
	@Nonnull
	public BlockState getOldBlockState() {
		return oldBlockState;
	}
	
	@Nonnull
	public BlockState getNewBlockState() {
		return newBlockState;
	}
	
	@Nonnull
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	@Nonnull
	public BlockPos getPos() {
		return pos;
	}
	
	public void setNewBlockState(@Nonnull BlockState newBlockState) {
		this.newBlockState = newBlockState;
	}
	
}
