package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SkyrootSignBlock extends StandingSignBlock
{
    public SkyrootSignBlock(Properties propertiesIn, WoodType woodTypeIn)
    {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154556_, BlockState p_154557_)
    {
        return new SkyrootSignTileEntity(p_154556_, p_154557_);
    }

}