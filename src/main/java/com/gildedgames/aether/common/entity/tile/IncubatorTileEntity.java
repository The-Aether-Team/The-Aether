package com.gildedgames.aether.common.entity.tile;

import java.util.UUID;

import javax.annotation.Nullable;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.inventory.container.IncubatorContainer;
import com.gildedgames.aether.common.registry.AetherItems;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class IncubatorTileEntity extends BaseContainerBlockEntity implements WorldlyContainer {
	private static final int[] SLOTS_UP = {0};
	private static final int[] SLOTS_DOWN = {};
	private static final int[] SLOTS_HORIZONTAL = {1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
	private UUID ownerUUID;
	private int progress;
	private int powerRemaining;
	private int ticksRequired = 5700;
	protected final ContainerData incubatorData = new ContainerData() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return IncubatorTileEntity.this.progress;
			case 1:
				return IncubatorTileEntity.this.powerRemaining;
			case 2:
				return IncubatorTileEntity.this.ticksRequired;
			default:
				return 0;
			}
		}
		
		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				IncubatorTileEntity.this.progress = value;
				break;
			case 1:
				IncubatorTileEntity.this.powerRemaining = value;
				break;
			case 2:
				IncubatorTileEntity.this.ticksRequired = value;
			}
		}
		
		@Override
		public int getCount() {
			return 3;
		}
	};

	//protected IncubatorTileEntity(BlockEntityType<?> tileEntityTypeIn) {
//		super(tileEntityTypeIn);
//	}

	public IncubatorTileEntity(BlockPos blockPos, BlockState blockState) {
		super(AetherTileEntityTypes.INCUBATOR.get(), blockPos, blockState);
	}
	
	public UUID getOwnerUniqueId() {
		return ownerUUID;
	}
	
	public void setOwnerUniqueId(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}
	
	public int getProgress() {
		return progress;
	}
	
	public int getTicksRequired() {
		return ticksRequired;
	}

	public int getPowerRemaining() {
		return powerRemaining;
	}

	@Override
	public int getContainerSize() {
		return items.size();
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

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0) {
			//return stack.getItem() == AetherItems.MOA_EGG;
			return true;
		} else /* index == 1 */ {
			return isFuel(stack);
		}
	}
	
	public static boolean isFuel(ItemStack itemStack) {
		return itemStack.getItem() == AetherItems.AMBROSIUM_SHARD.get();
	}
	
	@Override
	public ItemStack getItem(int index) {
		return items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ContainerHelper.removeItem(this.items, index, count);
	}

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
			this.progress = 0;
			this.setChanged();
		}
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.level.getBlockEntity(this.worldPosition)!= this) {
			return false;	
		} else {
			return player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
		}
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	@Override
	protected Component getDefaultName() {
		return new TranslatableComponent("container." + Aether.MODID + ".incubator");
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new IncubatorContainer(id, player, this, incubatorData, this::setOwnerUniqueId);
	}
	
	public boolean isIncubating() {
		return powerRemaining > 0;
	}

//	@Override
//	public void tick() {
//		if (isIncubating()) {
//			--this.powerRemaining;
//		}
//
//		// TODO
//	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) {
		return this.canPlaceItem(index, itemStackIn);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return false;
	}
	
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.items);
		this.powerRemaining = compound.getInt("BurnTime");
		this.progress = compound.getInt("CookTime");
		this.ticksRequired = compound.getInt("CookTimeTotal");
	}
	
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("BurnTime", this.powerRemaining);
		compound.putInt("CookTime", this.progress);
		compound.putInt("CookTimeTotal", this.ticksRequired);
		ContainerHelper.saveAllItems(compound, this.items);
	}
	
	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
        net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
		if (!this.remove && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP) {
				return handlers[0].cast();
			}
			else if (facing == Direction.DOWN) {
				return handlers[1].cast();
			}
			else {
				return handlers[2].cast();
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (int x = 0; x < handlers.length; x++) {
			handlers[x].invalidate();
		}
	}

}
