package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.fabric.events.CancellableCallbackImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * FreezeEvent is fired when an event for a freezing recipe occurs.<br>
 * If a method utilizes this {@link Event} as its parameter, the method will receive every child event of this class.<br>
 * <br>
 */
public abstract class FreezeEvent extends CancellableCallbackImpl {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState priorBlock;
    private BlockState frozenBlock;

    /**
     * @param level       The {@link LevelAccessor} that the freezing is occurring in.
     * @param pos         The {@link BlockPos} that the freezing is occurring at.
     * @param priorBlock  The old {@link BlockState} that is to be frozen.
     * @param frozenBlock The original result {@link BlockState} from the freezing.
     */
    protected FreezeEvent(LevelAccessor level, BlockPos pos, BlockState priorBlock, BlockState frozenBlock) {
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
     *
     * @param frozenBlock The new {@link BlockState}.
     */
    public void setFrozenBlock(BlockState frozenBlock) {
        this.frozenBlock = frozenBlock;
    }

    /**
     * FreezeEvent.FreezeFromBlock is fired for freezing recipes triggered by blocks.
     * <br>
     * This event is {@link CancellableCallbackImpl}.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromBlock extends FreezeEvent {
        private final BlockPos sourcePos;
        private final BlockState sourceBlock;

        /**
         * @param level       The {@link LevelAccessor} that the freezing is occurring in.
         * @param pos         The {@link BlockPos} that the freezing is occurring at.
         * @param sourcePos   The {@link BlockPos} of the source that is causing the freezing.
         * @param priorBlock  The old {@link BlockState} that is to be frozen.
         * @param frozenBlock The original result {@link BlockState} from the freezing.
         * @param sourceBlock The source {@link BlockState} performing the freezing.
         */
        protected FreezeFromBlock(LevelAccessor level, BlockPos pos, BlockPos sourcePos, BlockState priorBlock, BlockState frozenBlock, BlockState sourceBlock) {
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
    }

    /**
     * FreezeEvent.FreezeFromItem is fired for freezing recipes triggered by items.
     * <br>
     * This event is {@link CancellableCallbackImpl}.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromItem extends FreezeEvent {
        private final ItemStack sourceStack;

        /**
         * @param level       The {@link LevelAccessor} that the freezing is occurring in.
         * @param pos         The {@link BlockPos} that the freezing is occurring at.
         * @param priorBlock  The old {@link BlockState} that is to be frozen.
         * @param frozenBlock The original result {@link BlockState} from the freezing.
         * @param sourceStack The source {@link ItemStack} performing the freezing.
         */
        protected FreezeFromItem(LevelAccessor level, BlockPos pos, BlockState priorBlock, BlockState frozenBlock, ItemStack sourceStack) {
            super(level, pos, priorBlock, frozenBlock);
            this.sourceStack = sourceStack;
        }

        /**
         * @return The source {@link ItemStack} performing the freezing.
         */
        public ItemStack getSourceStack() {
            return this.sourceStack;
        }
    }

    //--

    public static final Event<ItemFreezeCallback> ITEM_FREEZE = EventFactory.createArrayBacked(ItemFreezeCallback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.onFreeze(event);
    });

    public interface ItemFreezeCallback {
        void onFreeze(FreezeFromItem event);
    }

    public static final Event<BlockFreezeCallback> BLOCK_FREEZE = EventFactory.createArrayBacked(BlockFreezeCallback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.onFreeze(event);
    });

    public interface BlockFreezeCallback {
        void onFreeze(FreezeFromBlock event);
    }
}
