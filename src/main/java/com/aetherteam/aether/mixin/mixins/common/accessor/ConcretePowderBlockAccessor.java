package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConcretePowderBlock.class)
public interface ConcretePowderBlockAccessor {
    @Accessor("concrete")
    Block aether$getConcrete();

    @Invoker
    static boolean callShouldSolidify(BlockGetter level, BlockPos pos, BlockState state) {
        throw new AssertionError();
    }
}