package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.gildedgames.aether.entity.ai.brain.sensing.AetherSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class SliderAi { // TODO: Most damage targeting
    /* TODO
    Most damage targeting
    Movement

     */

    private static final ImmutableList<SensorType<? extends Sensor<Slider>>> SENSOR_TYPES = ImmutableList.of(AetherSensorTypes.SLIDER_PLAYER_SENSOR.get());
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            AetherMemoryModuleTypes.MOVE_DELAY.get(),
            AetherMemoryModuleTypes.MOVE_DIRECTION.get(),
            AetherMemoryModuleTypes.TARGET_POSITION.get()
    );

    private static final ImmutableList<Activity> ACTIVITY_PRIORITY = ImmutableList.of(Activity.FIGHT, Activity.IDLE);

    public static Brain<Slider> makeBrain(Dynamic<?> dynamic) {
        Brain<Slider> brain = Brain.provider(MEMORY_TYPES, SENSOR_TYPES).makeBrain(dynamic);
        initIdleActivity(brain);
        initFightActivity(brain);
        return brain;
    }

    private static void initIdleActivity(Brain<Slider> brain) {
//        brain.addActivity(Activity.IDLE, ImmutableList.of(0, new Stay));
    }

    private static void initFightActivity(Brain<Slider> brain) {
        brain.addActivity(Activity.FIGHT, 10, ImmutableList.of(new Collide(), /*new AvoidObstacles(),*/ new Move()));
    }

    public static void updateActivity(Slider slider) {
        slider.getBrain().setActiveActivityToFirstValid(ACTIVITY_PRIORITY);
    }

    /**
     * Creates an AABB expanded to the point the slider wants to go to.
     */
    private static AABB calculatePathBox(AABB box, Vec3 length, Direction direction) {
        return box.expandTowards((length.x - box.getXsize()) * direction.getStepX(), (length.y - box.getYsize()) * direction.getStepY(), (length.z - box.getZsize()) * direction.getStepZ());
    }

    /**
     * Calculates a box adjacent to the original, with equal dimensions except for the axis it's translated along.
     */
    private static AABB calculateAdjacentBox(AABB box, Direction direction) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;
        if (direction == Direction.UP) {
            minY = maxY;
            maxY += 1;
        } else if (direction == Direction.DOWN) {
            maxY = minY;
            minY -= 1;
        } else if (direction == Direction.NORTH) {
            maxZ = minZ;
            minZ -= 1;
        } else if (direction == Direction.SOUTH) {
            minZ = maxZ;
            maxZ += 1;
        } else if (direction == Direction.EAST) {
            minX = maxX;
            maxX += 1;
        } else { // West
            maxX = minX;
            minX -= 1;
        }

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Move to the defined target position.
     */
    static class Move extends Behavior<Slider> {
        private float velocity;

        public Move() {
            super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED, AetherMemoryModuleTypes.TARGET_POSITION.get(), MemoryStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryStatus.REGISTERED));
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            if (!slider.isAwake() || slider.isDeadOrDying()) {
                return false;
            }

            Brain<?> brain = slider.getBrain();

            int moveDelay = brain.getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(1);
            brain.setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), --moveDelay);

            return moveDelay <= 0;
        }

        @Override
        protected boolean canStillUse(ServerLevel level, Slider slider, long gameTime) {
            return slider.isAwake() && !slider.isDeadOrDying();
        }

        @Override
        protected void start(ServerLevel level, Slider slider, long pGameTime) {
            slider.getBrain().eraseMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
        }

        @Override
        protected void tick(ServerLevel level, Slider slider, long gameTime) {
            Brain<?> brain = slider.getBrain();
            BlockPos targetPoint = getTargetPoint(brain);

            // Move along the calculated path
            if (targetPoint == null) {
                this.doStop(level, slider, gameTime);
                return;
            }

            Direction moveDir = getMoveDirection(slider, targetPoint);

            if (axisDistance(targetPoint.getX() - slider.getX(), targetPoint.getY() - slider.getY(), targetPoint.getZ() - slider.getZ(), moveDir) <= 0) {
                this.doStop(level, slider, gameTime);
                return;
            }

            if (this.velocity < slider.getMaxVelocity()) {
                // The Slider increases its speed based on the speed it has saved
                this.velocity = Math.min(slider.getMaxVelocity(), this.velocity + slider.getVelocityIncrease());
            }

            Vec3 movement = new Vec3(moveDir.getStepX() * this.velocity,
                    moveDir.getStepY() * this.velocity,
                    moveDir.getStepZ() * this.velocity);

            slider.setDeltaMovement(movement);
        }

        @Override
        protected void stop(ServerLevel level, Slider slider, long gameTime) {
            slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), slider.calculateMoveDelay());
            slider.getBrain().eraseMemory(AetherMemoryModuleTypes.TARGET_POSITION.get());
            this.velocity = 0;
            slider.setDeltaMovement(Vec3.ZERO);
        }

        @Nullable
        private static BlockPos getTargetPoint(Brain<?> brain) {
            Optional<BlockPos> pos = brain.getMemory(AetherMemoryModuleTypes.TARGET_POSITION.get());
            if (pos.isPresent()) {
                return pos.get();
            } else {
                Optional<Player> target = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
                return target.map(Entity::blockPosition).orElse(null);
            }
        }

        /**
         * Get the move direction if it already exists, or calculate a new one.
         */
        private static Direction getMoveDirection(Slider slider, BlockPos targetPoint) {
            Brain<?> brain = slider.getBrain();
            Optional<Direction> optionalDir = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
            Direction moveDir;

            if (optionalDir.isEmpty()) { // If the direction has changed
                double x = targetPoint.getX() - slider.getX();
                double y = targetPoint.getY() - slider.getY();
                double z = targetPoint.getZ() - slider.getZ();
                moveDir = calculateDirection(x, y, z);
                brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), moveDir);
            } else {
                moveDir = optionalDir.get();
            }
            return moveDir;
        }

        private static Direction calculateDirection(double x, double y, double z) {
            double absX = Math.abs(x);
            double absY = Math.abs(y);
            double absZ = Math.abs(z);
            if (absY > absX && absY > absZ) {
                return y > 0 ? Direction.UP : Direction.DOWN;
            } else if (absX > absZ) {
                return x > 0 ? Direction.EAST : Direction.WEST;
            } else {
                return z > 0 ? Direction.SOUTH : Direction.NORTH;
            }
        }

        private static double axisDistance(double x, double y, double z, Direction direction) {
            return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
        }
    }

    /**
     * Set the path up to avoid an unbreakable block.
     */
    static class AvoidObstacles extends Behavior<Slider> {
        public AvoidObstacles() {
            super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED));
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            if (!slider.isAwake() || slider.isDeadOrDying() || slider.getBrain().getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(1) > 0) {
                return false;
            }

            Brain<?> brain = slider.getBrain();
            Direction direction = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get()).orElse(Direction.UP);
            if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            }


            AABB collisionBox = calculateAdjacentBox(slider.getBoundingBox(), direction);
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
                brain.setMemory(AetherMemoryModuleTypes.TARGET_POSITION.get(), slider.blockPosition().atY(y));
                brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), Direction.UP);
            }
            return false;
        }
    }

    static class Collide extends Behavior<Slider> {
        public Collide() {
            super(ImmutableMap.of());
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            return slider.isAwake() && !slider.isDeadOrDying() && !slider.getDeltaMovement().equals(Vec3.ZERO);
        }

        @Override
        protected boolean canStillUse(ServerLevel level, Slider slider, long gameTime) {
            return this.checkExtraStartConditions(level, slider);
        }

        @Override
        protected void tick(ServerLevel level, Slider slider, long gameTime) {
            AABB collisionBounds = new AABB(slider.getBoundingBox().minX - 0.1, slider.getBoundingBox().minY - 0.1, slider.getBoundingBox().minZ - 0.1,
                    slider.getBoundingBox().maxX + 0.1, slider.getBoundingBox().maxY + 0.1, slider.getBoundingBox().maxZ + 0.1);
            for (Entity entity : level.getEntities(slider, collisionBounds)) {
                if (entity instanceof LivingEntity livingEntity && entity.hurt(new EntityDamageSource("aether.crush", slider), 6)) {
                    if (livingEntity instanceof Player player && player.getUseItem().is(Items.SHIELD) && player.isBlocking()) {
                        player.getCooldowns().addCooldown(Items.SHIELD, 100);
                        player.stopUsingItem();
                        level.broadcastEntityEvent(player, (byte) 30);
                    }
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                    slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));

                    // Stop the slider moving
                    slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), slider.calculateMoveDelay());
                    slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), Optional.empty());
                    slider.setDeltaMovement(Vec3.ZERO);
                } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                    entity.setDeltaMovement(slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                }
            }
        }
    }

    static abstract class MoveBehavior extends Behavior<Slider> {
        public MoveBehavior(Map<MemoryModuleType<?>, MemoryStatus> pEntryCondition) {
            super(pEntryCondition);
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            return slider.isAwake() && !slider.isDeadOrDying() && slider.getBrain().getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).get() <= 0;
        }
    }

}
