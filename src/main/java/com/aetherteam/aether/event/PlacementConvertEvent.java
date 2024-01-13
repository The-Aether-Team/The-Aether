package com.aetherteam.aether.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * PlacementConvertEvent is fired after a {@link com.aetherteam.aether.recipe.AetherRecipeTypes#PLACEMENT_CONVERSION} recipe is checked as existing for the placement of a block, but before a converted block is placed from the recipe.
 * <br>
 * This event is {@link Cancelable}.<br>
 * If the event is not canceled, the block conversion will happen.
 * <br>
 * This event does not have a result. {@link net.minecraftforge.eventbus.api.Event.HasResult}<br>
 * <br>
 * This event is fired on the {@link MinecraftForge#EVENT_BUS}.<br>
 * <br>
 * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
 * <br>
 * If this event is canceled, block conversion will not happen.
 */
public class PlacementConvertEvent extends BaseEvent {
    public static final Event<PlacementConvert> EVENT = EventFactory.createArrayBacked(PlacementConvert.class, callbacks -> event -> {
        for (PlacementConvert callback : callbacks)
            callback.onPlacementConvert(event);
    });
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

    @Override
    public void sendEvent() {
        EVENT.invoker().onPlacementConvert(this);
    }

    @FunctionalInterface
    public interface PlacementConvert {
        void onPlacementConvert(PlacementConvertEvent event);
    }
}
