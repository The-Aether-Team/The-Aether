package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Bee.BeeGrowCropGoal.class)
public class BeeGrowCropGoalMixin
{
    @Unique
    private Bee bee;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(Bee outer, CallbackInfo ci) {
        this.bee = outer;
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", shift = At.Shift.AFTER), method = "tick")
    private void tick(CallbackInfo ci) {
        for (int i = 1; i <= 2; ++i) {
            BlockPos blockPos = this.bee.blockPosition().below(i);
            BlockState blockState = this.bee.level.getBlockState(blockPos);
            boolean isStem = false;
            if (blockState.is(BlockTags.BEE_GROWABLES)) {
                if (blockState.is(AetherBlocks.BERRY_BUSH_STEM.get())) {
                    isStem = true;
                }
                if (isStem) {
                    this.bee.level.levelEvent(2005, blockPos, 0);
                    this.bee.level.setBlockAndUpdate(blockPos, AetherBlocks.BERRY_BUSH.get().defaultBlockState());
                    this.bee.incrementNumCropsGrownSincePollination();
                }
            }
        }
    }
}
