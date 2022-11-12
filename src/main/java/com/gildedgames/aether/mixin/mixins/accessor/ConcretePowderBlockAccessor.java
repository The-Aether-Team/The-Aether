package com.gildedgames.aether.mixin.mixins.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConcretePowderBlock.class)
public interface ConcretePowderBlockAccessor {
    @Accessor
    BlockState getConcrete();

    @Invoker("shouldSolidify")
    static boolean shouldSolidify(BlockGetter level, BlockPos pos, BlockState state) {
        throw new AssertionError();
    }
}
