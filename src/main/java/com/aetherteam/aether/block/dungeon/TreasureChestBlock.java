package com.aetherteam.aether.block.dungeon;

import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.TreasureChestBlockEntity;
import com.aetherteam.aether.integration.lootr.AetherLootrPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.ModList;

import java.util.function.Supplier;

/**
 * Mostly copied from {@link ChestBlock}.
 */
public class TreasureChestBlock extends AbstractChestBlock<TreasureChestBlockEntity> implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty IS_LOOT_CONTAINER = BooleanProperty.create("is_loot");
	protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);

	public TreasureChestBlock(Properties properties) {
		this(properties, AetherBlockEntityTypes.TREASURE_CHEST::get);
	}

	public TreasureChestBlock(Properties properties, Supplier<BlockEntityType<? extends TreasureChestBlockEntity>> blockEntityTypeSupplier) {
		super(properties, blockEntityTypeSupplier);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false).setValue(IS_LOOT_CONTAINER, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, IS_LOOT_CONTAINER);
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TreasureChestBlockEntity(pos, state);
	}

	public BlockEntityType<? extends TreasureChestBlockEntity> blockEntityType() {
		return this.blockEntityType.get();
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide()
				? createTickerHelper(blockEntityType, this.blockEntityType(), TreasureChestBlockEntity::lidAnimateTick)
				: createTickerHelper(blockEntityType, this.blockEntityType(), TreasureChestBlockEntity::serverTick);
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			treasureChestBlockEntity.recheckOpen();
		}
	}

	/**
	 * [CODE COPY] - {@link ChestBlock#use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult)}.<br><br>
	 * Handles behavior for checking if a chest is locked and being able to unlock the chest.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param player The {@link Player} interacting with the block.
	 * @param hand The {@link InteractionHand} the player interacts with.
	 * @param hit The {@link BlockHitResult} of the interaction.
	 * @return The {@link InteractionResult} of the interaction.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
				MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
				if (ModList.get().isLoaded("lootr") && state.getValue(IS_LOOT_CONTAINER)) {
					if (treasureChestBlockEntity.getLootTable() != null) {
						menuProvider = AetherLootrPlugin.getTreasureMenu((ServerPlayer) player, treasureChestBlockEntity);
					}
				}
				if (treasureChestBlockEntity.getLocked()) {
					ItemStack stack = player.getMainHandItem();
					if (treasureChestBlockEntity.tryUnlock(player)) {
						if (player instanceof ServerPlayer) {
							player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
						}
						if (!player.getAbilities().instabuild) {
							stack.shrink(1);
						}
					}
				} else if (!ChestBlock.isChestBlockedAt(level, pos) && menuProvider != null) {
					if (treasureChestBlockEntity.getOpeners().add(player.getUUID())) {
//						treasureChestBlockEntity.setChanged();
					}
					player.openMenu(menuProvider);
					player.awardStat(Stats.CUSTOM.get(Stats.OPEN_CHEST));
					PiglinAi.angerNearbyPiglins(player, true);
				}
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidState.is(Fluids.WATER));
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
				treasureChestBlockEntity.setCustomName(stack.getHoverName());
				treasureChestBlockEntity.setChanged();
			}
		}
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState stateOther, boolean flag) {
		if (!state.is(stateOther.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof Container container) {
				if (!(ModList.get().isLoaded("lootr") && container instanceof TreasureChestBlockEntity treasureChestBlockEntity && treasureChestBlockEntity.getLootTable() != null)) {
					Containers.dropContents(level, pos, container);
					level.updateNeighbourForOutputSignal(pos, this);
				}
			}
			super.onRemove(state, level, pos, stateOther, flag);
		}
	}

	/**
	 * Prevents Treasure Chests from being broken when they're locked, but allows it when they're unlocked.
	 * @param state The {@link BlockState} of the block.
	 * @param player The {@link Player} interacting with the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @return The {@link Float} for the destruction progress.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			float f = treasureChestBlockEntity.getLocked() ? state.getDestroySpeed(level, pos) : 3.0F;
			if (f < 0.0F) {
				return 0.0F;
			} else {
				int i = ForgeHooks.isCorrectToolForDrops(state, player) ? 30 : 100;
				return player.getDigSpeed(state, pos) / f / (float) i;
			}
		}
		return super.getDestroyProgress(state, player, level, pos);
	}

	/**
	 * Prevents Treasure Chests from being affected by explosions when they're locked, but allows it when they're unlocked.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param explosion The {@link Explosion} affecting the block.
	 * @return The {@link Float} for the explosion resistance.
	 */
	@Override
	public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			return treasureChestBlockEntity.getLocked() ? super.getExplosionResistance(state, level, pos, explosion) : 3.0F;
		}
		return super.getExplosionResistance(state, level, pos, explosion);
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean flag) {
		return DoubleBlockCombiner.Combiner::acceptNone;
	}

	/**
	 * Copies all the NBT data from the block entity to the item when using pick block on the Treasure Chest.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param state The {@link BlockState} of the block.
	 * @return The cloned {@link ItemStack} from the block.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		ItemStack stack = super.getCloneItemStack(level, pos, state);
		TreasureChestBlockEntity treasureChestBlockEntity = (TreasureChestBlockEntity) level.getBlockEntity(pos);
		if (treasureChestBlockEntity != null) {
			CompoundTag compound = new CompoundTag();
			compound.putBoolean("Locked", treasureChestBlockEntity.getLocked());
			compound.putString("Kind", treasureChestBlockEntity.getKind().toString());
			stack.addTagElement("BlockEntityTag", compound);
		}
		return stack;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, facingState, level, currentPos, facingPos);
	}
}
