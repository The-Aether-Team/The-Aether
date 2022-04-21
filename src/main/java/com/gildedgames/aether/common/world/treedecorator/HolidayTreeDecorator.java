package com.gildedgames.aether.common.world.treedecorator;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.worldgen.AetherTreeDecoratorTypes;
import com.gildedgames.aether.core.util.BlockPlacers;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

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

    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random pRandom, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        if (!logPositions.isEmpty()) {
            int i = logPositions.get(0).getY();
            logPositions.stream().filter((logs) -> logs.getY() == i).forEach((logPos) -> {
                this.placeCircle(level, blockSetter, pRandom, logPos);
            });
        }
    }

    private void placeCircle(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, Random pRandom, BlockPos pPos) {
        this.placeBlockAt(pLevel, pBlockSetter, pRandom, pPos);
        int radius = 8;

        for (int z = 1; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radius*radius) continue;
                this.placeBlockAt(pLevel, pBlockSetter, pRandom, pPos.offset(x, 0, z));
                this.placeBlockAt(pLevel, pBlockSetter, pRandom, pPos.offset(-x, 0, -z));
                this.placeBlockAt(pLevel, pBlockSetter, pRandom, pPos.offset(-z, 0, x));
                this.placeBlockAt(pLevel, pBlockSetter, pRandom, pPos.offset(z, 0, -x));
            }
        }
    }

    private void placeBlockAt(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, Random pRandom, BlockPos pPos) {
        for(int i = 9; i >= -3; i--) {
            BlockPos blockpos = pPos.above(i);
            if (Feature.isAir(pLevel, blockpos.above())) {
                if (!Feature.isAir(pLevel, blockpos) && !pLevel.isStateAtPosition(blockpos, HolidayTreeDecorator::isSnow) && Feature.isAir(pLevel, blockpos.above(4))) {
                    if (Feature.isGrassOrDirt(pLevel, blockpos)) {
                        pBlockSetter.accept(blockpos.above(), this.provider.getState(pRandom, blockpos));
                    } else {
                        pBlockSetter.accept(blockpos.above(), Blocks.SNOW.defaultBlockState());
                    }
                }
            }
        }
    }

    private static boolean isSnow(BlockState state) {
        return state == Blocks.SNOW.defaultBlockState();
    }
}