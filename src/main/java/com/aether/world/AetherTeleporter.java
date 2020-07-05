package com.aether.world;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

import com.aether.block.AetherBlocks;
import com.aether.block.AetherPortalBlock;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

public class AetherTeleporter extends Teleporter {
	private static final Logger LOGGER = LogManager.getLogger();

	public AetherTeleporter(ServerWorld worldIn) {
		super(worldIn);
	}

	@Override
	@Nullable
	public BlockPattern.PortalInfo func_222272_a(BlockPos p_222272_1_, Vec3d p_222272_2_, Direction p_222272_3_, double p_222272_4_, double p_222272_6_, boolean p_222272_8_) {
		boolean flag = true;
		BlockPos blockpos = null;
		ColumnPos columnpos = new ColumnPos(p_222272_1_);
		if (!p_222272_8_ && this.field_222275_f.containsKey(columnpos)) {
			return null;
		}
		else {
			Teleporter.PortalPosition teleporter$portalposition = this.destinationCoordinateCache.get(columnpos);
			if (teleporter$portalposition != null) {
				blockpos = teleporter$portalposition.field_222267_a;
				teleporter$portalposition.lastUpdateTime = this.world.getGameTime();
				flag = false;
			}
			else {
				double d0 = Double.MAX_VALUE;

				for (int j = -128; j <= 128; ++j) {
					BlockPos blockpos2;
					for (int k = -128; k <= 128; ++k) {
						for (BlockPos blockpos1 = p_222272_1_.add(j,
							this.world.getActualHeight() - 1 - p_222272_1_.getY(), k); blockpos1
								.getY() >= 0; blockpos1 = blockpos2) {
							blockpos2 = blockpos1.down();
							if (this.world.getBlockState(blockpos1).getBlock() == AetherBlocks.AETHER_PORTAL) {
								for (blockpos2 = blockpos1.down(); this.world.getBlockState(blockpos2)
									.getBlock() == AetherBlocks.AETHER_PORTAL; blockpos2 = blockpos2.down()) {
									blockpos1 = blockpos2;
								}

								double d1 = blockpos1.distanceSq(p_222272_1_);
								if (d0 < 0.0D || d1 < d0) {
									d0 = d1;
									blockpos = blockpos1;
								}
							}
						}
					}
				}
			}

			if (blockpos == null) {
				long l = this.world.getGameTime() + 300L;
				this.field_222275_f.put(columnpos, l);
				return null;
			}
			else {
				if (flag) {
					this.destinationCoordinateCache.put(columnpos, new Teleporter.PortalPosition(blockpos, this.world.getGameTime()));
					Logger logger = LOGGER;
					Supplier<?>[] asupplier = new Supplier[2];
					Dimension dimension = this.world.getDimension();
					asupplier[0] = dimension::getType;
					asupplier[1] = () -> columnpos;
					logger.debug("Adding aether portal ticket for {}:{}", asupplier);
					this.world.getChunkProvider().func_217228_a(TicketType.PORTAL, new ChunkPos(blockpos), 3, columnpos);
				}

				BlockPattern.PatternHelper blockpattern$patternhelper = AetherBlocks.AETHER_PORTAL.createPatternHelper(this.world, blockpos);
				return blockpattern$patternhelper.func_222504_a(p_222272_3_, blockpos, p_222272_6_, p_222272_2_, p_222272_4_);
			}
		}
	}
	
	@Override
	public boolean makePortal(Entity entityIn) {
		double d0 = -1.0;
		int j = MathHelper.floor(entityIn.posX);
		int k = MathHelper.floor(entityIn.posY);
		int l = MathHelper.floor(entityIn.posZ);
		int i1 = j;
		int j1 = k;
		int k1 = l;
		int l1 = 0;
		int i2 = this.random.nextInt(4);
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int j2 = j - 16; j2 <= j + 16; ++j2) {
			double d1 = j2 + 0.5 - entityIn.posX;

			for (int l2 = l - 16; l2 <= l + 16; ++l2) {
				double d2 = l2 + 0.5 - entityIn.posZ;

				label276:
				for (int j3 = this.world.getActualHeight() - 1; j3 >= 0; --j3) {
					if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3, l2))) {
						while (j3 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(j2, j3 - 1, l2))) {
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
										blockpos$mutableblockpos.setPos(i5, j5, k5);
										if (l4 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid()
											|| l4 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
											continue label276;
										}
									}
								}
							}

							double d5 = j3 + 0.5 - entityIn.posY;
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
				double d3 = l5 + 0.5 - entityIn.posX;

				for (int j6 = l - 16; j6 <= l + 16; ++j6) {
					double d4 = j6 + 0.5 - entityIn.posZ;

					label214:
					for (int i7 = this.world.getActualHeight() - 1; i7 >= 0; --i7) {
						if (this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7, j6))) {
							while (i7 > 0 && this.world.isAirBlock(blockpos$mutableblockpos.setPos(l5, i7 - 1, j6))) {
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
										blockpos$mutableblockpos.setPos(i11, j11, k11);
										if (k10 < 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().isSolid()
											|| k10 >= 0 && !this.world.isAirBlock(blockpos$mutableblockpos)) {
											continue label214;
										}
									}
								}

								double d6 = i7 + 0.5 - entityIn.posY;
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
			j1 = MathHelper.clamp(j1, 70, this.world.getActualHeight() - 10);
			k2 = j1;

			for (int j7 = -1; j7 <= 1; ++j7) {
				for (int i8 = 1; i8 < 3; ++i8) {
					for (int i9 = -1; i9 < 3; ++i9) {
						int l9 = i6 + (i8 - 1) * l6 + j7 * i3;
						int j10 = k2 + i9;
						int l10 = k6 + (i8 - 1) * i3 - j7 * l6;
						boolean flag = i9 < 0;
						blockpos$mutableblockpos.setPos(l9, j10, l10);
						this.world.setBlockState(blockpos$mutableblockpos, flag? Blocks.GLOWSTONE.getDefaultState() : Blocks.AIR.getDefaultState());
					}
				}
			}
		}

		for (int k7 = -1; k7 < 3; ++k7) {
			for (int j8 = -1; j8 < 4; ++j8) {
				if (k7 == -1 || k7 == 2 || j8 == -1 || j8 == 3) {
					blockpos$mutableblockpos.setPos(i6 + k7 * l6, k2 + j8, k6 + k7 * i3);
					this.world.setBlockState(blockpos$mutableblockpos, Blocks.GLOWSTONE.getDefaultState(), 3);
				}
			}
		}

		BlockState blockstate = AetherBlocks.AETHER_PORTAL.getDefaultState().with(AetherPortalBlock.AXIS, l6 == 0? Direction.Axis.Z : Direction.Axis.X);

		for (int k8 = 0; k8 < 2; ++k8) {
			for (int j9 = 0; j9 < 3; ++j9) {
				blockpos$mutableblockpos.setPos(i6 + k8 * l6, k2 + j9, k6 + k8 * i3);
				this.world.setBlockState(blockpos$mutableblockpos, blockstate, 18);
			}
		}

		return true;
	}

}
