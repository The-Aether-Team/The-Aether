package com.aetherteam.aether.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * Use this processor for structure pieces that shouldn't replace certain blocks in the world.
 * An example of this being used is the Bronze Dungeon's tunnel not replacing air to blend in with the landscape.
 */
public class NoReplaceProcessor extends StructureProcessor {
    public static final NoReplaceProcessor AIR = new NoReplaceProcessor(Blocks.AIR);

    public static final Codec<NoReplaceProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("baseblock").forGetter(o -> o.baseBlock)
    ).apply(instance, NoReplaceProcessor::new));

    private final Block baseBlock;

    public NoReplaceProcessor(Block baseBlock) {
        this.baseBlock = baseBlock;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings) {
        BlockState state = level.getBlockState(modifiedBlockInfo.pos());
        if (state.is(this.baseBlock)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), state, null);
        }
        return modifiedBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.NO_REPLACE.get();
    }
}
