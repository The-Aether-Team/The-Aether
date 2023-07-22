package com.aetherteam.aether.block;

import com.aetherteam.aether.data.resources.AetherDamageTypes;
import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 * [VANILLA COPY] - {@link net.minecraft.world.level.block.Fallable}.
 */
public interface Floatable {
    default void onCollide(Level level, BlockPos pos, BlockState state, BlockState oldState, FloatingBlockEntity floatingBlock) { }

    default void onBrokenAfterCollide(Level level, BlockPos pos, FloatingBlockEntity floatingBlock) { }

    default DamageSource getFallDamageSource(Entity entity) {
        return AetherDamageTypes.damageSource(entity.level, AetherDamageTypes.FLOATING_BLOCK);
    }

    default Predicate<Entity> getHurtsEntitySelector() {
        return EntitySelector.NO_SPECTATORS;
    }
}
