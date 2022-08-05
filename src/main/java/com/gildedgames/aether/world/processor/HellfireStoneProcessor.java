package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.block.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

/**
 * Randomly replaces some hellfire stone in the gold dungeon with light hellfire stone
 */
public class HellfireStoneProcessor extends StructureProcessor {
    // Pass this instance to the structure pieces that use it
    public static final HellfireStoneProcessor INSTANCE = new HellfireStoneProcessor();
    public static final Codec<HellfireStoneProcessor> CODEC = Codec.unit(() -> INSTANCE);
    // Every hellfire stone block has a 10% probability of being a light hellfire stone block
    private final RandomBlockMatchTest blockMatch = new RandomBlockMatchTest(AetherBlocks.HELLFIRE_STONE.get(), 0.1F);

    /**
     * This is run for every block in the structure. We use this to replace the hellfire stone.
     */
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader pLevel, BlockPos p_74417_, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings pSettings, @Nullable StructureTemplate template) {
        RandomSource randomSource = RandomSource.create(Mth.getSeed(pRelativeBlockInfo.pos));
        if (blockMatch.test(pRelativeBlockInfo.state, randomSource)) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos, AetherBlocks.LIGHT_HELLFIRE_STONE.get().defaultBlockState(), null);
        }
        return pRelativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.HELLFIRE_STONE_REPLACE.get();
    }
}
