package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.entity.tile.SkyrootSignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SkyrootWallSignBlock extends WallSignBlock
{
    public SkyrootWallSignBlock(AbstractBlock.Properties propertiesIn, WoodType woodTypeIn)
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
