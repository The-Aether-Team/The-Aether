package com.aetherteam.aether.event;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * FreezeEvent is fired when an event for a freezing recipe occurs.<br>
 * If a method utilizes this {@link BaseEvent} as its parameter, the method will receive every child event of this class.<br>
 * <br>
 */
public class FreezeEvent extends BaseEvent {
    public static final Event<FreezeCallback> FREEZE = EventFactory.createArrayBacked(FreezeCallback.class, callbacks -> event -> {
        for (FreezeCallback e : callbacks)
            e.onFreeze(event);
    });
    public static final Event<FreezeBlockCallback> FREEZE_FROM_BLOCK = EventFactory.createArrayBacked(FreezeBlockCallback.class, callbacks -> event -> {
        for (FreezeBlockCallback e : callbacks)
            e.onFreezeBlock(event);
    });
    public static final Event<FreezeItemCallback> FREEZE_ITEM_BLOCK = EventFactory.createArrayBacked(FreezeItemCallback.class, callbacks -> event -> {
        for (FreezeItemCallback e : callbacks)
            e.onFreezeItem(event);
    });

    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState priorBlock;
    private BlockState frozenBlock;

    /**
     * @param level The {@link LevelAccessor} that the freezing is occurring in.
     * @param pos The {@link BlockPos} that the freezing is occurring at.
     * @param priorBlock The old {@link BlockState} that is to be frozen.
     * @param frozenBlock The original result {@link BlockState} from the freezing.
     */
    public FreezeEvent(LevelAccessor level, BlockPos pos, BlockState priorBlock, BlockState frozenBlock) {
        this.level = level;
        this.pos = pos;
        this.priorBlock = priorBlock;
        this.frozenBlock = frozenBlock;
    }

    /**
     * @return The {@link LevelAccessor} that the freezing is occurring in.
     */
    public LevelAccessor getLevel() {
        return this.level;
    }

    /**
     * @return The {@link BlockPos} that the freezing is occurring at.
     */
    public BlockPos getPos() {
        return this.pos;
    }

    /**
     * @return The old {@link BlockState} that is to be frozen.
     */
    public BlockState getPriorBlock() {
        return this.priorBlock;
    }

    /**
     * @return The result {@link BlockState} from the freezing.
     */
    public BlockState getFrozenBlock() {
        return this.frozenBlock;
    }

    /**
     * Sets a new block to result from the freezing.
     * @param frozenBlock The new {@link BlockState}.
     */
    public void setFrozenBlock(BlockState frozenBlock) {
        this.frozenBlock = frozenBlock;
    }

    @Override
    public void sendEvent() {
        FREEZE.invoker().onFreeze(this);
    }

    /**
     * FreezeEvent.FreezeFromBlock is fired for freezing recipes triggered by blocks.
     * <br>
     * This event is cancelable.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event does not have a result.<br>
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromBlock extends FreezeEvent {
        private final BlockPos sourcePos;
        private final BlockState sourceBlock;

        /**
         * @param level The {@link LevelAccessor} that the freezing is occurring in.
         * @param pos The {@link BlockPos} that the freezing is occurring at.
         * @param sourcePos The {@link BlockPos} of the source that is causing the freezing.
         * @param priorBlock The old {@link BlockState} that is to be frozen.
         * @param frozenBlock The original result {@link BlockState} from the freezing.
         * @param sourceBlock The source {@link BlockState} performing the freezing.
         */
        public FreezeFromBlock(LevelAccessor level, BlockPos pos, BlockPos sourcePos, BlockState priorBlock, BlockState frozenBlock, BlockState sourceBlock) {
            super(level, pos, priorBlock, frozenBlock);
            this.sourcePos = sourcePos;
            this.sourceBlock = sourceBlock;
        }

        /**
         * @return The source {@link BlockState} performing the freezing.
         */
        public BlockState getSourceBlock() {
            return this.sourceBlock;
        }

        /**
         * @return The {@link BlockPos} of the source that is causing the freezing.
         */
        public BlockPos getSourcePos() {
            return this.sourcePos;
        }

        @Override
        public void sendEvent() {
            FREEZE_FROM_BLOCK.invoker().onFreezeBlock(this);
        }
    }

    /**
     * FreezeEvent.FreezeFromItem is fired for freezing recipes triggered by items.
     * <br>
     * This event is cancelable.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event does not have a result.<br>
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromItem extends FreezeEvent {
        private final ItemStack sourceStack;

        /**
         * @param level The {@link LevelAccessor} that the freezing is occurring in.
         * @param pos The {@link BlockPos} that the freezing is occurring at.
         * @param priorBlock The old {@link BlockState} that is to be frozen.
         * @param frozenBlock The original result {@link BlockState} from the freezing.
         * @param sourceStack The source {@link ItemStack} performing the freezing.
         */
        public FreezeFromItem(LevelAccessor level, BlockPos pos, BlockState priorBlock, BlockState frozenBlock, ItemStack sourceStack) {
            super(level, pos, priorBlock, frozenBlock);
            this.sourceStack = sourceStack;
        }

        /**
         * @return The source {@link ItemStack} performing the freezing.
         */
        public ItemStack getSourceStack() {
            return this.sourceStack;
        }

        @Override
        public void sendEvent() {
            FREEZE_ITEM_BLOCK.invoker().onFreezeItem(this);
        }
    }

    @FunctionalInterface
    public interface FreezeCallback {
        void onFreeze(FreezeEvent event);
    }

    @FunctionalInterface
    public interface FreezeBlockCallback {
        void onFreezeBlock(FreezeFromBlock event);
    }

    @FunctionalInterface
    public interface FreezeItemCallback {
        void onFreezeItem(FreezeFromItem event);
    }
}
