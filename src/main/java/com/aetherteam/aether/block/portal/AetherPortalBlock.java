package com.aetherteam.aether.block.portal;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.mixin.mixins.common.accessor.EntityAccessor;
import com.aetherteam.aether.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;

public class AetherPortalBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

	public AetherPortalBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(AXIS, Direction.Axis.X));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	/**
	 * Based on {@link Entity#handleInsidePortal(BlockPos)} and {@link Entity#handleNetherPortal()}.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		EntityAccessor entityAccessor = (EntityAccessor) entity;
		if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
			if (entity.isOnPortalCooldown()) {
				entity.setPortalCooldown();
			} else {
				if (!entity.getLevel().isClientSide() && !pos.equals(entityAccessor.aether$getPortalEntrancePos())) {
					entityAccessor.aether$setPortalEntrancePos(pos.immutable());
				}
				LazyOptional<AetherPlayer> aetherPlayer = entity.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
				if (!aetherPlayer.isPresent()) {
					this.handleTeleportation(entity);
				} else {
					aetherPlayer.ifPresent(handler -> {
						handler.setInPortal(true);
						int waitTime = handler.getPortalTimer();
						if (waitTime >= entity.getPortalWaitTime()) {
							this.handleTeleportation(entity);
							handler.setPortalTimer(0);
						}
					});
				}
			}
		}
	}

	/**
	 * Based on {@link Entity#handleNetherPortal()}.
	 */
	private void handleTeleportation(Entity entity) {
		MinecraftServer minecraftserver = entity.getLevel().getServer();
		ResourceKey<Level> destinationKey = entity.getLevel().dimension() == LevelUtil.destinationDimension() ? LevelUtil.returnDimension() : LevelUtil.destinationDimension();
		if (minecraftserver != null) {
			ServerLevel destinationLevel = minecraftserver.getLevel(destinationKey);
			if (destinationLevel != null && !entity.isPassenger()) {
				entity.getLevel().getProfiler().push("aether_portal");
				entity.setPortalCooldown();
				entity.changeDimension(destinationLevel, new AetherPortalForcer(destinationLevel, true));
				entity.getLevel().getProfiler().pop();
			}
		}
	}

	/**
	 * Based on {@link net.minecraft.world.level.block.NetherPortalBlock#animateTick(BlockState, Level, BlockPos, RandomSource)}.
	 */
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(100) == 0) {
			level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}
		for (int i = 0; i < 4; ++i) {
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble();
			double z = pos.getZ() + random.nextDouble();
			double xSpeed = (random.nextFloat() - 0.5) * 0.5;
			double ySpeed = (random.nextFloat() - 0.5) * 0.5;
			double zSpeed = (random.nextFloat() - 0.5) * 0.5;
			int j = random.nextInt(2) * 2 - 1;
			if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
				x = pos.getX() + 0.5 + 0.25 * j;
				xSpeed = random.nextFloat() * 2.0F * j;
			} else {
				z = pos.getZ() + 0.5 + 0.25 * j;
				zSpeed = random.nextFloat() * 2.0F * j;
			}
			level.addParticle(AetherParticleTypes.AETHER_PORTAL.get(), x, y, z, xSpeed, ySpeed, zSpeed);
		}
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return switch (rotation) {
			case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
				case Z -> state.setValue(AXIS, Direction.Axis.X);
				case X -> state.setValue(AXIS, Direction.Axis.Z);
				default -> state;
			};
			default -> state;
		};
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.getValue(AXIS) == Direction.Axis.Z) {
			return Z_AXIS_AABB;
		} else {
			return X_AXIS_AABB;
		}
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		Direction.Axis directionAxis = direction.getAxis();
		Direction.Axis blockAxis = state.getValue(AXIS);
		boolean flag = blockAxis != directionAxis && directionAxis.isHorizontal();
		return !flag && !facingState.is(this) && !(new AetherPortalShape(level, currentPos, blockAxis).isComplete()) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, level, currentPos, facingPos);
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
}
