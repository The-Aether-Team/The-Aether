package com.gildedgames.aether.entity.monster.dungeon.boss.slider;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class SetPathUpOrDown extends Behavior<Slider> {
    public SetPathUpOrDown() {
        super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED, AetherMemoryModuleTypes.TARGET_POSITION.get(), MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel pLevel, Slider slider) {
        Brain<?> brain = slider.getBrain();
        // Run this behavior only once between each movement cycle.
        if (brain.getTimeUntilExpiry(AetherMemoryModuleTypes.MOVE_DELAY.get()) != 1) {
            return false;
        }

        if (slider.getRandom().nextInt(3) != 0) {
            return false;
        }

        Optional<Direction> optionalDir = slider.getBrain().getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
        if (optionalDir.isPresent() && optionalDir.get().getAxis() == Direction.Axis.Y) {
            return false;
        }

        return true;
    }

    @Override
    protected void start(ServerLevel level, Slider slider, long gameTime) {
        Brain<?> brain = slider.getBrain();

        Vec3 targetPos = getTargetOrCurrentPosition(slider);
        if (targetPos == null) {
            return;
        }
        Vec3 currentPos = slider.position();

        AABB currentPath = calculatePathBox(slider.getBoundingBox(), targetPos.x - currentPos.x, targetPos.y - currentPos.y, targetPos.z - currentPos.z);

        Direction direction = currentPos.y > targetPos.y ? Direction.DOWN : Direction.UP;

        currentPath = SliderAi.calculateAdjacentBox(currentPath, direction);
        currentPath = currentPath.expandTowards(0, targetPos.y - currentPos.y, 0);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        // If there's a block in the way, don't take the low road.
        for (int x = Mth.floor(currentPath.minX); x < currentPath.maxX; x++) {
            for (int z = Mth.floor(currentPath.minZ); z < currentPath.maxZ; z++) {
                BlockState state = level.getBlockState(pos.set(x, targetPos.y, z));
                if (state.is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                    return;
                }
            }
        }

        double y = direction == Direction.UP ? Math.max(targetPos.y, currentPos.y + 1) : targetPos.y;
        brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), direction);
        brain.setMemoryWithExpiry(AetherMemoryModuleTypes.TARGET_POSITION.get(), new Vec3(currentPos.x, y, currentPos.z), 100);
    }

    @Nullable
    private static Vec3 getTargetOrCurrentPosition(Slider slider) {
        Optional<LivingEntity> player = slider.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        return player.map(Entity::position).orElse(null);
    }

    /**
     * Creates an AABB expanded to the point the slider wants to go to.
     */
    private static AABB calculatePathBox(AABB box, double x, double y, double z) {
        return box.expandTowards(x - box.getXsize(), y - box.getYsize(), z - box.getZsize());
    }
}