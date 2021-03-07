package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.tileentity.TileEntity;

public class ChestMimicTileEntity extends TileEntity
{
	public ChestMimicTileEntity() {
		super(AetherTileEntityTypes.CHEST_MIMIC.get());
	}
}
