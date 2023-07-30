package com.aetherteam.aether.entity.ai.brain.sensing;

import com.aetherteam.aether.entity.AetherBossMob;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.ai.sensing.PlayerSensor}.<br><br>
 * Changed to track players within the dungeon instead of within a 16 block radius.
 */
public class InDungeonPlayerSensor<T extends Mob & AetherBossMob<T>> extends Sensor<T> {
    private static final TargetingConditions TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forNonCombat().range(16.0).ignoreInvisibilityTesting().ignoreLineOfSight();
    private static final TargetingConditions ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING = TargetingConditions.forCombat().range(16.0).ignoreInvisibilityTesting().ignoreLineOfSight();

    @Override
    protected void doTick(ServerLevel level, T entity) {
        List<Player> targets = level.players().stream().filter(EntitySelector.NO_SPECTATORS).filter((target) -> entity.getDungeon() != null
                ? entity.getDungeon().isPlayerTracked(target)
                : entity.closerThan(target, 16.0)).sorted(Comparator.comparingDouble(entity::distanceToSqr)).collect(Collectors.toList());

        Brain<?> brain = entity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, targets);

        List<Player> nearestVisiblePlayers = targets.stream().filter((target) -> InDungeonPlayerSensor.isEntityTargetable(entity, target)).toList();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, nearestVisiblePlayers.isEmpty() ? null : nearestVisiblePlayers.get(0));

        Optional<Player> nearestPlayer = nearestVisiblePlayers.stream().filter((target) -> InDungeonPlayerSensor.isEntityAttackable(entity, target)).findFirst();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, nearestPlayer);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
    }

    public static boolean isEntityTargetable(LivingEntity livingEntity, LivingEntity target) {
        return TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.test(livingEntity, target);
    }

    public static boolean isEntityAttackable(LivingEntity attacker, LivingEntity target) {
        return ATTACK_TARGET_CONDITIONS_IGNORE_INVISIBILITY_TESTING.test(attacker, target);
    }
}
