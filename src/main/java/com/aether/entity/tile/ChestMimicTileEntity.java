package com.aether.entity.tile;

import com.aether.registry.AetherTileEntityTypes;
import net.minecraft.tileentity.TileEntity;

public class ChestMimicTileEntity extends TileEntity
{
	public ChestMimicTileEntity() {
		super(AetherTileEntityTypes.CHEST_MIMIC.get());
	}
}
