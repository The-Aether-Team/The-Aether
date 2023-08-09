package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface AerogelCulling {
    /**
     * Skips rendering for Aerogel blocks that are neighboring this block if the bordering face shapes match.
     * @param level The {@link Level} that the block is in.
     * @param pos The {@link BlockPos} of this block.
     * @param state The {@link BlockState} of the block.
     * @param neighborState The {@link BlockState} of the neighboring block.
     * @param dir The {@link Direction} to the neighboring state.
     * @return Whether the neighbor block should skip rendering the neighboring face, as a {@link Boolean}.
     */
    default boolean shouldHideNeighboringAerogelFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (neighborState.getBlock() instanceof AerogelCulling) {
            List<AABB> faceBounds = state.getBlockSupportShape(level, pos).getFaceShape(dir).toAabbs();
            List<AABB> neighborFaceBounds = neighborState.getBlockSupportShape(level, pos.offset(dir.getNormal())).getFaceShape(dir.getOpposite()).toAabbs();
            return faceBounds.equals(neighborFaceBounds);
        }
        return false;
    }
}
