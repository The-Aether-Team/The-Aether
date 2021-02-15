package com.aether.entity.tile;

import com.aether.registry.AetherTileEntityTypes;
import net.minecraft.block.BlockState;
import org.apache.commons.lang3.Validate;

import com.aether.api.AetherAPI;
import com.aether.api.dungeon.DungeonType;
import com.aether.api.dungeon.DungeonTypes;
import com.aether.registry.AetherBlocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TreasureChestTileEntity extends ChestTileEntity {
	protected boolean locked = false;
	private DungeonType kind;
	
	protected TreasureChestTileEntity(TileEntityType<?> typeIn) {
		super(typeIn);
	}
	
	public TreasureChestTileEntity() {
		this(AetherTileEntityTypes.TREASURE_CHEST.get());
	}
	
	public TreasureChestTileEntity(DungeonType type) {
		this(AetherTileEntityTypes.TREASURE_CHEST.get());
		this.kind = type;
		this.locked = (type != null);
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("gui.treasure_chest", new TranslationTextComponent(this.getKind().getTranslationKey()), new TranslationTextComponent(AetherBlocks.TREASURE_CHEST.get().getTranslationKey()));
	}
	
	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		this.kind = DungeonTypes.BRONZE;
		if (compound.contains("Kind", 8)) {
			String kind = compound.getString("Kind");
			if (!kind.isEmpty()) {
				this.kind = AetherAPI.getDungeonType(new ResourceLocation(kind));
				if (this.kind == null) {
					this.kind = DungeonTypes.BRONZE;
				}
			}
		}
	}
	
	@Override
	protected boolean checkLootAndRead(CompoundNBT compound) {
		this.locked = compound.getBoolean("Locked");
		return this.locked | super.checkLootAndRead(compound); // intentional | instead of ||
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		
		compound.putBoolean("Locked", this.locked);
		compound.putString("Kind", this.getKind().getRegistryName().toString());
		
		return compound;
	}
	
	@Override
	protected boolean checkLootAndWrite(CompoundNBT compound) {
		return isLocked() | super.checkLootAndWrite(compound); // intentional | instead of ||
	}
	
	public void setKind(DungeonType kind) {
		this.kind = Validate.notNull(kind);
	}
	
	public DungeonType getKind() {
		DungeonType kind = this.kind;
		return (kind == null)? DungeonTypes.BRONZE : kind;
	}
	
	public void unlock() {
		if (!this.isLocked()) {
			return;
		}
		
		this.lootTable = this.getKind().getLootTable();
		this.locked = false;
		
		this.markDirty();
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT compound = new CompoundNBT();
		this.write(compound);
		return new SUpdateTileEntityPacket(this.getPos(), 191, compound);
	}

	/* I don't know if this is needed
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(pkt.getNbtCompound());
	}
	*/
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
		this.markDirty();
	}
	
	@Override
	public boolean canOpen(PlayerEntity player) {
		return !isLocked() && super.canOpen(player);
	}
	
}
