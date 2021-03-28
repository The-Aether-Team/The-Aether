package com.gildedgames.aether.core.mixin.common;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.FindPollinationTargetGoal.class)
public class FindPollinationTargetGoalMixin
{
    @Unique
    private BeeEntity beeEntity;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(BeeEntity outer, CallbackInfo ci) {
        this.beeEntity = outer;
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        if (this.beeEntity.random.nextInt(30) == 0) {
            for (int i = 1; i <= 2; ++i) {
                BlockPos blockpos = this.beeEntity.blockPosition().below(i);
                BlockState blockstate = this.beeEntity.level.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                boolean flag = false;
                if (block.is(BlockTags.BEE_GROWABLES)) {
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
}
