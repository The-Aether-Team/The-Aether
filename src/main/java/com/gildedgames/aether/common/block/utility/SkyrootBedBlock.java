package com.gildedgames.aether.common.block.utility;

import com.gildedgames.aether.common.block.entity.SkyrootBedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import javax.annotation.Nonnull;

public class SkyrootBedBlock extends BedBlock
{
    public SkyrootBedBlock(Properties properties) {
        super(DyeColor.CYAN, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, Boolean.FALSE));
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new SkyrootBedBlockEntity(pos, state);
    }
}

