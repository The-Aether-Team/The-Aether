package com.aether.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.aether.Aether;
import com.aether.inventory.container.IncubatorContainer;
import com.aether.item.AetherItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class IncubatorTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
	private static final int[] SLOTS_UP = {0};
	private static final int[] SLOTS_DOWN = {};
	private static final int[] SLOTS_HORIZONTAL = {1};
	protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
	private UUID ownerUUID;
	private int progress;
	private int powerRemaining;
	private int ticksRequired = 5700;
	protected final IIntArray incubatorData = new IIntArray() {
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
		public int size() {
			return 3;
		}
	};

	protected IncubatorTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public IncubatorTileEntity() {
		super(AetherTileEntityTypes.INCUBATOR);
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
	public int getSizeInventory() {
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
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return stack.getItem() == AetherItems.MOA_EGG;
		} else /* index == 1 */ {
			return isFuel(stack);
		}
	}
	
	public static boolean isFuel(ItemStack itemStack) {
		return itemStack.getItem() == AetherItems.AMBROSIUM_SHARD;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.items.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.items.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		
		if (index == 0 && !flag) {
			this.progress = 0;
			this.markDirty();
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos)!= this) {
			return false;	
		} else {
			return player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
		}
	}

	@Override
	public void clear() {
		items.clear();
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container." + Aether.MODID + ".incubator");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new IncubatorContainer(id, player, this, incubatorData, this::setOwnerUniqueId);
	}
	
	public boolean isIncubating() {
		return powerRemaining > 0;
	}

	@Override
	public void tick() {
		if (isIncubating()) {
			--this.powerRemaining;
		}
		
		// TODO
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return false;
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.items);
		this.powerRemaining = compound.getInt("BurnTime");
		this.progress = compound.getInt("CookTime");
		this.ticksRequired = compound.getInt("CookTimeTotal");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("BurnTime", this.powerRemaining);
		compound.putInt("CookTime", this.progress);
		compound.putInt("CookTimeTotal", this.ticksRequired);
		ItemStackHelper.saveAllItems(compound, this.items);
		return compound;
	}
	
	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
        net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
	public void remove() {
		super.remove();
		for (int x = 0; x < handlers.length; x++) {
			handlers[x].invalidate();
		}
	}

}
