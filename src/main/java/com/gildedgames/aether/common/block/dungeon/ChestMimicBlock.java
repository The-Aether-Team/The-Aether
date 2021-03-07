package com.gildedgames.aether.common.block.dungeon;

import com.gildedgames.aether.common.entity.monster.MimicEntity;
import com.gildedgames.aether.common.entity.tile.ChestMimicTileEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.function.BiPredicate;

public class ChestMimicBlock extends Block implements IWaterLoggable
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_SINGLE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

	public ChestMimicBlock(AbstractBlock.Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TYPE, ChestType.SINGLE).with(WATERLOGGED, Boolean.valueOf(false)));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		if (facingState.isIn(this) && facing.getAxis().isHorizontal()) {
			ChestType chesttype = facingState.get(TYPE);
			if (stateIn.get(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE && stateIn.get(FACING) == facingState.get(FACING) && getDirectionToAttached(facingState) == facing.getOpposite()) {
				return stateIn.with(TYPE, chesttype.opposite());
			}
		} else if (getDirectionToAttached(stateIn) == facing) {
			return stateIn.with(TYPE, ChestType.SINGLE);
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(TYPE) == ChestType.SINGLE) {
			return SHAPE_SINGLE;
		} else {
			switch(getDirectionToAttached(state)) {
				case NORTH:
				default:
					return SHAPE_NORTH;
				case SOUTH:
					return SHAPE_SOUTH;
				case WEST:
					return SHAPE_WEST;
				case EAST:
					return SHAPE_EAST;
			}
		}
	}

	public static Direction getDirectionToAttached(BlockState state) {
		Direction direction = state.get(FACING);
		return state.get(TYPE) == ChestType.LEFT ? direction.rotateY() : direction.rotateYCCW();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		ChestType chesttype = ChestType.SINGLE;
		Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = context.hasSecondaryUseForPlayer();
		Direction direction1 = context.getFace();
		if (direction1.getAxis().isHorizontal() && flag) {
			Direction direction2 = this.getDirectionToAttach(context, direction1.getOpposite());
			if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
				direction = direction2;
				chesttype = direction2.rotateYCCW() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
			}
		}

		if (chesttype == ChestType.SINGLE && !flag) {
			if (direction == this.getDirectionToAttach(context, direction.rotateY())) {
				chesttype = ChestType.LEFT;
			} else if (direction == this.getDirectionToAttach(context, direction.rotateYCCW())) {
				chesttype = ChestType.RIGHT;
			}
		}

		return this.getDefaultState().with(FACING, direction).with(TYPE, chesttype).with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Nullable
	private Direction getDirectionToAttach(BlockItemUseContext context, Direction direction) {
		BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction));
		return blockstate.isIn(this) && blockstate.get(TYPE) == ChestType.SINGLE ? blockstate.get(FACING) : null;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!ChestBlock.isBlocked(worldIn, pos) && !worldIn.isRemote) {
			spawnMimic(state, worldIn, pos);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, worldIn, pos, stack);
		spawnMimic(state, worldIn, pos);
	}

	private void spawnMimic(BlockState state, World worldIn, BlockPos pos) {
		Direction facing = state.get(FACING);
		float angle = facing.getHorizontalAngle();
		MimicEntity mimic = new MimicEntity(AetherEntityTypes.MIMIC.get(), worldIn);
		mimic.setPositionAndRotation(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, angle, 0.0F);
		mimic.setRotationYawHead(angle);
		worldIn.addEntity(mimic);
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		worldIn.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
		mimic.spawnExplosionParticle();
	}

	public TileEntityMerger.ICallbackWrapper<? extends ChestMimicTileEntity> combine(BlockState state, World world, BlockPos pos, boolean override) {
		BiPredicate<IWorld, BlockPos> bipredicate;
		if (override) {
			bipredicate = (worldIn, posIn) -> false;
		} else {
			bipredicate = ChestBlock::isBlocked;
		}

		return TileEntityMerger.func_226924_a_(AetherTileEntityTypes.CHEST_MIMIC.get(), ChestBlock::getChestMergerType, ChestBlock::getDirectionToAttached, FACING, state, world, pos, bipredicate);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ChestMimicTileEntity();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, TYPE, WATERLOGGED);
	}

	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
}
