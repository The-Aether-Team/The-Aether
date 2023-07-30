package com.aetherteam.aether.world.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class CrystalTreeTrunkPlacer extends StraightTrunkPlacer {
    public static final Codec<CrystalTreeTrunkPlacer> CODEC = RecordCodecBuilder.create((codec) -> trunkPlacerParts(codec).apply(codec, CrystalTreeTrunkPlacer::new));

    public CrystalTreeTrunkPlacer(int height, int heightRandA, int heightRandB) {
        super(height, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return AetherTrunkPlacerTypes.CRYSTAL_TREE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        super.placeTrunk(level, blockSetter, random, height, pos, config);
        float f = 0;
        for (int i = 2; i < 7; i += 3) {
            for (int l = 0; l < 4; l++) {
                int j = (int) Mth.cos(f);
                int k = (int) Mth.sin(f);
                BlockPos blockpos = pos.offset(j, i, k);
                this.placeLog(level, blockSetter, random, blockpos, config);
                f += 0.25F * Mth.TWO_PI;
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height), 0, false));
    }
}
