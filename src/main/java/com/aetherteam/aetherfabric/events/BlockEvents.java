package com.aetherteam.aetherfabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import java.util.EnumSet;
import java.util.function.BiConsumer;

public class BlockEvents {

    public static final Event<NeighborUpdate> NEIGHBOR_UPDATE = EventFactory.createArrayBacked(NeighborUpdate.class, invokers -> (level, pos, state, notifiedSides, forceRedstoneUpdate, callback) -> {
        for (var invoker : invokers) invoker.onNeighborUpdate(level, pos, state, notifiedSides, forceRedstoneUpdate, callback);
    });

    public static final Event<TreeGrow> ON_TREE_GROW = EventFactory.createArrayBacked(TreeGrow.class, invokers -> (state, level, placeFunction, randomSource, pos, config, callback) -> {
        for (var invoker : invokers) invoker.onTreeGrow(state, level, placeFunction, randomSource, pos, config, callback);
    });

    public interface NeighborUpdate {
        void onNeighborUpdate(Level level, BlockPos pos, BlockState state, EnumSet<Direction> notifiedSides, boolean forceRedstoneUpdate, CancellableCallback callback);
    }

    public interface TreeGrow {
        void onTreeGrow(BlockState state, LevelReader level, BiConsumer<BlockPos, BlockState> placeFunction, RandomSource randomSource, BlockPos pos, TreeConfiguration config, CancellableCallback callback);
    }
}
