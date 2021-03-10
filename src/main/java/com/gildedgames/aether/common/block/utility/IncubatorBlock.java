package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.common.entity.tile.IncubatorTileEntity;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock;

public class IncubatorBlock extends ContainerBlock {
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

	public IncubatorBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new IncubatorTileEntity();
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		}
		else {
			this.interactWith(worldIn, pos, player);
			return ActionResultType.SUCCESS;
		}
	}

	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		if (!worldIn.isClientSide) { 
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (IncubatorTileEntity) tileentity);
			}
		}
	}

	/*
	@Deprecated
	@Override
	public int getLightValue(BlockState state) {
		return state.get(LIT)? super.getLightValue(state) : 0;
	}
	*/

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				((IncubatorTileEntity)tileentity).setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				InventoryHelper.dropContents(worldIn, pos, (IncubatorTileEntity)tileentity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Deprecated
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Deprecated
	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
	}

	@Deprecated
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

}
