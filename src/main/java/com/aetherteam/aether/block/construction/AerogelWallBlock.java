package com.aetherteam.aether.block.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AerogelWallBlock extends WallBlock {
    public AerogelWallBlock(Properties properties) {
        super(properties);
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
     * [CODE COPY] - {@link net.minecraft.world.level.block.TransparentBlock#getVisualShape(BlockState, BlockGetter, BlockPos, CollisionContext)}.
     */
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}
