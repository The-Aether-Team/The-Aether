package com.aetherteam.aether.entity.ai.goal;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.EnumSet;
import java.util.function.Predicate;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.ai.goal.EatBlockGoal}.
 * Changed checks for Grass Blocks to Aether Grass Blocks.
 */
public class EatAetherGrassGoal extends Goal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
    private final Mob mob;
    private int eatAnimationTick;

    public EatAetherGrassGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.mob.level().getBlockState(blockpos))) {
                return true;
            } else {
                return this.mob.level().getBlockState(blockpos.below()).is(AetherBlocks.AETHER_GRASS_BLOCK.get());
            }
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(40);
        this.mob.level().broadcastEntityEvent(this.mob, (byte) 10);
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }

    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
            BlockPos blockPos = this.mob.blockPosition();
            if (IS_TALL_GRASS.test(this.mob.level().getBlockState(blockPos))) {
                if (EntityEventFactory.getMobGriefingEvent(this.mob.level(), this.mob)) {
                    this.mob.level().destroyBlock(blockPos, false);
                }
                this.mob.ate();
            } else {
                BlockPos blockPos1 = blockPos.below();
                if (this.mob.level().getBlockState(blockPos1).is(AetherBlocks.AETHER_GRASS_BLOCK.get())) {
                    if (EntityEventFactory.getMobGriefingEvent(this.mob.level(), this.mob)) {
                        this.mob.level().levelEvent(2001, blockPos1, Block.getId(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState()));
                        this.mob.level().setBlock(blockPos1, AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, this.mob.level().getBlockState(blockPos1).getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 2);
                    }
                    this.mob.ate();
                }
            }
        }
    }
}
