package com.aether.world;

import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aether.block.AetherBlocks;
import com.aether.block.AetherPortalBlock;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.util.ITeleporter;

public class AetherTeleporter implements ITeleporter {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	protected final Map<ColumnPos, PortalPosition> destinationCoordinateCache = Maps.newHashMapWithExpectedSize(4096);
	private final Object2LongMap<ColumnPos> columnMap = new Object2LongOpenHashMap<>();

	public boolean placeInPortal(ServerWorld world, Entity entity, float yaw) {
		Vec3d vec3d = entity.getLastPortalVec();
		Direction direction = entity.getTeleportDirection();
		BlockPattern.PortalInfo pattern = this.placeInExistingPortal(world, new BlockPos(entity.getPosX(), entity.getPosY(), entity.getPosZ()), entity.getMotion(), direction, vec3d.x, vec3d.y, entity instanceof PlayerEntity);
		if (pattern == null) {
			return false;
		}
		else {
			Vec3d position = pattern.pos;
			Vec3d motion = pattern.motion;
			entity.setMotion(motion);
			entity.rotationYaw = yaw + pattern.rotation;
			entity.moveForced(position.x, position.y, position.z);
			return true;
		}
	}
	
	@Nullable
	public BlockPattern.PortalInfo placeInExistingPortal(ServerWorld world, BlockPos pos, Vec3d motion, Direction direction, double x, double y, boolean isPlayer) {
		boolean isFrame = true;
		BlockPos blockpos = null;
		ColumnPos columnpos = new ColumnPos(pos);
		if (!isPlayer && this.columnMap.containsKey(columnpos)) {
			return null;
		}
		else {
			PortalPosition position = this.destinationCoordinateCache.get(columnpos);
			if (position != null) {
				blockpos = position.pos;
				position.lastUpdateTime = world.getGameTime();
				isFrame = false;
			}
			else {
				double d0 = Double.MAX_VALUE;

				for (int eX = -128; eX <= 128; ++eX) {
					BlockPos blockpos2;
					for (int eZ = -128; eZ <= 128; ++eZ) {
						for (BlockPos blockpos1 = pos.add(eX, world.getActualHeight() - 1 - pos.getY(), eZ); blockpos1
							.getY() >= 0; blockpos1 = blockpos2) {
							blockpos2 = blockpos1.down();
							if (world.getBlockState(blockpos1).getBlock() == AetherBlocks.AETHER_PORTAL) {
								for (blockpos2 = blockpos1.down(); world.getBlockState(blockpos2).getBlock() == AetherBlocks.AETHER_PORTAL; blockpos2 = blockpos2.down()) {
									blockpos1 = blockpos2;
								}

								double distance = blockpos1.distanceSq(pos);
								if (d0 < 0.0 || distance < d0) {
									d0 = distance;
									blockpos = blockpos1;
								}
							}
						}
					}
				}
			}

			if (blockpos == null) {
				long factor = world.getGameTime() + 300L;
				this.columnMap.put(columnpos, factor);
				return null;
			}
			else {
				if (isFrame) {
					this.destinationCoordinateCache.put(columnpos, new PortalPosition(blockpos, world.getGameTime()));
					world.getChunkProvider().registerTicket(TicketType.PORTAL, new ChunkPos(blockpos), 3,
						new BlockPos(columnpos.x, blockpos.getY(), columnpos.z));
				}

				BlockPattern.PatternHelper helper = AetherPortalBlock.createPatternHelper(world, blockpos);
				return helper.getPortalInfo(direction, blockpos, y, motion, x);
			}
		}
	}

