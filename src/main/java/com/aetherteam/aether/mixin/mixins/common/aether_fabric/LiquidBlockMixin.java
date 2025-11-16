package com.aetherteam.aether.mixin.mixins.common.aether_fabric;

import com.aetherteam.aether.block.AetherBlocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {
    @WrapOperation(method = {"onPlace", "neighborChanged"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LiquidBlock;shouldSpreadLiquid(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean aetherFabric$adjustInteraction(LiquidBlock instance, Level level, BlockPos pos, BlockState state, Operation<Boolean> original) {
        FluidState fluidState = level.getFluidState(pos);

        if (fluidState.is(FluidTags.WATER)) {
            for (Direction direction : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos relativePos = pos.relative(direction.getOpposite());

                if (level.getBlockState(pos.below()).is(AetherBlocks.QUICKSOIL.get()) && level.getBlockState(relativePos).is(Blocks.MAGMA_BLOCK)) {
                    var replacementState = AetherBlocks.HOLYSTONE.get().defaultBlockState();

                    level.setBlockAndUpdate(pos, replacementState);
                    level.levelEvent(1501, pos, 0);

                    return false;
                }
            }
        }

        return original.call(instance, level, pos, state);
    }
}
