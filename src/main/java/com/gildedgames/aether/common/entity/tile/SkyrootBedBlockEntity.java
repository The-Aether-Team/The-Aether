package com.gildedgames.aether.common.entity.tile;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootBedBlockEntity extends BlockEntity
{
    public SkyrootBedBlockEntity(BlockPos pos, BlockState state) {
        super(AetherTileEntityTypes.SKYROOT_BED.get(), pos, state);
    }

    public SkyrootBedBlockEntity() {
        super(AetherTileEntityTypes.SKYROOT_BED.get(), BlockPos.ZERO, AetherBlocks.SKYROOT_BED.get().defaultBlockState());
    }

//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        return new ClientboundBlockEntityDataPacket(this.worldPosition, 11, this.getUpdateTag());
//    }
}
