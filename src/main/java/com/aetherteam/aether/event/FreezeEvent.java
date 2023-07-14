package com.aetherteam.aether.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

public class FreezeEvent extends Event
{
    private final LevelAccessor world;
    private final BlockPos pos;
    private final BlockState priorBlock;
    @Nonnull
    private BlockState frozenBlock;

    public FreezeEvent(LevelAccessor world, BlockPos pos, BlockState priorBlock, @Nonnull BlockState frozenBlock) {
        this.world = world;
        this.pos = pos;
        this.priorBlock = priorBlock;
        this.frozenBlock = frozenBlock;
    }

    public LevelAccessor getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public BlockState getPriorBlock() {
        return this.priorBlock;
    }

    @Nonnull
    public BlockState getFrozenBlock() {
        return this.frozenBlock;
    }

    public void setFrozenBlock(@Nonnull BlockState frozenBlock) {
        this.frozenBlock = frozenBlock;
    }

    @Cancelable
    public static class FreezeFromBlock extends FreezeEvent
    {
        private final BlockState sourceBlock;

        public FreezeFromBlock(LevelAccessor world, BlockPos pos, BlockState priorBlock, BlockState frozenBlock, BlockState sourceBlock) {
            super(world, pos, priorBlock, frozenBlock);
            this.sourceBlock = sourceBlock;
        }

        public BlockState getSourceBlock() {
            return this.sourceBlock;
        }
    }

    @Cancelable
    public static class FreezeFromItem extends FreezeEvent
    {
        private final ItemStack sourceStack;

        public FreezeFromItem(LevelAccessor world, BlockPos pos, BlockState priorBlock, BlockState frozenBlock, ItemStack sourceStack) {
            super(world, pos, priorBlock, frozenBlock);
            this.sourceStack = sourceStack;
        }

        public ItemStack getSourceStack() {
            return this.sourceStack;
        }
    }
}
