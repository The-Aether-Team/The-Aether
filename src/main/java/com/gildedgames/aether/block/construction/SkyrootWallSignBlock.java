package com.gildedgames.aether.block.construction;

import com.gildedgames.aether.block.entity.SkyrootSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;

public class SkyrootWallSignBlock extends WallSignBlock
{
    public SkyrootWallSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
        super(propertiesIn, woodTypeIn);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new SkyrootSignBlockEntity(pos, state);
    }
}
