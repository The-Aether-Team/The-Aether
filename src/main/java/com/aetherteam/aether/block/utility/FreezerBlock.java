package com.aetherteam.aether.block.utility;

import com.aetherteam.aether.blockentity.AbstractAetherFurnaceBlockEntity;
import com.aetherteam.aether.blockentity.AetherBlockEntityTypes;
import com.aetherteam.aether.blockentity.FreezerBlockEntity;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FreezerBlock extends AbstractFurnaceBlock {
	public FreezerBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FreezerBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		return level.isClientSide() ? null : createTickerHelper(blockEntityType, AetherBlockEntityTypes.FREEZER.get(), AbstractAetherFurnaceBlockEntity::serverTick);
	}
	
	@Override
	protected void openContainer(Level level, BlockPos pos, Player player) {
		if (!level.isClientSide()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof FreezerBlockEntity freezerBlockEntity) {
				player.openMenu(freezerBlockEntity);
			}
		}
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(LIT)) {
			double x = pos.getX() + 0.5;
			double y = pos.getY() + 1.0 + (random.nextFloat() * 6.0) / 16.0;
			double z = pos.getZ() + 0.5;
			level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0);
			for (int i = 0; i < 10; ++i) {
				level.addParticle(AetherParticleTypes.FROZEN.get(), x, y, z, 0.0, 0.0, 0.0);
			}
			level.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, AetherSoundEvents.BLOCK_FREEZER_CRACKLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
	}
}
