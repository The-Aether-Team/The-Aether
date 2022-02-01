package com.gildedgames.aether.common.block.dungeon;

import com.gildedgames.aether.common.block.entity.TreasureChestBlockEntity;
import com.gildedgames.aether.common.registry.AetherBlockEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TreasureChestBlock extends AbstractChestBlock<TreasureChestBlockEntity> implements SimpleWaterloggedBlock
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

	public TreasureChestBlock(Properties properties, Supplier<BlockEntityType<? extends TreasureChestBlockEntity>> tileEntityTypeSupplier) {
		super(properties, tileEntityTypeSupplier);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
	}

	public TreasureChestBlock(Properties properties) {
		this(properties, AetherBlockEntityTypes.TREASURE_CHEST::get);
	}

	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new TreasureChestBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
		return pLevel.isClientSide ? createTickerHelper(blockEntityType, this.blockEntityType(), TreasureChestBlockEntity::lidAnimateTick) : null;
	}

	@Nonnull
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Nonnull
	@Override
	public BlockState updateShape(BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
	}

	@Nonnull
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return AABB;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.is(Fluids.WATER));
	}

	@Nonnull
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity livingEntity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
				treasureChestBlockEntity.setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	public float getDestroyProgress(@Nonnull BlockState state, @Nonnull Player player, @Nonnull BlockGetter level, @Nonnull BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			float f = treasureChestBlockEntity.getLocked() ? state.getDestroySpeed(level, pos) : 3.0F;
			if (f == -1.0F) {
				return 0.0F;
			} else {
				int i = net.minecraftforge.common.ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
				return player.getDigSpeed(state, pos) / f / (float) i;
			}
		}
		return super.getDestroyProgress(state, player, level, pos);
	}

	@Override
	public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		if (tileEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			return treasureChestBlockEntity.getLocked() ? super.getExplosionResistance(state, world, pos, explosion) : 3.0F;
		}
		return super.getExplosionResistance(state, world, pos, explosion);
	}

	@Override
	public void onRemove(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, BlockState stateOther, boolean flag) {
		if (!state.is(stateOther.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof Container container) {
				Containers.dropContents(level, pos, container);
				level.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, level, pos, stateOther, flag);
		}
	}

	@Nonnull
	@Override
	public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
				MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
				if (treasureChestBlockEntity.getLocked()) {
					ItemStack stack = player.getMainHandItem();
					if (treasureChestBlockEntity.tryUnlock(player)) {
						if (player instanceof ServerPlayer serverPlayer) {
							CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
							player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
						}
						stack.shrink(1);
					}
				} else if (!ChestBlock.isChestBlockedAt(level, pos) && menuProvider != null) {
					player.openMenu(menuProvider);
					player.awardStat(this.getOpenChestStat());
					PiglinAi.angerNearbyPiglins(player, true);
				}
			}
			return InteractionResult.CONSUME;
		}
	}

	@Nonnull
	@Override
	public ItemStack getCloneItemStack(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		ItemStack stack = super.getCloneItemStack(level, pos, state);
		TreasureChestBlockEntity treasureChestBlockEntity = (TreasureChestBlockEntity) level.getBlockEntity(pos);
		if (treasureChestBlockEntity != null) {
			CompoundTag compound = new CompoundTag();
			compound.putBoolean("Locked", treasureChestBlockEntity.getLocked());
			compound.putString("Kind", treasureChestBlockEntity.getKind());
			stack.addTagElement("BlockEntityTag", compound);
		}
		return stack;
	}

	protected Stat<ResourceLocation> getOpenChestStat() {
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}

	public BlockEntityType<? extends TreasureChestBlockEntity> blockEntityType() {
		return this.blockEntityType.get();
	}

	@Nonnull
	@Override
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, boolean flag) {
		return DoubleBlockCombiner.Combiner::acceptNone;
	}

	@Override
	public boolean hasAnalogOutputSignal(@Nonnull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}

	@Nonnull
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Nonnull
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, WATERLOGGED);
	}

	@Override
	public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
		return false;
	}

	@Override
	public void tick(@Nonnull BlockState state, ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			treasureChestBlockEntity.recheckOpen();
		}
	}
}
