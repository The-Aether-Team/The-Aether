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
public class FindPollinationTargetGoalMixin
{
    @Unique
    private Bee beeEntity;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(Bee outer, CallbackInfo ci) {
        this.beeEntity = outer;
    }

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I", shift = At.Shift.AFTER), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        for (int i = 1; i <= 2; ++i) {
            BlockPos blockpos = this.beeEntity.blockPosition().below(i);
            BlockState blockstate = this.beeEntity.level.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            boolean flag = false;
            if (blockstate.is(BlockTags.BEE_GROWABLES)) {
                if (block == AetherBlocks.BERRY_BUSH_STEM.get()) {
                    flag = true;
                }
                if (flag) {
                    this.beeEntity.level.levelEvent(2005, blockpos, 0);
                    this.beeEntity.level.setBlockAndUpdate(blockpos, AetherBlocks.BERRY_BUSH.get().defaultBlockState());
                    this.beeEntity.incrementNumCropsGrownSincePollination();
                }
            }
        }
    }
}
