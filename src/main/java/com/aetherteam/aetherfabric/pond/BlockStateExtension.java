package com.aetherteam.aetherfabric.pond;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface BlockStateExtension {

    default boolean aetherFabric$hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState neighborState, Direction dir) {
        return throwUnimplementedException();
    }

    /**
     * Whether this block allows a neighboring block to hide the face of this block it touches.
     * If this returns true, {@link BlockStateExtension#aetherFabric$hidesNeighborFace(BlockGetter, BlockPos, BlockState, Direction)}
     * will be called on the neighboring block.
     */
    default boolean aetherFabric$supportsExternalFaceHiding() {
        return throwUnimplementedException();
    }

    default Float aetherFabric$getExplosionResistance(BlockGetter level, BlockPos pos, Explosion explosion) {
        return throwUnimplementedException();
    }

    static float aetherFabric$getFriction(Entity entity, Supplier<Float> frictionGetter) {
        var blockPos = entity.getBlockPosBelowThatAffectsMyMovement();
        var level = entity.level();

        var alternativeFriction = level.getBlockState(blockPos).aetherFabric$getFriction(level, blockPos, entity);

        return alternativeFriction != null ? alternativeFriction : frictionGetter.get();
    }

    default Float aetherFabric$getFriction(LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
