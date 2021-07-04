package com.gildedgames.aether.common.event.events;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class FreezeEvent extends Event
{
    private final IWorld world;
    private final BlockPos pos;
    private final FluidState previousFluid;
    private BlockState frozenBlock;

    public FreezeEvent(IWorld world, BlockPos pos, FluidState fluidState, BlockState blockState) {
        this.world = world;
        this.pos = pos;
        this.previousFluid = fluidState;
        this.frozenBlock = blockState;
    }

    public IWorld getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public FluidState getPreviousFluid() {
        return this.previousFluid;
    }

    public BlockState getFrozenBlock() {
        return this.frozenBlock;
    }

    public void setFrozenBlock(BlockState frozenBlock) {
        this.frozenBlock = frozenBlock;
    }

    @Cancelable
    public static class FreezeFromBlock extends FreezeEvent
    {
        private final BlockState sourceBlock;

        public FreezeFromBlock(IWorld world, BlockPos pos, FluidState fluidState, BlockState blockState, BlockState sourceBlock) {
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

        public FreezeFromItem(IWorld world, BlockPos pos, FluidState fluidState, BlockState blockState, ItemStack sourceStack) {
            super(world, pos, fluidState, blockState);
            this.sourceStack = sourceStack;
        }

        public ItemStack getSourceStack() {
            return this.sourceStack;
        }
    }
}
