package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Set the path up to avoid an unbreakable block.
 */
public class AvoidObstaclesGoal extends Goal {
    private final Slider slider;

    public AvoidObstaclesGoal(Slider slider) {
        this.slider = slider;
    }

    @Override
    public boolean canUse() {
        if (!this.slider.isAwake() || this.slider.isDeadOrDying() || this.slider.getMoveDelay() != 1) {
            return false;
        }

        Direction direction = this.slider.getMoveDirection();
        return direction != null && direction.getAxis() != Direction.Axis.Y;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        Vec3 targetPos = this.slider.findTargetPoint();
        if (targetPos == null) {
            return;
        }
        Direction direction = Slider.calculateDirection(targetPos.x() - this.slider.getX(), targetPos.y() - this.slider.getY(), targetPos.z() - this.slider.getZ());
        AABB collisionBox = Slider.calculateAdjacentBox(this.slider.getBoundingBox(), direction);
        BlockPos min = new BlockPos(Mth.floor(collisionBox.minX), Mth.floor(collisionBox.minY), Mth.floor(collisionBox.minZ));
        BlockPos max = new BlockPos(Mth.ceil(collisionBox.maxX - 1), Mth.ceil(collisionBox.maxY - 1), Mth.ceil(collisionBox.maxZ - 1));

        boolean isTouchingWall = false;
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (this.slider.getLevel().getBlockState(pos).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                isTouchingWall = true;
                break;
            }
        }
        if (isTouchingWall) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            int y = Mth.floor(collisionBox.minY);
            while (isTouchingWall) {
                y++;
                isTouchingWall = false;
                for (int x = Mth.floor(collisionBox.minX); x < collisionBox.maxX; x++) {
                    for (int z = Mth.floor(collisionBox.minZ); z < collisionBox.maxZ; z++) {
                        if (this.slider.getLevel().getBlockState(pos.set(x, y, z)).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                            isTouchingWall = true;
                        }
                    }
                }
            }
            Vec3 currentPos = slider.position();
            this.slider.setTargetPoint(new Vec3(currentPos.x(), y, currentPos.z()));
            this.slider.setMoveDirection(Direction.UP);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}