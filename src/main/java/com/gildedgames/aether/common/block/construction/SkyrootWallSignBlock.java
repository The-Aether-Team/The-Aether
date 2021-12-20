package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

public class SkyrootWallSignBlock extends WallSignBlock
{
    public SkyrootWallSignBlock(BlockBehaviour.Properties propertiesIn, WoodType woodTypeIn)
    {
        super(propertiesIn, woodTypeIn);
    }


    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_) {
        return new SkyrootSignTileEntity(p_154556_, p_154557_);
    }
}
