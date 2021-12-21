package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

import java.util.Random;

/**
 * @see net.minecraft.world.level.levelgen.feature.LakeFeature
 * This class is an edited version of LakesFeature. The normal LakesFeature is hardcoded to generate either
 * grass or mycelium, depending on the SurfaceBuilder's top block. This version generates whatever the
 * surfacebuilder's top block is.
 */
public class AetherLakeFeature extends Feature<BlockStateConfiguration>
{
    public AetherLakeFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();
        Random rand = context.random();
        BlockStateConfiguration config = context.config();
        while (pos.getY() > 5 && reader.isEmptyBlock(pos)) {
            pos = pos.below();
        }

        if (pos.getY() <= 4) {
            return true;
        } else {
            pos = pos.below(4);
            if (reader.startsForFeature(SectionPos.of(pos), StructureFeature.VILLAGE).stream().findAny().isEmpty()) {
                boolean[] aboolean = new boolean[2048];
                int i = rand.nextInt(4) + 4;

                for (int j = 0; j < i; ++j) {
                    double d0 = rand.nextDouble() * 6.0D + 3.0D;
                    double d1 = rand.nextDouble() * 4.0D + 2.0D;
                    double d2 = rand.nextDouble() * 6.0D + 3.0D;
                    double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                    double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                    double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                    for (int l = 1; l < 15; ++l) {
                        for (int i1 = 1; i1 < 15; ++i1) {
                            for (int j1 = 1; j1 < 7; ++j1) {
                                double d6 = ((double) l - d3) / (d0 / 2.0D);
                                double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                                double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                                double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                                if (d9 < 1.0D) {
                                    aboolean[(l * 16 + i1) * 8 + j1] = true;
                                }
                            }
                        }
                    }
                }

                for (int k1 = 0; k1 < 16; ++k1) {
                    for (int l2 = 0; l2 < 16; ++l2) {
                        for (int k = 0; k < 8; ++k) {
                            boolean flag = !aboolean[(k1 * 16 + l2) * 8 + k] && (k1 < 15 && aboolean[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && aboolean[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && aboolean[(k1 * 16 + l2 + 1) * 8 + k] || l2 > 0 && aboolean[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && aboolean[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && aboolean[(k1 * 16 + l2) * 8 + (k - 1)]);
                            if (flag) {
                                Material material = reader.getBlockState(pos.offset(k1, k, l2)).getMaterial();
                                if (k >= 4 && material.isLiquid()) {
                                    return true;
                                }

                                if (k < 4 && !material.isSolid() && reader.getBlockState(pos.offset(k1, k, l2)) != config.state) {
                                    return true;
                                }
                            }
                        }
                    }
                }

                for (int l1 = 0; l1 < 16; ++l1) {
                    for (int i3 = 0; i3 < 16; ++i3) {
                        for (int i4 = 0; i4 < 8; ++i4) {
                            if (aboolean[(l1 * 16 + i3) * 8 + i4]) {
                                reader.setBlock(pos.offset(l1, i4, i3), i4 >= 4 ? Blocks.CAVE_AIR.defaultBlockState() : config.state, 2);
                            }
                        }
                    }
                }

                for (int i2 = 0; i2 < 16; ++i2) {
                    for (int j3 = 0; j3 < 16; ++j3) {
                        for (int j4 = 4; j4 < 8; ++j4) {
                            if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                                BlockPos blockpos = pos.offset(i2, j4 - 1, j3);
                                if (isDirt(reader.getBlockState(blockpos)) && reader.getBrightness(LightLayer.SKY, pos.offset(i2, j4, j3)) > 0) {
                                    Biome biome = reader.getBiome(blockpos);
                                    // This is changed from LakesFeature to allow the feature to generate Aether grass instead of Minecraft's grass block
                                    reader.setBlock(blockpos, AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }

                if (config.state.getMaterial() == Material.LAVA) {
                    for (int j2 = 0; j2 < 16; ++j2) {
                        for (int k3 = 0; k3 < 16; ++k3) {
                            for (int k4 = 0; k4 < 8; ++k4) {
                                boolean flag1 = !aboolean[(j2 * 16 + k3) * 8 + k4] && (j2 < 15 && aboolean[((j2 + 1) * 16 + k3) * 8 + k4] || j2 > 0 && aboolean[((j2 - 1) * 16 + k3) * 8 + k4] || k3 < 15 && aboolean[(j2 * 16 + k3 + 1) * 8 + k4] || k3 > 0 && aboolean[(j2 * 16 + (k3 - 1)) * 8 + k4] || k4 < 7 && aboolean[(j2 * 16 + k3) * 8 + k4 + 1] || k4 > 0 && aboolean[(j2 * 16 + k3) * 8 + (k4 - 1)]);
                                if (flag1 && (k4 < 4 || rand.nextInt(2) != 0) && reader.getBlockState(pos.offset(j2, k4, k3)).getMaterial().isSolid()) {
                                    reader.setBlock(pos.offset(j2, k4, k3), Blocks.STONE.defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }

                if (config.state.getMaterial() == Material.WATER) {
                    for (int k2 = 0; k2 < 16; ++k2) {
                        for (int l3 = 0; l3 < 16; ++l3) {
                            int l4 = 4;
                            BlockPos blockpos1 = pos.offset(k2, 4, l3);
                            if (reader.getBiome(blockpos1).shouldFreeze(reader, blockpos1, false)) {
                                reader.setBlock(blockpos1, Blocks.ICE.defaultBlockState(), 2);
                            }
                        }
                    }
                }

            }
            return true;
        }
    }
}
