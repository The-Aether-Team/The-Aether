package com.gildedgames.aether.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ConstantsUtil {
    /**
     * Y-vector of an entity's delta movement when on the ground.
     */
    public static double DEFAULT_DELTA_MOVEMENT_Y = -0.0784000015258789;

    /**
     * Causes a block update.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_BLOCK_UPDATE = 1;

    /**
     * Send the change to clients.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_CLIENT_CHANGE = 2;

    /**
     * Prevent the block from being re-rendered.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_PREVENT_RERENDER = 4;

    /**
     * Force any re-renders to run on the main thread instead.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_FORCE_MAIN_RERENDER = 8;

    /**
     * Prevent neighbor reactions (e.g. fences connecting, observers pulsing).
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_PREVENT_NEIGHBOR_UPDATE = 16;

    /**
     * Prevent neighbor reactions from spawning drops.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_PREVENT_NEIGHBOR_DROPS = 32;

    /**
     * Signify the block is being moved.
     * @see net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)
     */
    public static int FLAG_BLOCK_MOVE = 64;
}
