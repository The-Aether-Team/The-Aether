package com.aether.tileentity;

import com.aether.registry.AetherTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ChestMimicTileEntity extends TileEntity {
	
	protected ChestMimicTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public ChestMimicTileEntity() {
		super(AetherTileEntityTypes.CHEST_MIMIC.get());
	}
	
//	@Override
//	public TileEntityType<?> getType() {
//		// FIXME This returns null for the instance contained within ChestMimicItemStackTileEntityRenderer#chestMimic
//		TileEntityType<?> type = super.getType();
//		if (type == null) {
//			return AetherTileEntityTypes.CHEST_MIMIC;
//		}
//		return type;
//	}

}
