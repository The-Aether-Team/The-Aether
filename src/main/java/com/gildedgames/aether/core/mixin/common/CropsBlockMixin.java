package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropsBlock.class)
public class CropsBlockMixin
{
    @Inject(at = @At("HEAD"), method = "mayPlaceOn", cancellable = true)
    private void mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(AetherBlocks.AETHER_FARMLAND.get())) {
            cir.setReturnValue(true);
        }
    }
}
