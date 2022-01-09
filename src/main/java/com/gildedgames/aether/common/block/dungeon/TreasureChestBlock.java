package com.gildedgames.aether.common.block.dungeon;

import com.gildedgames.aether.common.entity.tile.TreasureChestBlockEntity;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TreasureChestBlock extends AbstractChestBlock<TreasureChestBlockEntity> implements SimpleWaterloggedBlock
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

	public TreasureChestBlock(BlockBehaviour.Properties properties, Supplier<BlockEntityType<? extends TreasureChestBlockEntity>> tileEntityTypeSupplier) {
		super(properties, tileEntityTypeSupplier);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
	}

	public TreasureChestBlock(BlockBehaviour.Properties properties) {
		this(properties, AetherTileEntityTypes.TREASURE_CHEST::get);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState stateOther, LevelAccessor world, BlockPos pos, BlockPos posOther) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return super.updateShape(state, direction, stateOther, world, pos, posOther);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

		return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof TreasureChestBlockEntity) {
				((TreasureChestBlockEntity) tileentity).setCustomName(stack.getHoverName());
			}
		}
	}

//	@Override
//	public float getDestroyProgress(BlockState state, Player player, BlockGetter world, BlockPos pos) {
//		BlockEntity tileEntity = world.getBlockEntity(pos);
//		if (tileEntity instanceof TreasureChestTileEntity) {
//			TreasureChestTileEntity treasureChest = (TreasureChestTileEntity) tileEntity;
//			float f = treasureChest.getLocked() ? state.getDestroySpeed(world, pos) : 3.0F;
//			if (f == -1.0F) {
//				return 0.0F;
//			} else {
//				int i = net.minecraftforge.common.ForgeHooks.canEntityDestroy(state, player, world, pos) ? 30 : 100;
//				return player.getDigSpeed(state, pos) / f / (float) i;
//			}
//		}
//		return super.getDestroyProgress(state, player, world, pos);
//	}

	@Override
	public float getExplosionResistance(BlockState state, BlockGetter world, BlockPos pos, Explosion explosion) {
		BlockEntity tileEntity = world.getBlockEntity(pos);
		if (tileEntity instanceof TreasureChestBlockEntity) {
			TreasureChestBlockEntity treasureChest = (TreasureChestBlockEntity) tileEntity;
			return treasureChest.getLocked() ? super.getExplosionResistance(state, world, pos, explosion) : 3.0F;
		}
		return super.getExplosionResistance(state, world, pos, explosion);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState stateOther, boolean flag) {
		if (!state.is(stateOther.getBlock())) {
			BlockEntity tileentity = world.getBlockEntity(pos);
			if (tileentity instanceof Container) {
				Containers.dropContents(world, pos, (Container)tileentity);
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, stateOther, flag);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof TreasureChestBlockEntity) {
				TreasureChestBlockEntity treasureChest = (TreasureChestBlockEntity) tileEntity;
				MenuProvider inamedcontainerprovider = this.getMenuProvider(state, world, pos);
				if (treasureChest.getLocked()) {
					ItemStack stack = player.getMainHandItem();
					if (treasureChest.tryUnlock(player)) {
						if (player instanceof ServerPlayer) {
							ServerPlayer serverplayerentity = (ServerPlayer) player;
							CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
							player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
						}
						stack.shrink(1);
					}
				} else if (inamedcontainerprovider != null) {
					player.openMenu(inamedcontainerprovider);
					player.awardStat(this.getOpenChestStat());
					PiglinAi.angerNearbyPiglins(player, true);
				}
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter reader, BlockPos pos, BlockState state) {
		ItemStack stack = super.getCloneItemStack(reader, pos, state);
		TreasureChestBlockEntity treasureChestBlockEntity = (TreasureChestBlockEntity) reader.getBlockEntity(pos);
		CompoundTag compound = new CompoundTag();
		compound.putBoolean("Locked", treasureChestBlockEntity.getLocked());
		compound.putString("Kind", treasureChestBlockEntity.getKind());
		stack.addTagElement("BlockEntityTag", compound);
		return stack;
	}

	protected Stat<ResourceLocation> getOpenChestStat() {
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level world, BlockPos pos, boolean flag) {
		return DoubleBlockCombiner.Combiner::acceptNone;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TreasureChestBlockEntity(pos, state);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState p_180641_1_, Level p_180641_2_, BlockPos p_180641_3_) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(p_180641_2_.getBlockEntity(p_180641_3_));
	}

	@Override
	public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
		return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
		return p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, WATERLOGGED);
	}

	@Override
	public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
		return false;
	}
}