	public boolean makePortal(ServerWorld world, Entity entityIn) {
		Random random = new Random(world.getSeed());
		double d0 = -1.0;
		int j = MathHelper.floor(entityIn.getPosX());
		int k = MathHelper.floor(entityIn.getPosY());
		int l = MathHelper.floor(entityIn.getPosZ());
		int i1 = j;
		int j1 = k;
		int k1 = l;
		int l1 = 0;
		int i2 = random.nextInt(4);
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (int j2 = j - 16; j2 <= j + 16; ++j2) {
			double d1 = j2 + 0.5 - entityIn.getPosX();

			for (int l2 = l - 16; l2 <= l + 16; ++l2) {
				double d2 = l2 + 0.5 - entityIn.getPosZ();

				label276:
				for (int j3 = world.getActualHeight() - 1; j3 >= 0; --j3) {
					if (world.isAirBlock(blockpos$mutable.setPos(j2, j3, l2))) {
						while (j3 > 0 && world.isAirBlock(blockpos$mutable.setPos(j2, j3 - 1, l2))) {
							--j3;
						}

						for (int k3 = i2; k3 < i2 + 4; ++k3) {
							int l3 = k3 % 2;
							int i4 = 1 - l3;
							if (k3 % 4 >= 2) {
								l3 = -l3;
								i4 = -i4;
							}

							for (int j4 = 0; j4 < 3; ++j4) {
								for (int k4 = 0; k4 < 4; ++k4) {
									for (int l4 = -1; l4 < 4; ++l4) {
										int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
										int j5 = j3 + l4;
										int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
										blockpos$mutable.setPos(i5, j5, k5);
										if (l4 < 0 && !world.getBlockState(blockpos$mutable).getMaterial().isSolid()
											|| l4 >= 0 && !world.isAirBlock(blockpos$mutable)) {
											continue label276;
										}
									}
								}
							}

							double d5 = j3 + 0.5 - entityIn.getPosY();
							double d7 = d1 * d1 + d5 * d5 + d2 * d2;
							if (d0 < 0.0 || d7 < d0) {
								d0 = d7;
								i1 = j2;
								j1 = j3;
								k1 = l2;
								l1 = k3 % 4;
							}
						}
					}
				}
			}
		}

		if (d0 < 0.0) {
			for (int l5 = j - 16; l5 <= j + 16; ++l5) {
				double d3 = l5 + 0.5 - entityIn.getPosX();

				for (int j6 = l - 16; j6 <= l + 16; ++j6) {
					double d4 = j6 + 0.5 - entityIn.getPosZ();

					label214:
					for (int i7 = world.getActualHeight() - 1; i7 >= 0; --i7) {
						if (world.isAirBlock(blockpos$mutable.setPos(l5, i7, j6))) {
							while (i7 > 0 && world.isAirBlock(blockpos$mutable.setPos(l5, i7 - 1, j6))) {
								--i7;
							}

							for (int l7 = i2; l7 < i2 + 2; ++l7) {
								int l8 = l7 % 2;
								int k9 = 1 - l8;

								for (int i10 = 0; i10 < 4; ++i10) {
									for (int k10 = -1; k10 < 4; ++k10) {
										int i11 = l5 + (i10 - 1) * l8;
										int j11 = i7 + k10;
										int k11 = j6 + (i10 - 1) * k9;
										blockpos$mutable.setPos(i11, j11, k11);
										if (k10 < 0 && !world.getBlockState(blockpos$mutable).getMaterial().isSolid()
											|| k10 >= 0 && !world.isAirBlock(blockpos$mutable)) {
											continue label214;
										}
									}
								}

								double d6 = i7 + 0.5 - entityIn.getPosY();
								double d8 = d3 * d3 + d6 * d6 + d4 * d4;
								if (d0 < 0.0 || d8 < d0) {
									d0 = d8;
									i1 = l5;
									j1 = i7;
									k1 = j6;
									l1 = l7 % 2;
								}
							}
						}
					}
				}
			}
		}

		int i6 = i1;
		int k2 = j1;
		int k6 = k1;
		int l6 = l1 % 2;
		int i3 = 1 - l6;
		if (l1 % 4 >= 2) {
			l6 = -l6;
			i3 = -i3;
		}

		if (d0 < 0.0) {
			j1 = MathHelper.clamp(j1, 70, world.getActualHeight() - 10);
			k2 = j1;

			for (int j7 = -1; j7 <= 1; ++j7) {
				for (int i8 = 1; i8 < 3; ++i8) {
					for (int i9 = -1; i9 < 3; ++i9) {
						int l9 = i6 + (i8 - 1) * l6 + j7 * i3;
						int j10 = k2 + i9;
						int l10 = k6 + (i8 - 1) * i3 - j7 * l6;
						boolean flag = i9 < 0;
						blockpos$mutable.setPos(l9, j10, l10);
						world.setBlockState(blockpos$mutable,
							flag? Blocks.GLOWSTONE.getDefaultState() : Blocks.AIR.getDefaultState());
					}
				}
			}
		}

		for (int k7 = -1; k7 < 3; ++k7) {
			for (int j8 = -1; j8 < 4; ++j8) {
				if (k7 == -1 || k7 == 2 || j8 == -1 || j8 == 3) {
					blockpos$mutable.setPos(i6 + k7 * l6, k2 + j8, k6 + k7 * i3);
					world.setBlockState(blockpos$mutable, Blocks.GLOWSTONE.getDefaultState(), 3);
				}
			}
		}

		BlockState blockstate = AetherBlocks.AETHER_PORTAL.getDefaultState().with(AetherPortalBlock.AXIS, l6 == 0? Direction.Axis.Z : Direction.Axis.X);

		for (int k8 = 0; k8 < 2; ++k8) {
			for (int j9 = 0; j9 < 3; ++j9) {
				blockpos$mutable.setPos(i6 + k8 * l6, k2 + j9, k6 + k8 * i3);
				world.setBlockState(blockpos$mutable, blockstate, 18);
			}
		}

		return true;
	}
	
	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		if (entity instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)entity;
			double d0 = player.getPosX();
			double d1 = player.getPosY();
			double d2 = player.getPosZ();
			float f = player.rotationPitch;
			float f1 = player.rotationYaw;
			float f2 = f1;
			currentWorld.getProfiler().startSection("moving");
			double moveFactor = currentWorld.getDimension().getMovementFactor() / destWorld.getDimension().getMovementFactor();
			d0 *= moveFactor;
			d2 *= moveFactor;

