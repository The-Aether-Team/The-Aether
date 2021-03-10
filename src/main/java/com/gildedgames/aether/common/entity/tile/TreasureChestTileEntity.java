package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.block.BlockState;
import org.apache.commons.lang3.Validate;

import com.gildedgames.aether.core.api.registers.DungeonType;
import com.gildedgames.aether.core.registry.AetherDungeonTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
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
		return new TranslationTextComponent("gui.treasure_chest", new TranslationTextComponent(this.getKind().getTranslationKey()), new TranslationTextComponent(AetherBlocks.TREASURE_CHEST.get().getDescriptionId()));
	}
	
	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
//		this.kind = AetherDungeonTypes.BRONZE.get();
//		if (compound.contains("Kind", 8)) {
//			String kind = compound.getString("Kind");
//			if (!kind.isEmpty()) {
//				this.kind = AetherAPI.getDungeonType(new ResourceLocation(kind));
//				if (this.kind == null) {
//					this.kind = AetherDungeonTypes.BRONZE.get();
//				}
//			}
//		}
	}
	
	@Override
	protected boolean tryLoadLootTable(CompoundNBT compound) {
		this.locked = compound.getBoolean("Locked");
		return this.locked | super.tryLoadLootTable(compound); // intentional | instead of ||
	}
	
	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
//
//		compound.putBoolean("Locked", this.locked);
//		compound.putString("Kind", this.getKind().getRegistryName().toString());
//
		return compound;
	}
	
	@Override
	protected boolean trySaveLootTable(CompoundNBT compound) {
		return isLocked() | super.trySaveLootTable(compound); // intentional | instead of ||
	}
	
	public void setKind(DungeonType kind) {
		this.kind = Validate.notNull(kind);
	}
	
	public DungeonType getKind() {
		DungeonType kind = this.kind;
		return (kind == null)? AetherDungeonTypes.BRONZE.get() : kind;
	}
	
	public void unlock() {
		if (!this.isLocked()) {
			return;
		}
		
		this.lootTable = this.getKind().getLootTable();
		this.locked = false;
		
		this.setChanged();
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT compound = new CompoundNBT();
		this.save(compound);
		return new SUpdateTileEntityPacket(this.getBlockPos(), 191, compound);
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
		this.setChanged();
	}
	
	@Override
	public boolean canOpen(PlayerEntity player) {
		return !isLocked() && super.canOpen(player);
	}
	
}
