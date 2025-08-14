package com.aetherteam.aether.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AetherFlowerBlockEntity extends BlockEntity {
    public AetherFlowerBlockEntity(BlockPos pos, BlockState blockState) {
        super(AetherBlockEntityTypes.FLOWER.get(), pos, blockState);
    }
}