			player.setLocationAndAngles(d0, d1, d2, f1, f);
			currentWorld.getProfiler().endSection();
			currentWorld.getProfiler().startSection("placing");
			double d7 = Math.min(-2.9999872E7, destWorld.getWorldBorder().minX() + 16.0);
			double d4 = Math.min(-2.9999872E7, destWorld.getWorldBorder().minZ() + 16.0);
			double d5 = Math.min(2.9999872E7, destWorld.getWorldBorder().maxX() - 16.0);
			double d6 = Math.min(2.9999872E7, destWorld.getWorldBorder().maxZ() - 16.0);
			d0 = MathHelper.clamp(d0, d7, d5);
			d2 = MathHelper.clamp(d2, d4, d6);
			player.setLocationAndAngles(d0, d1, d2, f1, f);
			if (!this.placeInPortal(destWorld, player, f2)) {
				this.makePortal(destWorld, player);
				this.placeInPortal(destWorld, player, f2);
			}

			currentWorld.getProfiler().endSection();
			player.setWorld(destWorld);
			destWorld.addDuringPortalTeleport(player);
			player.func_213846_b(currentWorld);
			player.connection.setPlayerLocation(player.getPosX(), player.getPosY(), player.getPosZ(), f1, f);
			return player;
		}
		else {
			Vec3d vec3d = entity.getMotion();
			float f = 0.0F;
			BlockPos blockpos;
	
			double movementFactor = currentWorld.getDimension().getMovementFactor()
				/ destWorld.getDimension().getMovementFactor();
			double d0 = entity.getPosX() * movementFactor;
			double d1 = entity.getPosZ() * movementFactor;
	
			double d3 = Math.min(-2.9999872E7, destWorld.getWorldBorder().minX() + 16.0);
			double d4 = Math.min(-2.9999872E7, destWorld.getWorldBorder().minZ() + 16.0);
			double d5 = Math.min(2.9999872E7, destWorld.getWorldBorder().maxX() - 16.0);
			double d6 = Math.min(2.9999872E7, destWorld.getWorldBorder().maxZ() - 16.0);
			d0 = MathHelper.clamp(d0, d3, d5);
			d1 = MathHelper.clamp(d1, d4, d6);
			Vec3d vec3d1 = entity.getLastPortalVec();
			blockpos = new BlockPos(d0, entity.getPosY(), d1);
	
			BlockPattern.PortalInfo blockpattern$portalinfo = this.placeInExistingPortal(destWorld, blockpos, vec3d,
				entity.getTeleportDirection(), vec3d1.x, vec3d1.y, entity instanceof PlayerEntity);
			if (blockpattern$portalinfo == null) {
				return null;
			}
	
			blockpos = new BlockPos(blockpattern$portalinfo.pos);
			vec3d = blockpattern$portalinfo.motion;
			f = blockpattern$portalinfo.rotation;
	
			entity.world.getProfiler().endStartSection("reloading");
			Entity newEntity = entity.getType().create(destWorld);
			if (newEntity != null) {
				newEntity.copyDataFromOld(entity);
				newEntity.moveToBlockPosAndAngles(blockpos, newEntity.rotationYaw + f, newEntity.rotationPitch);
				newEntity.setMotion(vec3d);
				destWorld.addFromAnotherDimension(newEntity);
			}
			return newEntity;
		}
	}
	
	static class PortalPosition {
		public final BlockPos pos;
		public long lastUpdateTime;
		
		public PortalPosition(BlockPos pos, long time) {
			this.pos = pos;
			this.lastUpdateTime = time;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(pos, lastUpdateTime);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			else if (obj instanceof PortalPosition) {
				PortalPosition other = (PortalPosition)obj;
				return this.pos.equals(other.pos) && this.lastUpdateTime == other.lastUpdateTime; 
			}
			else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return this.getClass().getSimpleName() + "[pos=" + this.pos + ", lastUpdateTime=" + this.lastUpdateTime + "]";
		}
		
	}

}
