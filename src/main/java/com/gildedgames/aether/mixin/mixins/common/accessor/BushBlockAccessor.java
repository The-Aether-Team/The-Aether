package com.gildedgames.aether.mixin.mixins.common.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BushBlock.class)
public interface BushBlockAccessor {
    @Invoker
    boolean callMayPlaceOn(BlockState state, BlockGetter level, BlockPos pos);
}
