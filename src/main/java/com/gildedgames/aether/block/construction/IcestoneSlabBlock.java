package com.gildedgames.aether.block.construction;

import com.gildedgames.aether.blockentity.IcestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class IcestoneSlabBlock extends SlabBlock implements EntityBlock {
    public IcestoneSlabBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new IcestoneBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(@Nonnull ServerLevel level, @Nonnull T blockEntity) {
        return blockEntity instanceof IcestoneBlockEntity icestoneBlockEntity ? icestoneBlockEntity.getListener() : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    public boolean triggerEvent(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockentity = level.getBlockEntity(pos);
        return blockentity != null && blockentity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }
}
