package com.gildedgames.aether.common.block.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.IncubatorContainer;

import com.gildedgames.aether.common.registry.AetherBlockEntityTypes;
import com.gildedgames.aether.common.registry.AetherTags;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.Map;

public class IncubatorBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer
{
	private static final int[] SLOTS_NS = {0};
	private static final int[] SLOTS_EW = {1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
	private int litTime;
	private int incubationProgress;
	private int incubationTotalTime = getTotalIncubationTime();
	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return switch (index) {
				case 0 -> IncubatorBlockEntity.this.litTime;
				case 1 -> IncubatorBlockEntity.this.incubationProgress;
				case 2 -> IncubatorBlockEntity.this.incubationTotalTime;
				default -> 0;
			};
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0 -> IncubatorBlockEntity.this.litTime = value;
				case 1 -> IncubatorBlockEntity.this.incubationProgress = value;
				case 2 -> IncubatorBlockEntity.this.incubationTotalTime = value;
			}
		}

		@Override
		public int getCount() {
			return 3;
		}
	};
	private static final Map<Item, Integer> incubatingMap = Maps.newLinkedHashMap();

	public IncubatorBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(AetherBlockEntityTypes.INCUBATOR.get(), blockPos, blockState);
	}

	@Nonnull
	@Override
	protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory) {
		return new IncubatorContainer(id, playerInventory, this, this.dataAccess);
	}

	@Nonnull
	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container." + Aether.MODID + ".incubator");
	}

	public static Map<Item, Integer> getIncubatingMap() {
		return incubatingMap;
	}

	private static void addItemTagIncubatingTime(Tag<Item> itemTag, int burnTime) {
		for (Item item : itemTag.getValues()) {
			incubatingMap.put(item, burnTime);
		}
	}

	public static void addItemIncubatingTime(ItemLike itemProvider, int burnTime) {
		Item item = itemProvider.asItem();
		incubatingMap.put(item, burnTime);
	}

	private boolean isLit() {
		return this.litTime > 0;
	}

	@Override
	public void load(@Nonnull CompoundTag tag) {
		super.load(tag);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.items);
		this.litTime = tag.getInt("LitTime");
		this.incubationProgress = tag.getInt("IncubationProgress");
		this.incubationTotalTime = tag.getInt("IncubationTotalTime");
	}

	@Override
	public void saveAdditional(@Nonnull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("LitTime", this.litTime);
		tag.putInt("IncubationProgress", this.incubationProgress);
		tag.putInt("IncubationTotalTime", this.incubationTotalTime);
		ContainerHelper.saveAllItems(tag, this.items);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, IncubatorBlockEntity blockEntity) {
		boolean flag = blockEntity.isLit();
		boolean flag1 = false;

		if (blockEntity.isLit()) {
			--blockEntity.litTime;
		}

		ItemStack itemstack = blockEntity.items.get(1);
		if (blockEntity.isLit() || !itemstack.isEmpty() && !blockEntity.items.get(0).isEmpty()) {
			if (!blockEntity.isLit() && !blockEntity.items.get(0).isEmpty()) {
				blockEntity.litTime = blockEntity.getBurnDuration(itemstack);
				if (blockEntity.isLit()) {
					flag1 = true;
					if (itemstack.hasContainerItem()) {
						blockEntity.items.set(1, itemstack.getContainerItem());
					} else if (!itemstack.isEmpty()) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							blockEntity.items.set(1, itemstack.getContainerItem());
						}
					}
				}
			}

			if (blockEntity.isLit() && !blockEntity.items.get(0).isEmpty()) {
				++blockEntity.incubationProgress;
				if (blockEntity.incubationProgress == blockEntity.incubationTotalTime) {
					blockEntity.incubationProgress = 0;
					blockEntity.incubationTotalTime = getTotalIncubationTime();
					blockEntity.incubate(blockEntity.items);
				}
			} else {
				blockEntity.incubationProgress = 0;
			}
		} else if (!blockEntity.isLit() && blockEntity.incubationProgress > 0) {
			blockEntity.incubationProgress = Mth.clamp(blockEntity.incubationProgress - 2, 0, blockEntity.incubationTotalTime);
		}

		if (flag != blockEntity.isLit()) {
			flag1 = true;
			state = state.setValue(AbstractFurnaceBlock.LIT, blockEntity.isLit());
			level.setBlock(pos, state, 3);
		}

		if (flag1) {
			setChanged(level, pos, state);
		}
	}

	private void incubate(NonNullList<ItemStack> stacks) { //TODO: The rest of this will be implemented with the PR for Moas.
		stacks.get(0).shrink(1);
		Aether.LOGGER.info(true);
	}

	protected int getBurnDuration(ItemStack fuelStack) {
		if (fuelStack.isEmpty() || !getIncubatingMap().containsKey(fuelStack.getItem())) {
			return 0;
		} else {
			return getIncubatingMap().get(fuelStack.getItem());
		}
	}

	private static int getTotalIncubationTime() {
		return 5700;
	}

	@Nonnull
	public int[] getSlotsForFace(@Nonnull Direction side) {
		if (side == Direction.NORTH || side == Direction.SOUTH) {
			return SLOTS_NS;
		} else {
			return SLOTS_EW;
		}
	}

	public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack stack, @Nullable Direction direction) {
		 if (index == 0) {
			 return stack.is(AetherTags.Items.MOA_EGGS);
		} else {
			 return this.getBurnDuration(stack) > 0;
		}
	}

	@Override
	public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack, @Nonnull Direction direction) {
		return false;
	}

	@Override
	public int getContainerSize() {
		return this.items.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.items) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Nonnull
	@Override
	public ItemStack getItem(int index) {
		return this.items.get(index);
	}

	@Nonnull
	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.items, index, count);
	}

	@Nonnull
	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ContainerHelper.takeItem(this.items, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getMaxStackSize()) {
			stack.setCount(this.getMaxStackSize());
		}

		if (index == 0 && !flag) {
			this.incubationProgress = 0;
			this.setChanged();
		}
	}

	public boolean stillValid(@Nonnull Player player) {
		if (this.level.getBlockEntity(this.worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.NORTH)
				return handlers[0].cast();
			else if (facing == Direction.SOUTH)
				return handlers[1].cast();
			else if (facing == Direction.EAST)
				return handlers[2].cast();
			else
				return handlers[3].cast();
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
			handler.invalidate();
		}
	}

	@Override
	public void reviveCaps() {
		super.reviveCaps();
		this.handlers = SidedInvWrapper.create(this, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
	}
}
