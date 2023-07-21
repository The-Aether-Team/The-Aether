package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

/**
 * ItemUseConvertEvent is fired after an item that can convert blocks is used, but before the block is converted by the recipe.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the block conversion will happen and the item will be consumed.
 * <br>
 * This event does not have a result. {@link net.minecraftforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 * <br>
 * This event is fired on both {@link LogicalSide sides}.<br>
 * <br>
 * If this event is canceled, block conversion will not happen and the item will not be consumed.
 */
@Cancelable
public class ItemUseConvertEvent extends PlayerEvent {
	private final LevelAccessor level;
	private final BlockPos pos;
	private final ItemStack itemStack;
	private final RecipeType<?> recipeType;
	private final BlockState oldBlockState;
	private BlockState newBlockState;

	/**
	 * @param player The {@link Player} using the conversion item.
	 * @param level The {@link LevelAccessor} that the conversion occurs in.
	 * @param pos The {@link BlockPos} the conversion occurs at.
	 * @param itemStack The {@link ItemStack} being used for the conversion.
	 * @param oldBlockState The old {@link BlockState} that is to be converted.
	 * @param newBlockState The original result {@link BlockState} from the conversion.
	 * @param recipe The {@link RecipeType}.
	 */
	public ItemUseConvertEvent(Player player, LevelAccessor level, BlockPos pos, ItemStack itemStack, BlockState oldBlockState, BlockState newBlockState, RecipeType<?> recipe) {
		super(player);
		this.level = level;
		this.pos = pos;
		this.itemStack = itemStack;
		this.oldBlockState = oldBlockState;
		this.newBlockState = newBlockState;
		this.recipeType = recipe;
	}

	/**
	 * @return The {@link LevelAccessor} that the conversion occurs in.
	 */
	public LevelAccessor getLevel() {
		return this.level;
	}

	/**
	 * @return The {@link BlockPos} the conversion occurs at.
	 */
	public BlockPos getPos() {
		return this.pos;
	}

	/**
	 * @return The {@link ItemStack} being used for the conversion.
	 */
	public ItemStack getItemStack() {
		return this.itemStack;
	}

	/**
	 * @return The {@link RecipeType}.
	 */
	public RecipeType<?> getRecipeType() {
		return this.recipeType;
	}

	/**
	 * @return The old {@link BlockState} that is to be converted.
	 */
	public BlockState getOldBlockState() {
		return this.oldBlockState;
	}

	/**
	 * @return The result {@link BlockState} from the conversion.
	 */
	public BlockState getNewBlockState() {
		return this.newBlockState;
	}

	/**
	 * Sets a new block to result from the conversion.
	 * @param newBlockState The new {@link BlockState}.
	 */
	public void setNewBlockState(BlockState newBlockState) {
		this.newBlockState = newBlockState;
	}
}
