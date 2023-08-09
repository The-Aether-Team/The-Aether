package com.aetherteam.aether.block;

import com.aetherteam.aether.event.FreezeEvent;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import net.minecraft.commands.CommandFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface FreezingBehavior<T> {
    /**
     * Cause a block update and send this change to the client.
     */
    int FLAG_SHELL = 1 | 2;
    /**
     * Send this change to the client and prevent any block updates from neighboring blocks.
     */
    int FLAG_VOLUME = 1 | 2 | 16;

    /**
     * Loops over the area of the positive quarter of a circle, then freezes blocks within that quarter along with the 3 quarters using {@link FreezingBehavior#quarters(Level, BlockPos, int, int, int, Object, int)}.<br><br>
     * Note 1: All 1/4 sections (quarters) of a circle are symmetrical, which is why only one quarter needs to be looped over, and the x and z positions gotten from the loop can be used to freeze in the other 3 quarters safely.<br><br>
     * Note 2: This is also modified to work with a sphere by looping through the positive area in its space (the +x,+y,+z octant), but freezing blocks in both positive and negative y positions relative from the origin.<br><br>
     * <a href="https://www.desmos.com/calculator/psqynhk21k">Relevant math showcasing quarters of circles can be found here.</a><br><br>
     * The loop also checks whether the block being frozen was the first block in the loop iteration, meaning the outermost block,
     * because the loop for each coordinate starts at the radius (as far away from the center as possible) and decreases (moves inwards towards the center).
     * It does this so that it can separate what update flags are used for the freezing depending on whether a block was at the exterior (see: {@link FreezingBehavior#FLAG_SHELL}) or at the interior (see: {@link FreezingBehavior#FLAG_VOLUME}),
     * to minimize the amount of block updates caused.
     * @param level The {@link Level} to perform the freezing in.
     * @param origin The origin {@link BlockPos}; the center of the circle.
     * @param source The source causing the freezing, which is accepted as {@link T}.
     * @param radius The radius of the circle/sphere as a {@link Float}.
     * @return An {@link Integer} added up from the blocks being frozen, with +1 corresponding to every successfully frozen block.
     */
    default int freezeBlocks(Level level, BlockPos origin, T source, float radius) {
        float radiusSq = radius * radius;

        int blocksFrozen = 0;

        for (int x = (int) radius; x >= 0; x--) {

            boolean firstXZ = true; // Reset every time the x iteration changes.
            for (int z = (int) radius; z >= 0; z--) {
                int xzLengthSq = x * x + z * z;

                if (xzLengthSq > radiusSq) continue; // Restarts the loop at the next iteration, skipping the following code. This ensures freezing never occurs beyond the radius.

                blocksFrozen += this.quarters(level, origin, x, 0, z, source, firstXZ ? FLAG_SHELL : FLAG_VOLUME); // Only places along the center-most y region (at 0), meaning this will only freeze within a circle.
                firstXZ = false;

                boolean firstY = true; // Reset every time the z iteration changes, or the x iteration changes causing the z iterations to restart.
                for (int y = (int) radius; y >= 0; y--) { // Responsible for freezing the rest of the blocks, forming the sphere shape, in addition to the circle frozen at y=0.

                    if (xzLengthSq + y * y > radiusSq) continue; // Restarts the loop at the next iteration, skipping the following code. This ensures freezing never occurs beyond the radius.

                    int placementFlag = firstY ? FLAG_SHELL : FLAG_VOLUME;
                    blocksFrozen += this.quarters(level, origin, x, y, z, source, placementFlag); // Place in positive space
                    blocksFrozen += this.quarters(level, origin, x, -y, z, source, placementFlag); // Place in negative space
                    firstY = false;
                }
            }
        }

        // Update the center manually too
        return this.freezeFromRecipe(level, origin, origin, source, FLAG_SHELL) + blocksFrozen;
    }

    /**
     * Freezes a block in all four quarters/quadrants of a circle (+x,+z; +x,-z; -x,+z; -x,-z), offset equally from the origin by the given x and z parameters.
     * @param level The {@link Level} to perform the freezing in.
     * @param origin The origin {@link BlockPos}; the center of the circle.
     * @param dX The x {@link Integer} offset from the center of the circle.
     * @param dY The y {@link Integer} offset from the center of the circle.
     * @param dZ The z {@link Integer} offset from the center of the circle.
     * @param source The source causing the freezing, which is accepted as {@link T}.
     * @param flag The flag to use for block placement when freezing.
     * @return An {@link Integer} added up from the blocks being frozen. See {@link FreezingBehavior#freezeFromRecipe(Level, BlockPos, BlockPos, Object, int)}.
     */
    private int quarters(Level level, BlockPos origin, int dX, int dY, int dZ, T source, int flag) {
        return this.freezeFromRecipe(level, origin.offset(dX, dY, dZ), origin, source, flag)
                + this.freezeFromRecipe(level, origin.offset(-dZ, dY, dX), origin, source, flag)
                + this.freezeFromRecipe(level, origin.offset(-dX, dY, -dZ), origin, source, flag)
                + this.freezeFromRecipe(level, origin.offset(dZ, dY, -dX), origin, source, flag);
    }

    /**
     * Handles pre-block modification freezing behavior, should be used by subclasses for recipe and source-specific code.
     * @param level The {@link Level} to freeze the blocks in.
     * @param pos The {@link BlockPos} the freezing occurred at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param source The source causing the freezing, which is accepted as {@link T}.
     * @param flag The {@link Integer} representing the block placement flag (see {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int)}).
     * @return An {@link Integer} 1 if a block was successfully frozen, or a 0 if it wasn't.
     */
    int freezeFromRecipe(Level level, BlockPos pos, BlockPos origin, T source, int flag);

    /**
     * Freezes (sets) a block at a position if the {@link FreezeEvent} isn't cancelled. Also schedules a tick if the block can randomly tick, and plays a lava extinguishing sound if the old block is in the {@link FluidTags#LAVA} tag.
     * @param level The {@link Level} to perform the freezing in.
     * @param pos The {@link BlockPos} to freeze at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param oldBlockState The original {@link BlockState} being frozen.
     * @param newBlockState The new {@link BlockState} to freeze into.
     * @param function The {@link CommandFunction.CacheableFunction} to run after freezing.
     * @param source The source causing the freezing, which is accepted as {@link T}.
     * @param flag The {@link Integer} placement flag.
     * @return An {@link Integer} 0 if the block failed to freeze or 1 if it succeeded
     */
    default int freezeBlockAt(Level level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, @Nullable CommandFunction.CacheableFunction function, T source, int flag) {
        FreezeEvent event = this.onFreeze(level, pos, origin, oldBlockState, newBlockState, source);
        if (!event.isCanceled()) {
            level.setBlock(pos, newBlockState, flag);
            if (newBlockState.isRandomlyTicking()) {
                level.scheduleTick(pos, newBlockState.getBlock(), Mth.nextInt(level.getRandom(), 60, 120));
            }
            BlockStateRecipeUtil.executeFunction(level, pos, function);
            return 1;
        }
        return 0;
    }

    /**
     * Event hook call for freezing blocks, used by subclasses.
     * @param level The {@link Level} to perform the freezing in.
     * @param pos The {@link BlockPos} to freeze at.
     * @param origin The {@link BlockPos} of the source that is causing the freezing.
     * @param oldBlockState The original {@link BlockState} being frozen.
     * @param newBlockState The new {@link BlockState} to freeze into.
     * @param source The source causing the freezing, which is accepted as {@link T}.
     * @return The {@link FreezeEvent} for this behavior.
     */
    FreezeEvent onFreeze(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState oldBlockState, BlockState newBlockState, T source);
}
