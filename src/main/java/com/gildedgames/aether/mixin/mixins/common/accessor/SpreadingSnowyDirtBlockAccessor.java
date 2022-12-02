package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpreadingSnowyDirtBlock.class)
public interface SpreadingSnowyDirtBlockAccessor {
    @Invoker
    static boolean callCanBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
        throw new AssertionError();
    }

    @Invoker
    static boolean callCanPropagate(BlockState state, LevelReader level, BlockPos pos) {
        throw new AssertionError();
    }
}