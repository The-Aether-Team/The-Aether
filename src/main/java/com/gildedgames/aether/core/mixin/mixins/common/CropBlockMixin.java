package com.gildedgames.aether.core.mixin.mixins.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class CropBlockMixin
{
    /**
     * Vanilla crops are hardcoded to check for normal farmland to determine whether they can live there. This injector
     * mixin bypasses that by checking for Aether farmland as well.
     */
    @Inject(at = @At("HEAD"), method = "mayPlaceOn", cancellable = true)
    private void mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(AetherBlocks.AETHER_FARMLAND.get())) {
            cir.setReturnValue(true);
        }
    }
}
