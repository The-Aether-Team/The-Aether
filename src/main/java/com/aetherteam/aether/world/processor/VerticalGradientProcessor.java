package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.BlockLogicUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * This processor is used to randomly place extra dirt near the top of the gold dungeon. It runs on any holystone block
 * that is right below aether dirt.
 */
public class VerticalGradientProcessor extends StructureProcessor {
    public static final VerticalGradientProcessor INSTANCE = new VerticalGradientProcessor();

    public static final MapCodec<VerticalGradientProcessor> CODEC = MapCodec.unit(VerticalGradientProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (level instanceof WorldGenLevel worldGenLevel) {
            // If the processor is running outside the center chunk, return immediately.
            if (worldGenLevel instanceof WorldGenRegion region && BlockLogicUtil.isOutOfBounds(modifiedBlockInfo.pos(), region.getCenter())) {
                return modifiedBlockInfo;
            }
            if (modifiedBlockInfo.state().is(AetherBlocks.AETHER_DIRT.get())) {
                BlockPos below = modifiedBlockInfo.pos().below();
                if (worldGenLevel.getBlockState(below).is(AetherTags.Blocks.HOLYSTONE)) {
                    RandomSource random = settings.getRandom(below);
                    if (random.nextBoolean()) {
                        worldGenLevel.setBlock(below, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), 2);
                    }
                }
            }
        }
        return modifiedBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.VERTICAL_GRADIENT.get();
    }
}
