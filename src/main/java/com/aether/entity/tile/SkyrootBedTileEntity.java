package com.aether.entity.tile;

import com.aether.registry.AetherTileEntityTypes;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class SkyrootBedTileEntity extends TileEntity
{
    public SkyrootBedTileEntity() {
        super(AetherTileEntityTypes.SKYROOT_BED.get());
    }

    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 11, this.getUpdateTag());
    }
}
