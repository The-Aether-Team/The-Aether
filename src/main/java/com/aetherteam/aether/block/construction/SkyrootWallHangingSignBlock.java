package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.blockentity.SkyrootHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SkyrootWallHangingSignBlock extends WallHangingSignBlock {
    public SkyrootWallHangingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootHangingSignBlockEntity(pos, state);
    }
}
