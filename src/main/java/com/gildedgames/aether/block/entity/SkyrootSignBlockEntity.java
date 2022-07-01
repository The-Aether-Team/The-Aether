package com.gildedgames.aether.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class SkyrootSignBlockEntity extends SignBlockEntity
{
    public SkyrootSignBlockEntity(BlockPos p_155700_, BlockState p_155701_) {
        super(p_155700_, p_155701_);
    }

    @Nonnull
    @Override
    public BlockEntityType<SkyrootSignBlockEntity> getType()
    {
        return AetherBlockEntityTypes.SKYROOT_SIGN.get();
    }
}
