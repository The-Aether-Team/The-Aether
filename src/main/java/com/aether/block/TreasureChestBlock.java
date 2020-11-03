package com.aether.block;

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

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TreasureChestBlock extends ChestBlock implements IWaterLoggable {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected TreasureChestBlock(Block.Properties properties, Supplier<TileEntityType<? extends TreasureChestTileEntity>> tileEntityTypeIn) {
		super(properties, (Supplier) tileEntityTypeIn);
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
	
//	@SuppressWarnings("serial")
//	private static void playLocalLockedSound(BlockPos pos) {
//		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable() {
//			@Override
//			public void run() {
//				Minecraft.getInstance().getConnection().getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
//				//Minecraft.getInstance().getSoundHandler().play(new SimpleSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F, chest1.getPos().getX(), chest1.getPos().getY(), chest1.getPos().getZ()));
//			}
//		});
//	}
	
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

	@SuppressWarnings("unchecked")
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

		return TileEntityMerger.func_226924_a_((TileEntityType<? extends TreasureChestTileEntity>) this.tileEntityType.get(), ChestBlock::func_226919_h_, ChestBlock::getDirectionToAttached, FACING, state, worldIn, pos, bipredicate);
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return this.combine(state, worldIn, pos, false).apply(getContainerProvider).orElse((INamedContainerProvider)null);
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TreasureChestTileEntity();
	}
		
}
