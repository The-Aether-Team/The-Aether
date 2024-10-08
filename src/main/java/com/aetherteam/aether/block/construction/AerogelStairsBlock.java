package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class AerogelStairsBlock extends StairBlock implements AerogelCulling {
    public AerogelStairsBlock(BlockState state, Properties properties) {
        super(state, properties);
    }

    /**
     * Determines the amount of light this will block.<br><br>
     *
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos   The {@link BlockPos} of the block.
     * @return The {@link Integer} of how many light levels are blocked, plus 2 extra by default.
     */
    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 3;
    }

    /**
     * Relevant to lighting checks for blocks that aren't full cubes and neighboring blocks.
     *
     * @param state The {@link BlockState} of the block.
     * @return Whether to use the shape for light occlusion, as a {@link Boolean}.
     */
    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    /**
     * @see net.neoforged.neoforge.common.extensions.IBlockExtension#supportsExternalFaceHiding(BlockState)
     */
    @Override
    public boolean supportsExternalFaceHiding(BlockState state) {
        return true;
    }

    /**
     * Skips rendering for Aerogel blocks that are neighboring this block, using {@link AerogelCulling#shouldHideNeighboringAerogelFace(BlockGetter, BlockPos, BlockState, BlockState, Direction)}.
     *
     * @param level         The {@link Level} that the block is in.
     * @param pos           The {@link BlockPos} of this block.
     * @param state         The {@link BlockState} of the block.
     * @param neighborState The {@link BlockState} of the neighboring block.
     * @param dir           The {@link Direction} to the neighboring state.
     * @return Whether the neighbor block should skip rendering the neighboring face, as a {@link Boolean}.
     */
    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return AerogelCulling.super.shouldHideNeighboringAerogelFace(level, pos, state, neighborState, dir);
    }
}
