package com.gildedgames.aether.world.processor;

import com.gildedgames.aether.block.AetherBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * Randomly replaces some hellfire stone in the gold dungeon with light hellfire stone
 */
@Deprecated(forRemoval = true) // Obsolete - see RuleProcessor, ProcessorRule, and RandomBlockMatchTest
public class DungeonStoneProcessor extends StructureProcessor {
    // Pass this instance to the structure pieces that use it
    public static final DungeonStoneProcessor HELLFIRE = new DungeonStoneProcessor(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get(), 0.1F);
    public static final Codec<DungeonStoneProcessor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ForgeRegistries.BLOCKS.getCodec().fieldOf("baseblock").forGetter(o -> o.baseBlock),
            ForgeRegistries.BLOCKS.getCodec().fieldOf("replacement").forGetter(o -> o.replacement),
            Codec.FLOAT.fieldOf("probability").forGetter(o -> o.probability)
    ).apply(builder, DungeonStoneProcessor::new));

    private final Block baseBlock;
    private final Block replacement;
    private final float probability;

    public DungeonStoneProcessor(Block baseBlock, Block replacement, float probability) {
        this.baseBlock = baseBlock;
        this.replacement = replacement;
        this.probability = probability;
    }

    /**
     * This is run for every block in the structure. We use this to replace the hellfire stone.
     */
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings pSettings, @Nullable StructureTemplate template) {
        RandomSource randomSource = RandomSource.create(Mth.getSeed(pRelativeBlockInfo.pos));
        if (pRelativeBlockInfo.state.is(this.baseBlock) && randomSource.nextFloat() < this.probability) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos, this.replacement.defaultBlockState(), null);
        }
        return pRelativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.DUNGEON_STONE_REPLACE.get();
    }
}
