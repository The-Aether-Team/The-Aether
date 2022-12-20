package com.gildedgames.aether.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class TrappedBlockProcessor extends StructureProcessor {

    public static final Codec<TrappedBlockProcessor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ForgeRegistries.BLOCKS.getCodec().fieldOf("baseblock").forGetter(o -> o.baseBlock),
            ForgeRegistries.BLOCKS.getCodec().fieldOf("replacement").forGetter(o -> o.replacement),
            Codec.FLOAT.fieldOf("probability").forGetter(o -> o.probability)
    ).apply(builder, TrappedBlockProcessor::new));

    private final Block baseBlock;
    private final Block replacement;
    private final float probability;

    public TrappedBlockProcessor(Block baseBlock, Block replacement, float probability) {
        this.baseBlock = baseBlock;
        this.replacement = replacement;
        this.probability = probability;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos templatePos, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings pSettings, @Nullable StructureTemplate template) {
        RandomSource randomSource = RandomSource.create(Mth.getSeed(pRelativeBlockInfo.pos));
        if (pRelativeBlockInfo.state.is(this.baseBlock) && level.isEmptyBlock(pPos.above()) && randomSource.nextFloat() < this.probability) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos, this.replacement.defaultBlockState(), null);
        }
        return pRelativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.TRAPPED_BLOCKS.get();
    }
}
