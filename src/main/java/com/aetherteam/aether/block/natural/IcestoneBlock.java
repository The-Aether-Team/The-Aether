package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.IcestoneBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class IcestoneBlock extends BaseEntityBlock {

    public static final MapCodec<IcestoneBlock> CODEC = simpleCodec(IcestoneBlock::new);

    public IcestoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IcestoneBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel level, T blockEntity) {
        return blockEntity instanceof IcestoneBlockEntity icestoneBlockEntity ? icestoneBlockEntity.getListener() : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, AetherBlockEntityTypes.ICESTONE.get(), IcestoneBlockEntity::serverTick);
    }

    /**
     * Overrides {@link BaseEntityBlock#getRenderShape(BlockState)} so the render shape isn't {@link RenderShape#INVISIBLE}.<br><br>
     * Warning for "deprecation" is suppressed because the method is fine to override.
     *
     * @param state The {@link BlockState} of the block.
     * @return The {@link RenderShape} of the block.
     */
    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
