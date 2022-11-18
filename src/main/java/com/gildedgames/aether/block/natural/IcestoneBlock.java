package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.blockentity.IcestoneBlockEntity;
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

import javax.annotation.Nonnull;

public class IcestoneBlock extends BaseEntityBlock {
	public IcestoneBlock(Properties properties) {
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

	@Nonnull
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState state) {
		return RenderShape.MODEL;
	}
}
