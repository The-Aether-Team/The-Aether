package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.fml.LogicalSide;

/**
 * FreezeEvent is fired when an event for a freezing recipe occurs.<br>
 * If a method utilizes this {@link Event} as its parameter, the method will receive every child event of this class.<br>
 * <br>
 * All children of this event are fired on the {@link net.neoforged.neoforge.common.NeoForge#EVENT_BUS}.
 */
public class FreezeEvent extends Event implements ICancellableEvent {
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

    /**
     * FreezeEvent.FreezeFromBlock is fired for freezing recipes triggered by blocks.
     * <br>
     * This event is {@link ICancellableEvent}.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromBlock extends FreezeEvent implements ICancellableEvent {
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
    }

    /**
     * FreezeEvent.FreezeFromItem is fired for freezing recipes triggered by items.
     * <br>
     * This event is {@link ICancellableEvent}.<br>
     * If the event is not canceled, the block will be frozen.
     * <br>
     * This event is only fired on the {@link LogicalSide#SERVER} side.<br>
     * <br>
     * If this event is canceled, the block will not be frozen.
     */
    public static class FreezeFromItem extends FreezeEvent implements ICancellableEvent {
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
    }
}
