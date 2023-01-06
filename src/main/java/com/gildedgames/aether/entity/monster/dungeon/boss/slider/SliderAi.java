package com.gildedgames.aether.entity.monster.dungeon.boss.slider;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.gildedgames.aether.entity.ai.brain.sensing.AetherSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class SliderAi {

    private static final ImmutableList<SensorType<? extends Sensor<Slider>>> SENSOR_TYPES = ImmutableList.of(AetherSensorTypes.SLIDER_PLAYER_SENSOR.get());
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, // For the StartAttacking behavior
            AetherMemoryModuleTypes.AGGRO_TRACKER.get(),
            AetherMemoryModuleTypes.MOVE_DELAY.get(),
            AetherMemoryModuleTypes.MOVE_DIRECTION.get(),
            AetherMemoryModuleTypes.TARGET_POSITION.get()
    );
    private static final ImmutableList<Activity> ACTIVITY_PRIORITY = ImmutableList.of(Activity.FIGHT, Activity.IDLE);

    public static Brain<Slider> makeBrain(Dynamic<?> dynamic) {
        Brain<Slider> brain = Brain.provider(MEMORY_TYPES, SENSOR_TYPES).makeBrain(dynamic);
        initIdleActivity(brain);
        initFightActivity(brain);
        // Initialize the aggro tracker
        brain.setMemory(AetherMemoryModuleTypes.AGGRO_TRACKER.get(), new Object2DoubleOpenHashMap<>());
        return brain;
    }

    private static void initIdleActivity(Brain<Slider> brain) {
//        brain.addActivity(Activity.IDLE, ImmutableList.of(0, new Stay));
    }

    private static void initFightActivity(Brain<Slider> brain) {
        brain.addActivity(Activity.FIGHT, 10, ImmutableList.of(StartAttacking.create(SliderAi::findNearestValidAttackTarget), new Collide(), new Crush(), new AvoidObstacles(), new SetPathUpOrDown(), new Move()));
    }

    public static void updateActivity(Slider slider) {
        slider.getBrain().setActiveActivityToFirstValid(ACTIVITY_PRIORITY);
    }

    /**
     * Reduces the aggro every second.
     */
    protected static void tick(Slider slider) {
        if (slider.tickCount % 20 != 0) {
            return;
        }

        Brain<?> brain = slider.getBrain();
        Optional<Object2DoubleMap<LivingEntity>> optional = brain.getMemory(AetherMemoryModuleTypes.AGGRO_TRACKER.get());
        if (optional.isPresent()) {
            Object2DoubleMap<LivingEntity> attackers = optional.get();
            attackers.forEach((target, oldAggro) -> {
                double aggro = oldAggro - 1;
                if (!target.isAlive() || (aggro <= 0 && !Sensor.isEntityAttackable(slider, target) || (target instanceof Player player && (player.isCreative() || player.isSpectator())))) {
                    attackers.removeDouble(target);
                } else {
                    attackers.put(target, aggro);
                }
            });
        }
    }

    /**
     * Adds aggro when attacked by a player.
     */
    protected static void wasHurtBy(Slider slider, LivingEntity attacker, float damage) {
        Brain<?> brain = slider.getBrain();
        Optional<Object2DoubleMap<LivingEntity>> optional = brain.getMemory(AetherMemoryModuleTypes.AGGRO_TRACKER.get());
        if (optional.isPresent()) {
            Object2DoubleMap<LivingEntity> attackers = optional.get();
            attackers.mergeDouble(attacker, damage, (Double::sum));
            LivingEntity target = getStrongestAttacker(slider, attackers);
            brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            brain.setMemory(MemoryModuleType.ATTACK_TARGET, target);
        }
    }

    /**
     * Returns the entity within the targeting range that has dealt the most damage.
     */
    private static LivingEntity getStrongestAttacker(Slider slider, Object2DoubleMap<LivingEntity> attackers) {
        Map.Entry<LivingEntity, Double> entry = attackers.object2DoubleEntrySet().stream().filter((entityEntry) ->
                Sensor.isEntityAttackable(slider, entityEntry.getKey())
        ).max(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return null;
        } else {
            return entry.getKey();
        }
    }

    /**
     * Finds a new target if there isn't one currently.
     */
    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(Slider slider) {
        return slider.isAwake() && !slider.isDeadOrDying() ? slider.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER) : Optional.empty();
    }

    @Nullable
    private static Vec3 getTargetPoint(Brain<?> brain) {
        Optional<Vec3> pos = brain.getMemory(AetherMemoryModuleTypes.TARGET_POSITION.get());
        if (pos.isPresent()) {
            return pos.get();
        } else {
            Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
            return target.map(Entity::position).orElse(null);
        }
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
            super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED, AetherMemoryModuleTypes.TARGET_POSITION.get(), MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED));
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            if (!slider.isAwake() || slider.isDeadOrDying()) {
                return false;
            }

            Brain<?> brain = slider.getBrain();

            int moveDelay = brain.getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(0);
            brain.setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), --moveDelay);

            return moveDelay <= 0;
        }

        @Override
        protected boolean canStillUse(ServerLevel level, Slider slider, long gameTime) {
            if (!slider.isAwake() || slider.isDeadOrDying()) {
                return false;
            }

            Brain<?> brain = slider.getBrain();

            if (brain.getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(0) > 0) {
                return false;
            }

            return !slider.horizontalCollision;
        }

        @Override
        protected void start(ServerLevel level, Slider slider, long pGameTime) {
            slider.getBrain().eraseMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
            slider.playSound(slider.getMoveSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
        }

        @Override
        protected void tick(ServerLevel level, Slider slider, long gameTime) {
            Brain<?> brain = slider.getBrain();
            Vec3 targetPoint = getTargetPoint(brain);

            // Move along the calculated path
            if (targetPoint == null) {
                this.doStop(level, slider, gameTime);
                return;
            }

            Direction moveDir = getMoveDirection(slider, targetPoint);

            if (axisDistance(targetPoint.x - slider.getX(), targetPoint.y - slider.getY(), targetPoint.z - slider.getZ(), moveDir) <= 0) {
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

        /**
         * Get the move direction if it already exists, or calculate a new one.
         */
        private static Direction getMoveDirection(Slider slider, Vec3 targetPoint) {
            Brain<?> brain = slider.getBrain();
            Optional<Direction> optionalDir = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get());
            Direction moveDir;

            if (optionalDir.isEmpty()) { // If the direction has changed
                double x = targetPoint.x - slider.getX();
                double y = targetPoint.y - slider.getY();
                double z = targetPoint.z - slider.getZ();
                moveDir = calculateDirection(x, y, z);
                brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), moveDir);
            } else {
                moveDir = optionalDir.get();
            }
            return moveDir;
        }

        private static double axisDistance(double x, double y, double z, Direction direction) {
            return x * direction.getStepX() + y * direction.getStepY() + z * direction.getStepZ();
        }
    }

    static class SetPathUpOrDown extends Behavior<Slider> {
        public SetPathUpOrDown() {
            super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED, AetherMemoryModuleTypes.TARGET_POSITION.get(), MemoryStatus.VALUE_ABSENT));
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel pLevel, Slider slider) {
            Brain<?> brain = slider.getBrain();
            // Run this behavior only once between each movement cycle.
            if (brain.getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(2) != 1) {
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

            currentPath = calculateAdjacentBox(currentPath, direction);
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

    /**
     * Set the path up to avoid an unbreakable block.
     */
    static class AvoidObstacles extends Behavior<Slider> {
        public AvoidObstacles() {
            super(ImmutableMap.of(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), MemoryStatus.REGISTERED));
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            if (!slider.isAwake() || slider.isDeadOrDying() || slider.getBrain().getMemory(AetherMemoryModuleTypes.MOVE_DELAY.get()).orElse(1) != 1) {
                return false;
            }

            Brain<?> brain = slider.getBrain();
            Direction direction = brain.getMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get()).orElse(Direction.UP);
            return direction.getAxis() != Direction.Axis.Y;
        }

        @Override
        protected void start(ServerLevel level, Slider slider, long gameTime) {
            Brain<?> brain = slider.getBrain();
            Vec3 targetPos = getTargetPoint(brain);
            if (targetPos == null) {
                return;
            }

            Direction direction = calculateDirection(targetPos.x - slider.getX(), targetPos.y - slider.getY(), targetPos.z - slider.getZ());
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
                Vec3 currentPos = slider.position();
                brain.setMemory(AetherMemoryModuleTypes.TARGET_POSITION.get(), new Vec3(currentPos.x, y, currentPos.z));
                brain.setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), Direction.UP);
            }
        }
    }

    static class Crush extends Behavior<Slider> {
        public Crush() {
            super(ImmutableMap.of());
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Slider slider) {
            return slider.isAwake() && !slider.isDeadOrDying() && (slider.horizontalCollision || slider.verticalCollision);
        }

        @Override
        protected void start(ServerLevel level, Slider slider, long gameTime) {
            boolean crushed = false;
            if (ForgeEventFactory.getMobGriefingEvent(level, slider)) {
                AABB crushBox = slider.getBoundingBox().inflate(0.2);
                for(BlockPos pos : BlockPos.betweenClosed(Mth.floor(crushBox.minX), Mth.floor(crushBox.minY), Mth.floor(crushBox.minZ), Mth.floor(crushBox.maxX), Mth.floor(crushBox.maxY), Mth.floor(crushBox.maxZ))) {
                    BlockState blockState = slider.level.getBlockState(pos);
                    if (!blockState.isAir()) {
                        if (!blockState.is(AetherTags.Blocks.SLIDER_UNBREAKABLE)) {
                            crushed = slider.level.destroyBlock(pos, true, slider) || crushed;
                            slider.blockDestroySmoke(pos);
                        }
                    }
                }
            }
            if (crushed) {
                slider.level.playSound(null, slider.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 3.0F, (0.625F + (slider.getRandom().nextFloat() - slider.getRandom().nextFloat()) * 0.2F) * 0.7F);
                slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
                slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), slider.calculateMoveDelay());
                slider.setDeltaMovement(Vec3.ZERO);
            }
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

                    // Stop the slider moving
                    slider.playSound(slider.getCollideSound(), 2.5F, 1.0F / (slider.getRandom().nextFloat() * 0.2F + 0.9F));
                    slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DELAY.get(), slider.calculateMoveDelay());
                    slider.getBrain().setMemory(AetherMemoryModuleTypes.MOVE_DIRECTION.get(), Optional.empty());
                    slider.setDeltaMovement(Vec3.ZERO);
                } else if (!(entity instanceof Player player && player.isCreative()) && !(entity instanceof Slider)) {
                    entity.setDeltaMovement(slider.getDeltaMovement().multiply(4.0, 1.0, 4.0).add(0.0, 0.25, 0.0));
                }
            }
        }
    }

}
