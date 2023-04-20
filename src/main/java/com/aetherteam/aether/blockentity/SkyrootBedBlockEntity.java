package com.aetherteam.aether.blockentity;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyrootBedBlockEntity extends BlockEntity {
    public SkyrootBedBlockEntity() {
        super(AetherBlockEntityTypes.SKYROOT_BED.get(), BlockPos.ZERO, AetherBlocks.SKYROOT_BED.get().defaultBlockState());
    }

    public SkyrootBedBlockEntity(BlockPos pos, BlockState state) {
        super(AetherBlockEntityTypes.SKYROOT_BED.get(), pos, state);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
