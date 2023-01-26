package com.gildedgames.aether.entity.monster.dungeon.boss.slider;

import com.gildedgames.aether.entity.ai.brain.memory.AetherMemoryModuleTypes;
import com.gildedgames.aether.entity.ai.brain.sensing.AetherSensorTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
            AetherMemoryModuleTypes.HAS_ATTACKED.get(),
            AetherMemoryModuleTypes.MOVE_DELAY.get(),
            AetherMemoryModuleTypes.MOVE_DIRECTION.get(),
            AetherMemoryModuleTypes.TARGET_POSITION.get()
    );
    private static final ImmutableList<Activity> ACTIVITY_PRIORITY = ImmutableList.of(Activity.FIGHT, Activity.IDLE);

    public static Brain<Slider> makeBrain(Dynamic<?> dynamic) {
        Brain<Slider> brain = Brain.provider(MEMORY_TYPES, SENSOR_TYPES).makeBrain(dynamic);
        initFightActivity(brain);
        // Initialize the aggro tracker
        brain.setMemory(AetherMemoryModuleTypes.AGGRO_TRACKER.get(), new Object2DoubleOpenHashMap<>());
        return brain;
    }

    private static void initFightActivity(Brain<Slider> brain) {
        brain.addActivity(Activity.FIGHT, 10, ImmutableList.of(
                StartAttacking.create(SliderAi::findNearestValidAttackTarget),
                new Collide(),
                new Crush(),
                new BackOffAfterAttack(),
                new SetPathUpOrDown(),
                new AvoidObstacles(),
                new Move()
        ));
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
    public static Vec3 getTargetPoint(Brain<?> brain) {
        Optional<Vec3> pos = brain.getMemory(AetherMemoryModuleTypes.TARGET_POSITION.get());
        if (pos.isPresent()) {
            return pos.get();
        } else {
            Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
            return target.map(Entity::position).orElse(null);
        }
    }

    public static Direction calculateDirection(double x, double y, double z) {
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
    public static AABB calculateAdjacentBox(AABB box, Direction direction) {
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
}
