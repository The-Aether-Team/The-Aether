package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nonnull;

@Cancelable
public class ItemUseConvertEvent extends PlayerEvent {
	private final LevelAccessor world;
	private final BlockPos pos;
	private final ItemStack itemStack;
	private final RecipeType recipeType;
	@Nonnull
	private final BlockState oldBlockState;
	@Nonnull
	private BlockState newBlockState;

	public ItemUseConvertEvent(Player player, LevelAccessor world, BlockPos pos, ItemStack itemStack, @Nonnull BlockState oldBlockState, @Nonnull BlockState newBlockState, RecipeType recipe) {
		super(player);
		this.world = world;
		this.pos = pos;
		this.itemStack = itemStack;
		this.oldBlockState = oldBlockState;
		this.newBlockState = newBlockState;
		this.recipeType = recipe;
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

	public RecipeType getRecipeType() {
		return this.recipeType;
	}

	@Nonnull
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
