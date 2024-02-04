package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.LogicalSide;

/**
 * PlacementConvertEvent is fired after a {@link com.aetherteam.aether.recipe.AetherRecipeTypes#PLACEMENT_CONVERSION} recipe is checked as existing for the placement of a block, but before a converted block is placed from the recipe.
 * <br>
 * This event is {@link ICancellableEvent}.<br>
 * If the event is not canceled, the block conversion will happen.
 * <br>
 * This event is fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, block conversion will not happen.
 */
public class PlacementConvertEvent extends Event implements ICancellableEvent {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState oldBlockState;
    private BlockState newBlockState;

    /**
     * @param level The {@link LevelAccessor} that the conversion occurs in.
     * @param pos The {@link BlockPos} the conversion occurs at.
     * @param oldBlockState The old {@link BlockState} that is to be converted.
     * @param newBlockState The original result {@link BlockState} from the conversion.
     */
    public PlacementConvertEvent(LevelAccessor level, BlockPos pos, BlockState oldBlockState, BlockState newBlockState) {
        this.level = level;
        this.pos = pos;
        this.oldBlockState = oldBlockState;
        this.newBlockState = newBlockState;
    }

    /**
     * @return The {@link LevelAccessor} that the conversion occurs in.
     */
    public LevelAccessor getLevel() {
        return this.level;
    }

    /**
     * @return The {@link BlockPos} the conversion occurs at.
     */
    public BlockPos getPos() {
        return this.pos;
    }

    /**
     * @return The old {@link BlockState} that is to be converted.
     */
    public BlockState getOldBlockState() {
        return this.oldBlockState;
    }

    /**
     * @return The result {@link BlockState} from the conversion.
     */
    public BlockState getNewBlockState() {
        return this.newBlockState;
    }

    /**
     * Sets a new block to result from the conversion.
     * @param newBlockState The new {@link BlockState}.
     */
    public void setNewBlockState(BlockState newBlockState) {
        this.newBlockState = newBlockState;
    }
}
