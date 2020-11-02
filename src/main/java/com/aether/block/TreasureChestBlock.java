package com.aether.block;

import static net.minecraft.block.ChestBlock.getDirectionToAttached;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.aether.api.dungeon.DungeonType;
import com.aether.item.DungeonKeyItem;
import com.aether.tileentity.AetherTileEntityTypes;
import com.aether.tileentity.TreasureChestTileEntity;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;

public class TreasureChestBlock extends AbstractChestBlock<TreasureChestTileEntity> implements IWaterLoggable {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);

	private static final TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<IInventory>> field_220109_i = new TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<IInventory>>() {
		@Override
		public Optional<IInventory> func_225539_a_(TreasureChestTileEntity chest1, TreasureChestTileEntity chest2) {
			return Optional.of(new DoubleSidedInventory(chest1, chest2));
		}

		@Override
		public Optional<IInventory> func_225538_a_(TreasureChestTileEntity chest) {
			return Optional.of(chest);
		}

		@Override
		public Optional<IInventory> func_225537_b_() {
			return Optional.empty();
		}
	};
	private static final TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<INamedContainerProvider>> getContainerProvider = new TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<INamedContainerProvider>>() {
		@Override
		public Optional<INamedContainerProvider> func_225539_a_(TreasureChestTileEntity chest1, TreasureChestTileEntity chest2) {
			final IInventory iinventory = new DoubleSidedInventory(chest1, chest2);
			return Optional.of(new INamedContainerProvider() {
				@Override
				@Nullable
				public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
					if (chest1.canOpen(player) && chest2.canOpen(player)) {
						chest1.fillWithLoot(inventory.player);
						chest2.fillWithLoot(inventory.player);
						return ChestContainer.createGeneric9X6(id, inventory, iinventory);
					}
					else {
						return null;
					}
				}

				@Override
				public ITextComponent getDisplayName() {
					if (chest1.hasCustomName()) {
						return chest1.getDisplayName();
					}
					else {
						return chest2.hasCustomName()? chest2.getDisplayName() : new TranslationTextComponent("container.chestDouble");
					}
				}
			});
		}

		@Override
		public Optional<INamedContainerProvider> func_225538_a_(TreasureChestTileEntity chest) {
			return Optional.of(chest);
		}

		@Override
		public Optional<INamedContainerProvider> func_225537_b_() {
			return Optional.empty();
		}
	};

	protected TreasureChestBlock(Block.Properties properties, Supplier<TileEntityType<? extends TreasureChestTileEntity>> tileEntityTypeIn) {
		super(properties, tileEntityTypeIn);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(TYPE, ChestType.SINGLE).with(WATERLOGGED, false));
	}
	
	public TreasureChestBlock(Block.Properties properties) {
		this(properties, () -> AetherTileEntityTypes.TREASURE_CHEST);
	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	 */
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state. For
	 * example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart. Note that this method should ideally consider only the specific face passed
	 * in.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
		BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}

		if (facingState.getBlock() == this && facing.getAxis().isHorizontal()) {
			ChestType chesttype = facingState.get(TYPE);
			if (stateIn.get(TYPE) == ChestType.SINGLE && chesttype != ChestType.SINGLE
				&& stateIn.get(FACING) == facingState.get(FACING)
				&& getDirectionToAttached(facingState) == facing.getOpposite()) {
				return stateIn.with(TYPE, chesttype.opposite());
			}
		}
		else if (getDirectionToAttached(stateIn) == facing) {
			return stateIn.with(TYPE, ChestType.SINGLE);
		}

		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(TYPE) == ChestType.SINGLE) {
			return SHAPE;
		}
		else {
			switch (getDirectionToAttached(state)) {
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

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		ChestType chesttype = ChestType.SINGLE;
		Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		boolean flag = context.func_225518_g_(); /// is secondary use
		Direction direction1 = context.getFace();
		if (direction1.getAxis().isHorizontal() && flag) {
			Direction direction2 = this.getDirectionToAttach(context, direction1.getOpposite());
			if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
				direction = direction2;
				chesttype = direction2.rotateYCCW() == direction1.getOpposite()? ChestType.RIGHT : ChestType.LEFT;
			}
		}

		if (chesttype == ChestType.SINGLE && !flag) {
			if (direction == this.getDirectionToAttach(context, direction.rotateY())) {
				chesttype = ChestType.LEFT;
			}
			else if (direction == this.getDirectionToAttach(context, direction.rotateYCCW())) {
				chesttype = ChestType.RIGHT;
			}
		}

		return this.getDefaultState().with(FACING, direction).with(TYPE, chesttype).with(WATERLOGGED,
			Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED)? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	/**
	 * Returns facing pointing to a chest to form a double chest with, null otherwise
	 */
	@Nullable
	private Direction getDirectionToAttach(BlockItemUseContext context, Direction direction) {
		BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction));
		return blockstate.getBlock() == this && blockstate.get(TYPE) == ChestType.SINGLE? blockstate.get(FACING) : null;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TreasureChestTileEntity) {
				((TreasureChestTileEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof IInventory) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	@SuppressWarnings("serial")
	private static void playLocalLockedSound(BlockPos pos) {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable() {
			@Override
			public void run() {
				Minecraft.getInstance().getConnection().getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
				//Minecraft.getInstance().getSoundHandler().play(new SimpleSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, chest1.getPos().getX(), chest1.getPos().getY(), chest1.getPos().getZ()));
			}
		});
	}
	
	private static final TileEntityMerger.ICallback<TreasureChestTileEntity, BiFunction<PlayerEntity, Hand, ActionResultType>> unlock = new TileEntityMerger.ICallback<TreasureChestTileEntity, BiFunction<PlayerEntity, Hand, ActionResultType>>() {

		@SuppressWarnings("serial")
		@Override
		public BiFunction<PlayerEntity, Hand, ActionResultType> func_225539_a_(TreasureChestTileEntity chest1, TreasureChestTileEntity chest2) {
			return (player, hand) -> {
				if (chest1.getKind() != chest2.getKind()) {
					return ActionResultType.FAIL;
				}
				boolean unlocked = false, messaged = false;
				ItemStack itemstack = player.getHeldItem(hand);
				if (chest1.isLocked() && itemstack.getItem() instanceof DungeonKeyItem) {
					DungeonKeyItem item = (DungeonKeyItem) itemstack.getItem();
					if (item.getDungeonType() == chest1.getKind()) {
						chest1.unlock();
						unlocked = true;
					} else {
						player.sendStatusMessage(new TranslationTextComponent("container.cannotUnlockWithKey", new TranslationTextComponent(chest1.getKind().getTranslationKey()), itemstack.getDisplayName()), true);
						player.getEntityWorld().playSound(null, chest1.getPos(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
						//player.getEntityWorld().playSound(chest1.getPos().getX(), chest1.getPos().getY(), chest1.getPos().getZ(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
						//playLocalLockedSound(chest1.getPos());
						messaged = true;
					}
				}
				if (chest2.isLocked() && (unlocked || itemstack.getItem() instanceof DungeonKeyItem)) {
					DungeonKeyItem item = (DungeonKeyItem) itemstack.getItem();
					if (item.getDungeonType() == chest2.getKind()) {
						chest2.unlock();
						unlocked = true;
					} else if (!messaged) {
						player.sendStatusMessage(new TranslationTextComponent("container.cannotUnlockWithKey", new TranslationTextComponent(chest2.getKind().getTranslationKey()), itemstack.getDisplayName()), true);
						player.getEntityWorld().playSound(null, chest2.getPos(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
						//player.getEntityWorld().playSound(chest2.getPos().getX(), chest2.getPos().getY(), chest2.getPos().getZ(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
						//playLocalLockedSound(chest2.getPos());
						messaged = true;
					}
				}
				if (messaged) {
					return ActionResultType.PASS;
				}
				if (unlocked) {
					itemstack.shrink(1);
					return ActionResultType.SUCCESS;
				}
				return ActionResultType.FAIL;
			};
		}
		
		@SuppressWarnings("serial")
		@Override
		public BiFunction<PlayerEntity, Hand, ActionResultType> func_225538_a_(TreasureChestTileEntity chest) {
			return (player, hand) -> {
				if (chest.isLocked()) {
					ItemStack itemstack = player.getHeldItem(hand);
					if (itemstack.getItem() instanceof DungeonKeyItem) {
						DungeonKeyItem item = (DungeonKeyItem) itemstack.getItem();
						if (item.getDungeonType() == chest.getKind()) {
							chest.unlock();
							itemstack.shrink(1);
							return ActionResultType.SUCCESS;
						} else {
							player.sendStatusMessage(new TranslationTextComponent("container.cannotUnlockWithKey", new TranslationTextComponent(chest.getKind().getTranslationKey()), itemstack.getDisplayName()), true);
							player.getEntityWorld().playSound(null, chest.getPos(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
							//player.getEntityWorld().playSound(chest.getPos().getX(), chest.getPos().getY(), chest.getPos().getZ(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
							//playLocalLockedSound(chest.getPos());
							return ActionResultType.PASS;
						}
					}
				}
				return ActionResultType.FAIL;
			};
		}

		@Override
		public BiFunction<PlayerEntity, Hand, ActionResultType> func_225537_b_() {
			return (player, hand) -> ActionResultType.FAIL;
		}
		
	};

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		}
		else {
			TileEntityMerger.ICallbackWrapper<? extends TreasureChestTileEntity> callbackWrapper = this.combine(state, worldIn, pos, false);
			ActionResultType result = callbackWrapper.apply(unlock).apply(player, handIn);
			if (result == ActionResultType.SUCCESS || result == ActionResultType.PASS) {
				return ActionResultType.SUCCESS;
			}
			INamedContainerProvider inamedcontainerprovider = callbackWrapper.apply(getContainerProvider).orElse(null);
			if (inamedcontainerprovider != null) {
				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof TreasureChestTileEntity) {
					//NetworkHooks.openGui((ServerPlayerEntity) player, inamedcontainerprovider);
					OptionalInt idOpt = player.openContainer(inamedcontainerprovider);
					if (idOpt.isPresent()) {
						player.addStat(this.getOpenStat());
					} else {
						player.sendStatusMessage(new TranslationTextComponent("container.isLocked", new TranslationTextComponent(this.getTranslationKey())), true);
						player.getEntityWorld().playSound(null, pos, SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
						//playLocalLockedSound(pos);
					}
				}
			}

			return ActionResultType.SUCCESS;
		}
	}

	protected Stat<ResourceLocation> getOpenStat() {
		return Stats.CUSTOM.get(Stats.OPEN_CHEST);
	}

	@Nullable
	public static IInventory func_226916_a_(TreasureChestBlock chest, BlockState state, World world, BlockPos pos, boolean p_226916_4_) {
		return chest.combine(state, world, pos, p_226916_4_).apply(field_220109_i).orElse((IInventory)null);
	}

	@Override
	public TileEntityMerger.ICallbackWrapper<? extends TreasureChestTileEntity> combine(BlockState state, World worldIn, BlockPos pos, boolean p_225536_4_) {
		BiPredicate<IWorld, BlockPos> bipredicate;
		TileEntity tileentity = worldIn.getTileEntity(pos);
		DungeonType dungeonType;
		if (tileentity instanceof TreasureChestTileEntity) {
			dungeonType = ((TreasureChestTileEntity)tileentity).getKind();
		} else {
			dungeonType = null;
		}
		if (p_225536_4_) {
			bipredicate = (_world, _pos) -> {
				TileEntity tileentity2 = _world.getTileEntity(_pos);
				DungeonType dungeonType2;
				if (tileentity2 instanceof TreasureChestTileEntity) {
					dungeonType2 = ((TreasureChestTileEntity)tileentity2).getKind();
				} else {
					dungeonType2 = null;
				}
				return dungeonType != dungeonType2;
			};
		}
		else {
			bipredicate = (_world, _pos) -> {
				if (ChestBlock.isBlocked(_world, _pos)) {
					return true;
				}
				TileEntity tileentity2 = _world.getTileEntity(_pos);
				DungeonType dungeonType2;
				if (tileentity2 instanceof TreasureChestTileEntity) {
					dungeonType2 = ((TreasureChestTileEntity)tileentity2).getKind();
				} else {
					dungeonType2 = null;
				}
				return dungeonType != dungeonType2;
			};
		}

		return TileEntityMerger.func_226924_a_(this.tileEntityType.get(), ChestBlock::func_226919_h_, ChestBlock::getDirectionToAttached, FACING, state, worldIn, pos, bipredicate);
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return this.combine(state, worldIn, pos, false).apply(getContainerProvider).orElse((INamedContainerProvider)null);
	}

	@OnlyIn(Dist.CLIENT)
	public static TileEntityMerger.ICallback<TreasureChestTileEntity, Float2FloatFunction> func_226917_a_(final IChestLid ichestlid) {
		return new TileEntityMerger.ICallback<TreasureChestTileEntity, Float2FloatFunction>() {
			@Override
			public Float2FloatFunction func_225539_a_(TreasureChestTileEntity chest1, TreasureChestTileEntity chest2) {
				return (partialTicks) -> Math.max(chest1.getLidAngle(partialTicks), chest2.getLidAngle(partialTicks));
			}

			@Override
			public Float2FloatFunction func_225538_a_(TreasureChestTileEntity chest) {
				return chest::getLidAngle;
			}

			@Override
			public Float2FloatFunction func_225537_b_() {
				return ichestlid::getLidAngle;
			}
		};
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TreasureChestTileEntity();
	}

	public static boolean isBlocked(IWorld world, BlockPos pos) {
		return isBelowSolidBlock(world, pos) || isCatSittingOn(world, pos);
	}

	private static boolean isBelowSolidBlock(IBlockReader world, BlockPos posIn) {
		BlockPos blockpos = posIn.up();
		return world.getBlockState(blockpos).isNormalCube(world, blockpos);
	}

	private static boolean isCatSittingOn(IWorld world, BlockPos pos) {
		List<CatEntity> list = world.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
		if (!list.isEmpty()) {
			for (CatEntity catentity : list) {
				if (catentity.isSitting()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Deprecated
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	/**
	 * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Deprecated
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstoneFromInventory(func_226916_a_(this, blockState, worldIn, pos, false));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Deprecated
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@Deprecated
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
