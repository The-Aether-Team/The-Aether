package com.aether.block;

import com.aether.tileentity.IncubatorTileEntity;

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

public class IncubatorBlock extends ContainerBlock {
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

	protected IncubatorBlock(Block.Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(LIT);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new IncubatorTileEntity();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		}
		else {
			this.interactWith(worldIn, pos, player);
			return ActionResultType.SUCCESS;
		}
	}

	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		if (!worldIn.isRemote) { 
			TileEntity tileentity = worldIn.getTileEntity(pos);
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				((IncubatorTileEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (IncubatorTileEntity)tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Deprecated
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	@Deprecated
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	@Deprecated
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

}
