package com.gildedgames.aether.world.feature;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.util.BlockPlacementUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HolystoneSphereFeature extends Feature<NoneFeatureConfiguration> {

    public HolystoneSphereFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();

        BlockPlacementUtil.placeSphere(pos, 10.5F, reader, BlockStateProvider.simple(AetherBlocks.HOLYSTONE.get().defaultBlockState()), context.random());

        return true;
    }
}
