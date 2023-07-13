package com.aetherteam.aether.world.feature;

import com.aetherteam.aether.world.configuration.AetherLakeConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;

public class AetherLakeFeature extends Feature<AetherLakeConfiguration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public AetherLakeFeature(Codec<AetherLakeConfiguration> p_66259_) {
        super(p_66259_);
    }

    public boolean place(FeaturePlaceContext<AetherLakeConfiguration> p_159958_) {
        BlockPos blockpos = p_159958_.origin();
        WorldGenLevel worldgenlevel = p_159958_.level();
        RandomSource random = p_159958_.random();
        AetherLakeConfiguration aetherLakeConfiguration = p_159958_.config();
        if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 4) {
            return false;
        } else {
            blockpos = blockpos.below(4);
            boolean[] aboolean = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for (int j = 0; j < i; ++j) {
                double d0 = random.nextDouble() * 6.0 + 3.0;
                double d1 = random.nextDouble() * 4.0 + 2.0;
                double d2 = random.nextDouble() * 6.0 + 3.0;
                double d3 = random.nextDouble() * (16.0 - d0 - 2.0) + 1.0 + d0 / 2.0;
                double d4 = random.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
                double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;

                for(int l = 1; l < 15; ++l) {
                    for(int i1 = 1; i1 < 15; ++i1) {
                        for(int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double)l - d3) / (d0 / 2.0);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0) {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState blockstate1 = aetherLakeConfiguration.fluid().getState(random, blockpos);

            for(int k1 = 0; k1 < 16; ++k1) {
                for(int k = 0; k < 16; ++k) {
                    for(int l2 = 0; l2 < 8; ++l2) {
                        boolean flag = !aboolean[(k1 * 16 + k) * 8 + l2] && (k1 < 15 && aboolean[((k1 + 1) * 16 + k) * 8 + l2] || k1 > 0 && aboolean[((k1 - 1) * 16 + k) * 8 + l2] || k < 15 && aboolean[(k1 * 16 + k + 1) * 8 + l2] || k > 0 && aboolean[(k1 * 16 + (k - 1)) * 8 + l2] || l2 < 7 && aboolean[(k1 * 16 + k) * 8 + l2 + 1] || l2 > 0 && aboolean[(k1 * 16 + k) * 8 + (l2 - 1)]);
                        if (flag) {
                            Material material = worldgenlevel.getBlockState(blockpos.offset(k1, l2, k)).getMaterial();
                            if (l2 >= 4 && material.isLiquid()) {
                                return false;
                            }

                            if (l2 < 4 && !material.isSolid() && worldgenlevel.getBlockState(blockpos.offset(k1, l2, k)) != blockstate1) {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int l1 = 0; l1 < 16; ++l1) {
                for(int i2 = 0; i2 < 16; ++i2) {
                    for(int i3 = 0; i3 < 8; ++i3) {
                        if (aboolean[(l1 * 16 + i2) * 8 + i3]) {
                            BlockPos blockpos1 = blockpos.offset(l1, i3, i2);
                            if (this.canReplaceBlock(worldgenlevel.getBlockState(blockpos1))) {
                                boolean flag1 = i3 >= 4;
                                worldgenlevel.setBlock(blockpos1, flag1 ? AIR : blockstate1, 2);
                                if (flag1) {
                                    worldgenlevel.scheduleTick(blockpos1, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(worldgenlevel, blockpos1);
                                }
                            }
                        }
                    }
                }
            }

            BlockState blockstate2 = aetherLakeConfiguration.top().getState(random, blockpos);
            if (!blockstate2.isAir()) {
                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int j3 = 0; j3 < 16; ++j3) {
                        for (int j4 = 4; j4 < 8; ++j4) {
                            if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                                BlockPos blockpos2 = blockpos.offset(i2, j4 - 1, j3);
                                if (isDirt(worldgenlevel.getBlockState(blockpos2)) && worldgenlevel.getBrightness(LightLayer.SKY, blockpos.offset(i2, j4, j3)) > 0) {
                                    worldgenlevel.setBlock(blockpos2,blockstate2, 2);
                                }
                            }
                        }
                    }
                }
            }

            if (blockstate1.getFluidState().is(FluidTags.WATER)) {
                for(int k2 = 0; k2 < 16; ++k2) {
                    for(int k3 = 0; k3 < 16; ++k3) {
                        BlockPos blockpos2 = blockpos.offset(k2, 4, k3);
                        if (worldgenlevel.getBiome(blockpos2).value().shouldFreeze(worldgenlevel, blockpos2, false) && this.canReplaceBlock(worldgenlevel.getBlockState(blockpos2))) {
                            worldgenlevel.setBlock(blockpos2, Blocks.ICE.defaultBlockState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState p_190952_) {
        return !p_190952_.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}
