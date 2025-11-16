package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.events.BlockEvents;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

@Mixin(DiodeBlock.class)
public abstract class DiodeBlockMixin {
    @Inject(method = "updateNeighborsInFront", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;relative(Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos;"), cancellable = true)
    private void aetherFabric$runUpdateEvent(Level level, BlockPos pos, BlockState state, CallbackInfo ci, @Local() Direction direction) {
        var isCancelled = new CancellableCallbackImpl(false);

        BlockEvents.NEIGHBOR_UPDATE.invoker().onNeighborUpdate(level, pos, level.getBlockState(pos), EnumSet.of(direction.getOpposite()), false, isCancelled);

        if (isCancelled.isCanceled()) ci.cancel();
    }
}
