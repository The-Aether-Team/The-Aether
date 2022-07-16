package com.gildedgames.aether.block.construction;

import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

public class AerogelStairsBlock extends StairBlock {
    public AerogelStairsBlock(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 3;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean supportsExternalFaceHiding(BlockState state) {
        return true;
    }

    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<BlockStatePairKey>> NEIGHBOR_OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
        var map = new Object2ByteLinkedOpenHashMap<BlockStatePairKey>() {
            @Override
            protected void rehash(int p_49979_) {}
        };
        map.defaultReturnValue((byte)127);
        return map;
    });

    /**
     * TODO: It is theoretically possible to implement this instead by overriding {@link #skipRendering(BlockState, BlockState, Direction)}
     */
    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (neighborState.is(this)) {
            var key = new BlockStatePairKey(neighborState, state, dir.getOpposite());
            var occlusion_cache = NEIGHBOR_OCCLUSION_CACHE.get();
            byte b = occlusion_cache.getAndMoveToFirst(key);
            if (b != 127) {
                return b != 0;
            }
            var voxelshape = neighborState.getFaceOcclusionShape(level, pos.relative(dir), dir.getOpposite());
            if (voxelshape.isEmpty()) {
                return false;
            }
            var voxelshape1 = state.getFaceOcclusionShape(level, pos, dir);
            boolean differing = !Shapes.joinIsNotEmpty(voxelshape, voxelshape1, BooleanOp.ONLY_FIRST);
            if (occlusion_cache.size() == 2048) {
                occlusion_cache.removeLastByte();
            }
            occlusion_cache.putAndMoveToFirst(key, (byte)(differing ? 1 : 0));
            return differing;
        }
        return super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }
}
