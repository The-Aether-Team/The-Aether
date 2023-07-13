package com.aetherteam.aether.block.dungeon;

import java.util.function.Supplier;

import com.aetherteam.aether.client.AetherSoundEvents;

import com.aetherteam.aether.event.dispatch.AetherEventDispatch;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class TrappedBlock extends Block {
	private final Supplier<EntityType<?>> spawnableEntityTypeSupplier;
	private final Supplier<? extends BlockState> defaultStateSupplier;
	
	public TrappedBlock(Supplier<EntityType<?>> spawnableEntityTypeSupplier, Supplier<? extends BlockState> defaultStateSupplier, Properties properties) {
		super(properties);
		this.spawnableEntityTypeSupplier = spawnableEntityTypeSupplier;
		this.defaultStateSupplier = defaultStateSupplier;
	}

	public BlockState getFacadeBlock() {
		return this.defaultStateSupplier.get();
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
				float yRot = player.getYRot() * Mth.DEG_TO_RAD;
				Vec3 targetVec = new Vec3(pos.getX() + 0.5 - Mth.sin(yRot) * 3, pos.getY() + 1, pos.getZ() + 0.5 + Mth.cos(yRot) * 3);
				ClipContext context = new ClipContext(player.position(), targetVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);
				BlockHitResult hitResult = serverLevel.clip(context);
				BlockPos spawnPos = hitResult.getBlockPos();
				if (hitResult.getType() == HitResult.Type.BLOCK) {
					spawnPos = spawnPos.relative(hitResult.getDirection());
				}
				this.spawnableEntityTypeSupplier.get().spawn(serverLevel, spawnPos, MobSpawnType.TRIGGERED);
				serverLevel.playSound(null, pos, AetherSoundEvents.BLOCK_DUNGEON_TRAP_TRIGGER.get(), SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
			}
		}
	}
}
