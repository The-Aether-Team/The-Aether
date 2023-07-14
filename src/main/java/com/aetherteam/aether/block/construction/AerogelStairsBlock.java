package com.aetherteam.aether.block.construction;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class AerogelStairsBlock extends StairBlock {
    /**
     * Copy of {@link Block#OCCLUSION_CACHE}.
     */
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockStatePairKey>> OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
        Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> occlusionCache = new Object2ByteLinkedOpenHashMap<>() {
            @Override
            protected void rehash(int value) { }
        };
        occlusionCache.defaultReturnValue((byte) 127);
        return occlusionCache;
    });

    public AerogelStairsBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }

    /**
     * Determines the amount of light this will block.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     * @param state The {@link BlockState} of the block.
     * @param level The {@link Level} the block is in.
     * @param pos The {@link BlockPos} of the block.
     * @return The {@link Integer} of how many light levels are blocked, plus 2 extra by default.
     */
    @SuppressWarnings("deprecation")
    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 3;
    }

    /**
     * Relevant to lighting checks for blocks that aren't full cubes and neighboring blocks.
     * @param state The {@link BlockState} of the block.
     * @return Whether to use the shape for light occlusion, as a {@link Boolean}.
     */
    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    /**
     * @see net.minecraftforge.common.extensions.IForgeBlock#supportsExternalFaceHiding(BlockState)
     */
    @Override
    public boolean supportsExternalFaceHiding(BlockState state) {
        return true;
    }

    /**
     * Based on {@link Block#hidesNeighborFace(BlockGetter, BlockPos, BlockState, BlockState, Direction)}.
     */
    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction direction) {
        if (neighborState.is(this)) {
            Block.BlockStatePairKey blockStatePairKey = new BlockStatePairKey(neighborState, state, direction.getOpposite());
            Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> occlusionCache = OCCLUSION_CACHE.get();
            byte cacheByte = occlusionCache.getAndMoveToFirst(blockStatePairKey);
            if (cacheByte != 127) {
                return cacheByte != 0;
            }
            VoxelShape neighborShape = neighborState.getFaceOcclusionShape(level, pos.relative(direction), direction.getOpposite());
            if (neighborShape.isEmpty()) {
                return false;
            }
            VoxelShape shape = state.getFaceOcclusionShape(level, pos, direction);
            boolean differing = !Shapes.joinIsNotEmpty(neighborShape, shape, BooleanOp.ONLY_FIRST);
            if (occlusionCache.size() == 2048) {
                occlusionCache.removeLastByte();
            }
            occlusionCache.putAndMoveToFirst(blockStatePairKey, (byte) (differing ? 1 : 0));
            return differing;
        }
        return super.hidesNeighborFace(level, pos, state, neighborState, direction);
    }
}
