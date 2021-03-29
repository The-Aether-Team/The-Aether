package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class SkyrootSignTileEntity extends SignTileEntity
{
    @Override
    public TileEntityType<SkyrootSignTileEntity> getType()
    {
        return AetherTileEntityTypes.SKYROOT_SIGN.get();
    }
}
