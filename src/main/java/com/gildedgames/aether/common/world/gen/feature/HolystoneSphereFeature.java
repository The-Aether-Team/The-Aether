package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.util.BlockPlacers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HolystoneSphereFeature extends Feature<NoneFeatureConfiguration> {
    private final BlockStateProvider holystone = BlockStateProvider.simple(AetherBlocks.HOLYSTONE.get().defaultBlockState());

    public HolystoneSphereFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();

        BlockPlacers.placeSphere(pos, 4.5f, reader, this.holystone, context.random());

        return true;
    }
}
