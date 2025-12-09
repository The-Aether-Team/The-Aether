package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * PlacementConvertEvent is fired after a {@link com.aetherteam.aether.recipe.AetherRecipeTypes#PLACEMENT_CONVERSION} recipe is checked as existing for the placement of a block, but before a converted block is placed from the recipe.
 * <br>
 * This event is {@link CancellableCallbackImpl}.<br>
 * If the event is not canceled, the block conversion will happen.
 * <br>
 * <br>
 * This event is only fired on the {@link EnvType#SERVER} side.<br>
 * <br>
 * If this event is canceled, block conversion will not happen.
 */
public class PlacementConvertEvent extends CancellableCallbackImpl {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState oldBlockState;
    private BlockState newBlockState;

    /**
     * @param level         The {@link LevelAccessor} that the conversion occurs in.
     * @param pos           The {@link BlockPos} the conversion occurs at.
     * @param oldBlockState The old {@link BlockState} that is to be converted.
     * @param newBlockState The original result {@link BlockState} from the conversion.
     */
    protected PlacementConvertEvent(LevelAccessor level, BlockPos pos, BlockState oldBlockState, BlockState newBlockState) {
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
     *
     * @param newBlockState The new {@link BlockState}.
     */
    public void setNewBlockState(BlockState newBlockState) {
        this.newBlockState = newBlockState;
    }

    //--

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback c : callbacks) c.findPacks(event);
    });

    public interface Callback {
        void findPacks(PlacementConvertEvent event);
    }

    public static PlacementConvertEvent invokeEvent(LevelAccessor level, BlockPos pos, BlockState oldBlockState, BlockState newBlockState) {
        var event = new PlacementConvertEvent(level, pos, oldBlockState, newBlockState);

        EVENT.invoker().findPacks(event);

        return event;
    }
}
