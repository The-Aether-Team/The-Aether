package com.gildedgames.aether.entity.tile;

import com.gildedgames.aether.registry.AetherTileEntityTypes;
import net.minecraft.tileentity.TileEntity;

public class ChestMimicTileEntity extends TileEntity
{
	public ChestMimicTileEntity() {
		super(AetherTileEntityTypes.CHEST_MIMIC.get());
	}
}
