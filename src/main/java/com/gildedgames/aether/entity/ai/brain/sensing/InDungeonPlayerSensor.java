package com.gildedgames.aether.entity.ai.brain.sensing;

import com.gildedgames.aether.entity.BossMob;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * [VANILLA COPY] - PlayerSensor
 * Changed to track players within the dungeon instead of within a 16 block radius.
 */
public class InDungeonPlayerSensor<T extends Mob & BossMob<T>> extends Sensor<T> {
    @Override
    protected void doTick(ServerLevel level, T entity) {
        List<Player> targets = level.players().stream().filter(EntitySelector.NO_SPECTATORS).filter((target) -> {
            return entity.getDungeon().isPlayerWithinRoom(target);
        }).sorted(Comparator.comparingDouble(entity::distanceToSqr)).collect(Collectors.toList());
        Brain<?> brain = entity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, targets);

        List<Player> nearestVisiblePlayers = targets.stream().filter((target) -> isEntityTargetable(entity, target)).toList();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, nearestVisiblePlayers.isEmpty() ? null : nearestVisiblePlayers.get(0));

        Optional<Player> nearestPlayer = nearestVisiblePlayers.stream().filter((target) -> isEntityAttackable(entity, target)).findFirst();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, nearestPlayer);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
    }
}
