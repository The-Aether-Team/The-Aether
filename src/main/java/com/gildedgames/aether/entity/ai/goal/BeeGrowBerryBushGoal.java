package com.gildedgames.aether.entity.ai.goal;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.mixin.mixins.accessor.BeeAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.state.BlockState;

public class BeeGrowBerryBushGoal extends Goal {
    public final Bee bee;

    public BeeGrowBerryBushGoal(Bee bee) {
        this.bee = bee;
    }

    @Override
    public boolean canUse() {
        return this.canBeeUse() && !this.bee.isAngry();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canBeeContinueToUse() && !this.bee.isAngry();
    }

    public boolean canBeeUse() {
        BeeAccessor beeAccessor = (BeeAccessor) this.bee;
        if (beeAccessor.getCropsGrownSincePollination() >= 10) {
            return false;
        } else if (this.bee.getRandom().nextFloat() < 0.3F) {
            return false;
        } else {
            return this.bee.hasNectar() && beeAccessor.isHiveValid();
        }
    }

    public boolean canBeeContinueToUse() {
        return this.canBeeUse();
    }

    @Override
    public void tick() {
        BeeAccessor beeAccessor = (BeeAccessor) this.bee;
        if (this.bee.getRandom().nextInt(this.adjustedTickDelay(30)) == 0) {
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
                        beeAccessor.incrementNumCropsGrownSincePollination();
                    }
                }
            }
        }
    }
}
