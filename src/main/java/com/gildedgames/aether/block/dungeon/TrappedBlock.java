package com.gildedgames.aether.block.dungeon;

import java.util.function.Supplier;

import com.gildedgames.aether.client.AetherSoundEvents;

import com.gildedgames.aether.event.dispatch.AetherEventDispatch;
import com.gildedgames.aether.mixin.mixins.common.accessor.EntityAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class TrappedBlock extends Block {
	private final Supplier<EntityType<?>> spawnableEntityTypeSupplier;
	private final Supplier<? extends BlockState> defaultStateSupplier;
	
	public TrappedBlock(Supplier<EntityType<?>> spawnableEntityTypeSupplier, Supplier<? extends BlockState> defaultStateSupplier, Properties properties) {
		super(properties);
		this.spawnableEntityTypeSupplier = spawnableEntityTypeSupplier;
		this.defaultStateSupplier = defaultStateSupplier;
	}

	/**
	 * If a player steps on the block, it converts to the state given by {@link TrappedBlock#defaultStateSupplier} and spawns the entity given by {@link TrappedBlock#spawnableEntityTypeSupplier}.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param state The {@link BlockState} of the block.
	 * @param entity The {@link Entity} stepping on the block.
	 */
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof Player player && AetherEventDispatch.onTriggerTrap(player, level, pos, state)) {
			level.setBlockAndUpdate(pos, this.defaultStateSupplier.get());
			if (level instanceof ServerLevel serverLevel) {
				Entity spawnableEntity = this.spawnableEntityTypeSupplier.get().create(level);
				if (spawnableEntity != null) {
					spawnableEntity.absMoveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, ((EntityAccessor) entity).getRandom().nextFloat() * 360.0F, 0.0F);
					if (spawnableEntity instanceof Mob spawnableMob) {
						spawnableMob.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(spawnableEntity.blockPosition()), MobSpawnType.TRIGGERED, null, null);
					}
					level.addFreshEntity(spawnableEntity);
				}
			}
			level.playSound(null, pos, AetherSoundEvents.BLOCK_DUNGEON_TRAP_TRIGGER.get(), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
		}
	}
}
