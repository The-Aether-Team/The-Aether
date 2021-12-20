package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.common.entity.tile.IncubatorTileEntity;

import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class IncubatorBlock extends BaseEntityBlock {
	public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

	public IncubatorBlock(BlockBehaviour.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new IncubatorTileEntity(blockPos, blockState);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		else {
			this.interactWith(worldIn, pos, player);
			return InteractionResult.SUCCESS;
		}
	}

	protected void interactWith(Level worldIn, BlockPos pos, Player player) {
		if (!worldIn.isClientSide) { 
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				//NetworkHooks.openGui((ServerPlayer) player, (IncubatorTileEntity) tileentity);
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
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				((IncubatorTileEntity)tileentity).setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof IncubatorTileEntity) {
				Containers.dropContents(worldIn, pos, (IncubatorTileEntity)tileentity);
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
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
	}

	@Deprecated
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

}
