package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Handles randomized up and down movement, this is used to occasional override the normal movement decisions in {@link SliderMoveGoal}.
 */
public class SetPathUpOrDownGoal extends Goal {
    private final Slider slider;

    public SetPathUpOrDownGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        // Run this behavior only once between each movement cycle.
        if (this.slider.getMoveDelay() != 1 || this.slider.getTargetPoint() != null || this.slider.getRandom().nextInt(3) != 0) {
            return false;
        }
        Direction moveDir = this.slider.getMoveDirection();
        return moveDir == null || moveDir.getAxis() != Direction.Axis.Y;
    }

    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        Vec3 targetPos = getTargetOrCurrentPosition(this.slider);
        if (targetPos == null) {
            return;
        }
        Vec3 currentPos = this.slider.position();
        AABB currentPath = calculatePathBox(this.slider.getBoundingBox(), targetPos.x() - currentPos.x(), 0, targetPos.z() - currentPos.z());

        Direction direction = currentPos.y() > targetPos.y() ? Direction.DOWN : Direction.UP;
        currentPath = Slider.calculateAdjacentBox(currentPath, direction);
        currentPath = currentPath.expandTowards(0, targetPos.y() - currentPos.y(), 0);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        // If there's a block in the way, don't take the low road.
        for (int x = Mth.floor(currentPath.minX); x < currentPath.maxX; x++) {
            for (int z = Mth.floor(currentPath.minZ); z < currentPath.maxZ; z++) {
                BlockState state = this.slider.level().getBlockState(pos.set(x, targetPos.y(), z));
                if (state.is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                    return;
                }
            }
        }

        double y = direction == Direction.UP ? Math.max(targetPos.y(), currentPos.y() + 1) : targetPos.y();
        this.slider.setMoveDirection(direction);
        this.slider.setTargetPoint(new Vec3(currentPos.x(), y, currentPos.z()));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Attempts to get a target position to move to.
     * @param slider The {@link Slider} that the brain belongs to.
     * @return The {@link Vec3} position.
     */
    @Nullable
    private static Vec3 getTargetOrCurrentPosition(Slider slider) {
        LivingEntity target = slider.getTarget();
        if (target == null) {
            return null;
        }
        return target.position();
    }

    /**
     * Creates an AABB expanded to the point the slider wants to go to.
     * @param box The original {@link AABB}.
     * @param x The {@link Double} for the x-direction to expand to.
     * @param y The {@link Double} for the y-direction to expand to.
     * @param z The {@link Double} for the z-direction to expand to.
     */
    private static AABB calculatePathBox(AABB box, double x, double y, double z) {
        return box.expandTowards(x, y, z);
    }
}
