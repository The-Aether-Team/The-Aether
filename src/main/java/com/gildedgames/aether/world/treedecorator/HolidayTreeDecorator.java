package com.gildedgames.aether.world.treedecorator;

import com.gildedgames.aether.AetherTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class HolidayTreeDecorator extends TreeDecorator {
    public static final Codec<HolidayTreeDecorator> CODEC = BlockStateProvider.CODEC.fieldOf("provider").xmap(HolidayTreeDecorator::new, (instance) -> {
        return instance.provider;
    }).codec();
    private final BlockStateProvider provider;

    public HolidayTreeDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    protected TreeDecoratorType<?> type() {
        return AetherTreeDecoratorTypes.HOLIDAY_TREE_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        List<BlockPos> logPositions = context.logs();
        if (!logPositions.isEmpty()) {
            int i = logPositions.get(0).getY();
            logPositions.stream().filter((logs) -> logs.getY() == i).forEach((logPos) -> {
                this.placeCircle(context, logPos);
            });
        }
    }

    private void placeCircle(Context context, BlockPos pPos) {
        LevelSimulatedReader level = context.level();
        RandomSource random = context.random();
        this.placeBlockAt(context, level, random, pPos, 0.0F);
        int radius = 10;

        for (int z = 1; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radius*radius) continue;
                float distance = (float) Math.sqrt(x*x + z*z) / (radius*radius);
                this.placeBlockAt(context, level, random, pPos.offset(x, 0, z), distance);
                this.placeBlockAt(context, level, random, pPos.offset(-x, 0, -z), distance);
                this.placeBlockAt(context, level, random, pPos.offset(-z, 0, x), distance);
                this.placeBlockAt(context, level, random, pPos.offset(z, 0, -x), distance);
            }
        }
    }

    private void placeBlockAt(Context context, LevelSimulatedReader pLevel, RandomSource pRandom, BlockPos pPos, float distance) {
        for(int i = 9; i >= -4; i--) {
            BlockPos blockpos = pPos.above(i);
            if (context.isAir(blockpos.above())) {
                if ((pLevel.isStateAtPosition(blockpos, HolidayTreeDecorator::isAetherGrass) || pLevel.isStateAtPosition(blockpos, HolidayTreeDecorator::isLeaves) || Feature.isGrassOrDirt(pLevel, blockpos)) && context.isAir(blockpos.above(4))) {
                    if (distance <= pRandom.nextFloat() / 2 * (1 - distance)) {
                        if (pLevel.isStateAtPosition(blockpos, HolidayTreeDecorator::isLeaves)) {
                            context.setBlock(blockpos.above(), Blocks.SNOW.defaultBlockState());
                        } else {
                            context.setBlock(blockpos.above(), this.provider.getState(pRandom, blockpos));
                        }
                    }
                }
            }
        }
    }
    private static boolean isAetherGrass(BlockState state) {
        return state.is(AetherTags.Blocks.AETHER_DIRT);
    }
    private static boolean isLeaves(BlockState state) {
        return state.is(BlockTags.LEAVES);
    }
}