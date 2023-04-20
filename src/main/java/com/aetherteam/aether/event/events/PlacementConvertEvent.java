package com.aetherteam.aether.event.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

@Cancelable
public class PlacementConvertEvent extends Event {
    private final LevelAccessor world;
    private final BlockPos pos;
    @Nonnull
    private final BlockState oldBlockState;
    @Nonnull
    private BlockState newBlockState;

    public PlacementConvertEvent(LevelAccessor world, BlockPos pos, @Nonnull BlockState oldBlockState, @Nonnull BlockState newBlockState) {
        this.world = world;
        this.pos = pos;
        this.oldBlockState = oldBlockState;
        this.newBlockState = newBlockState;
    }

    public LevelAccessor getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    @Nonnull
    public BlockState getOldBlockState() {
        return this.oldBlockState;
    }

    @Nonnull
    public BlockState getNewBlockState() {
        return this.newBlockState;
    }

    public void setNewBlockState(@Nonnull BlockState newBlockState) {
        this.newBlockState = newBlockState;
    }
}
