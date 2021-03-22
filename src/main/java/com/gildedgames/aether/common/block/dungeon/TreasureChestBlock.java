package com.gildedgames.aether.common.block.dungeon;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.gildedgames.aether.core.api.registers.DungeonType;
import com.gildedgames.aether.common.item.misc.DungeonKeyItem;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import com.gildedgames.aether.common.entity.tile.TreasureChestTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

import net.minecraft.block.AbstractBlock;

public class TreasureChestBlock extends ChestBlock implements IWaterLoggable {
	private static final TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<INamedContainerProvider>> getContainerProvider = new TileEntityMerger.ICallback<TreasureChestTileEntity, Optional<INamedContainerProvider>>() {
		@Override
		public Optional<INamedContainerProvider> acceptDouble(TreasureChestTileEntity chest1, TreasureChestTileEntity chest2) {
			final IInventory iinventory = new DoubleSidedInventory(chest1, chest2);
			return Optional.of(new INamedContainerProvider() {
				@Override
				@Nullable
				public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
					if (chest1.canOpen(player) && chest2.canOpen(player)) {
						chest1.unpackLootTable(inventory.player);
						chest2.unpackLootTable(inventory.player);
						return ChestContainer.sixRows(id, inventory, iinventory);
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
		public Optional<INamedContainerProvider> acceptSingle(TreasureChestTileEntity chest) {
			return Optional.of(chest);
		}

		@Override
		public Optional<INamedContainerProvider> acceptNone() {
			return Optional.empty();
		}
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected TreasureChestBlock(AbstractBlock.Properties properties, Supplier<TileEntityType<? extends TreasureChestTileEntity>> tileEntityTypeIn) {
		super(properties, (Supplier) tileEntityTypeIn);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, false));
	}
	
	public TreasureChestBlock(AbstractBlock.Properties properties) {
		this(properties, AetherTileEntityTypes.TREASURE_CHEST::get);
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			ItemStack itemStack = player.getItemInHand(handIn);
			Item item = itemStack.getItem();
			CompoundNBT nbt = itemStack.getOrCreateTag();

			if (item instanceof DungeonKeyItem && !nbt.contains("Lock")) {
				TileEntity tile = worldIn.getBlockEntity(pos);

				if (tile instanceof TreasureChestTileEntity) {
					String key = ((TreasureChestTileEntity) tile).createKey(((DungeonKeyItem) item).getDungeonType());

					if (key != null) {
						nbt.putString("Lock", key);
					}
				}
			}
		}

		return super.use(state, worldIn, pos, player, handIn, hit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TileEntityMerger.ICallbackWrapper<? extends TreasureChestTileEntity> combine(BlockState state, World worldIn, BlockPos pos, boolean override) {
		BiPredicate<IWorld, BlockPos> bipredicate;
		TileEntity tileentity = worldIn.getBlockEntity(pos);
		DungeonType dungeonType;
		if (tileentity instanceof TreasureChestTileEntity) {
			dungeonType = ((TreasureChestTileEntity)tileentity).getKind();
		} else {
			dungeonType = null;
		}
		if (override) {
			bipredicate = (_world, _pos) -> {
				TileEntity tileentity2 = _world.getBlockEntity(_pos);
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
				if (ChestBlock.isChestBlockedAt(_world, _pos)) {
					return true;
				}
				TileEntity tileentity2 = _world.getBlockEntity(_pos);
				DungeonType dungeonType2;
				if (tileentity2 instanceof TreasureChestTileEntity) {
					dungeonType2 = ((TreasureChestTileEntity)tileentity2).getKind();
				} else {
					dungeonType2 = null;
				}
				return dungeonType != dungeonType2;
			};
		}

		return TileEntityMerger.combineWithNeigbour((TileEntityType<? extends TreasureChestTileEntity>) this.blockEntityType.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, worldIn, pos, bipredicate);
	}

	@Override
	@Nullable
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		return this.combine(state, worldIn, pos, false).apply(getContainerProvider).orElse((INamedContainerProvider)null);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new TreasureChestTileEntity();
	}
		
}
