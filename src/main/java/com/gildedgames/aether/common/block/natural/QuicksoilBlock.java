package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.util.AetherDoubleDropBlock;
import com.gildedgames.aether.common.block.util.FrictionCapped;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class QuicksoilBlock extends AetherDoubleDropBlock implements FrictionCapped {
    public QuicksoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return this.getFriction(entity, super.getFriction());
    }
}
