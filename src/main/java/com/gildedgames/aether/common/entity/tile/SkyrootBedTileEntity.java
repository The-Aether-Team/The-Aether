package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootBedTileEntity extends BlockEntity
{
    public SkyrootBedTileEntity(BlockPos pos, BlockState state) {
        super(AetherTileEntityTypes.SKYROOT_BED.get(), pos, state);
    }

//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        return new ClientboundBlockEntityDataPacket(this.worldPosition, 11, this.getUpdateTag());
//    }
}
