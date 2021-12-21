package com.gildedgames.aether.common.event.events;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

public class FreezeEvent extends Event
{
    private final LevelAccessor world;
    private final BlockPos pos;
    private final FluidState previousFluid;
    @Nonnull
    private BlockState frozenBlock;

    public FreezeEvent(LevelAccessor world, BlockPos pos, FluidState fluidState, @Nonnull BlockState blockState) {
        this.world = world;
        this.pos = pos;
        this.previousFluid = fluidState;
        this.frozenBlock = blockState;
    }

    public LevelAccessor getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public FluidState getPreviousFluid() {
        return this.previousFluid;
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

        public FreezeFromBlock(LevelAccessor world, BlockPos pos, FluidState fluidState, BlockState blockState, BlockState sourceBlock) {
            super(world, pos, fluidState, blockState);
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

        public FreezeFromItem(LevelAccessor world, BlockPos pos, FluidState fluidState, BlockState blockState, ItemStack sourceStack) {
            super(world, pos, fluidState, blockState);
            this.sourceStack = sourceStack;
        }

        public ItemStack getSourceStack() {
            return this.sourceStack;
        }
    }
}
