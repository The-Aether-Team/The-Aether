package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class CrushGoal extends Goal {
    private final Slider slider;

    public CrushGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        return this.slider.isAwake() && !this.slider.isDeadOrDying() && (this.slider.horizontalCollision || this.slider.verticalCollision || this.blocksBetween(this.slider));
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        boolean crushed = false;
        if (ForgeEventFactory.getMobGriefingEvent(this.slider.getLevel(), this.slider)) {
            AABB crushBox = this.slider.getBoundingBox().inflate(0.2);
            for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(crushBox.minX), Mth.floor(crushBox.minY), Mth.floor(crushBox.minZ), Mth.floor(crushBox.maxX), Mth.floor(crushBox.maxY), Mth.floor(crushBox.maxZ))) {
                if (this.slider.getDungeon() == null || this.slider.getDungeon().roomBounds().contains(Vec3.atCenterOf(pos))) {
                    BlockState blockState = this.slider.getLevel().getBlockState(pos);
                    if (this.isBreakable(blockState)) {
                        crushed = this.slider.getLevel().destroyBlock(pos, true, this.slider) || crushed;
                        EntityUtil.spawnRemovalParticles(this.slider.getLevel(), pos);
                    }
                }
            }
        }
        if (crushed) {
            this.slider.getLevel().playSound(null, this.slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (this.slider.getRandom().nextFloat() - this.slider.getRandom().nextFloat()) * 0.2F) * 0.7F);
            this.slider.playSound(this.slider.getCollideSound(), 2.5F, 1.0F / (this.slider.getRandom().nextFloat() * 0.2F + 0.9F));
            this.slider.setMoveDelay(this.slider.calculateMoveDelay());
            this.slider.setDeltaMovement(Vec3.ZERO);
        }
    }

    /**
     * Checks if there are blocks between a target and the Slider.
     * @param slider The {@link Slider} that the brain belongs to.
     * @return Whether there are blocks, as a {@link Boolean}.
     */
    private boolean blocksBetween(Slider slider) {
        LivingEntity target = slider.getTarget();
        if (target == null) {
            return false;
        }
        return slider.getLevel().getBlockStates(
                AABB.of(BoundingBox.fromCorners(target.blockPosition(), slider.blockPosition()))
        ).anyMatch(this::isBreakable);
    }

    private boolean isBreakable(BlockState blockState) {
        return !blockState.isAir() && !blockState.is(AetherTags.Blocks.SLIDER_UNBREAKABLE);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}