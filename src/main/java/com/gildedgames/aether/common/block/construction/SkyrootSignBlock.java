package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SkyrootSignBlock extends StandingSignBlock
{
    public SkyrootSignBlock(Properties propertiesIn, WoodType woodTypeIn)
    {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public boolean hasTileEntity(BlockState stateIn)
    {
        return true;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn)
    {
        return new SkyrootSignTileEntity();
    }
}