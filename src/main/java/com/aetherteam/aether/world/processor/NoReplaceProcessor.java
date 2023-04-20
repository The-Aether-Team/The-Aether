package com.aetherteam.aether.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * Use this processor for structure pieces that shouldn't replace certain blocks in the world.
 * An example of this being used is the bronze dungeon's tunnel not replacing air to blend in with the landscape.
 */
public class NoReplaceProcessor extends StructureProcessor {
    public static final NoReplaceProcessor AIR = new NoReplaceProcessor(Blocks.AIR);
    public static final Codec<NoReplaceProcessor> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(ForgeRegistries.BLOCKS.getCodec().fieldOf("baseblock").forGetter(o -> o.baseBlock)
        ).apply(builder, NoReplaceProcessor::new));

    private final Block baseBlock;
    public NoReplaceProcessor(Block baseBlock) {
        this.baseBlock = baseBlock;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos pos, BlockPos pPos, StructureTemplate.StructureBlockInfo pBlockInfo, StructureTemplate.StructureBlockInfo pRelativeBlockInfo, StructurePlaceSettings pSettings, @Nullable StructureTemplate template) {
        BlockState state = level.getBlockState(pRelativeBlockInfo.pos);
        if (state.is(baseBlock)) {
            return new StructureTemplate.StructureBlockInfo(pRelativeBlockInfo.pos, state, null);
        }
        return pRelativeBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.NO_REPLACE.get();
    }
}
