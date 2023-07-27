package com.aetherteam.aether.entity.monster.dungeon.boss.ai;


import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Set the path up to avoid an unbreakable block.
 */
public class AvoidObstacles extends Behavior<Slider> {
    public AvoidObstacles() {
        super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
        if (!slider.isAwake() || slider.isDeadOrDying() || slider.getBrain().getTimeUntilExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get()) != 1) {
            return false;
        }

        Brain<?> brain = slider.getBrain();
        Direction direction = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get()).orElse(Direction.UP);
        return direction.getAxis() != Direction.Axis.Y;
    }

    @Override
    protected void start(ServerLevel level, Slider slider, long gameTime) {
        Brain<?> brain = slider.getBrain();
        Vec3 targetPos = SliderAi.getTargetPoint(brain);
        if (targetPos == null) {
            return;
        }

        Direction direction = SliderAi.calculateDirection(targetPos.x - slider.getX(), targetPos.y - slider.getY(), targetPos.z - slider.getZ());
        AABB collisionBox = SliderAi.calculateAdjacentBox(slider.getBoundingBox(), direction);

        boolean isTouchingWall = false;
        for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(collisionBox.minX), Mth.floor(collisionBox.minY), Mth.floor(collisionBox.minZ), Mth.ceil(collisionBox.maxX - 1), Mth.ceil(collisionBox.maxY - 1), Mth.ceil(collisionBox.maxZ - 1))) {
            if (slider.level.getBlockState(pos).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
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
                        if (slider.level.getBlockState(pos.set(x, y, z)).is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                            isTouchingWall = true;
                        }
                    }
                }
            }
            Vec3 currentPos = slider.position();
            brain.setMemory(AetherMemoryModuleTypes.TARGET_POSITION.get(), new Vec3(currentPos.x, y, currentPos.z));
            brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), Direction.UP);
        }
    }
}