package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.block.AetherBlocks;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * This processor replaces Cobblestone blocks Holystone Brick blocks.
 */
public class HolystoneReplaceProcessor extends StructureProcessor {
    public static final Codec<HolystoneReplaceProcessor> CODEC = Codec.unit(() -> HolystoneReplaceProcessor.INSTANCE);
    public static final HolystoneReplaceProcessor INSTANCE = new HolystoneReplaceProcessor();
    private final Map<Block, Block> replacements = Util.make(Maps.newHashMap(), (map) -> {
        map.put(Blocks.COBBLESTONE, AetherBlocks.HOLYSTONE_BRICKS.get());
        map.put(Blocks.MOSSY_COBBLESTONE, AetherBlocks.HOLYSTONE_BRICKS.get());
        map.put(Blocks.COBBLESTONE_STAIRS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get());
        map.put(Blocks.MOSSY_COBBLESTONE_STAIRS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get());
        map.put(Blocks.COBBLESTONE_SLAB, AetherBlocks.HOLYSTONE_BRICK_SLAB.get());
        map.put(Blocks.MOSSY_COBBLESTONE_SLAB, AetherBlocks.HOLYSTONE_BRICK_SLAB.get());
        map.put(Blocks.COBBLESTONE_WALL, AetherBlocks.HOLYSTONE_BRICK_WALL.get());
        map.put(Blocks.MOSSY_COBBLESTONE_WALL, AetherBlocks.HOLYSTONE_BRICK_WALL.get());
    });

    private HolystoneReplaceProcessor() { }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos otherPos, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings) {
        Block block = this.replacements.get(relativeBlockInfo.state().getBlock());
        if (block == null) {
            return relativeBlockInfo;
        } else {
            BlockState originalState = relativeBlockInfo.state();
            BlockState newState = block.defaultBlockState();
            if (originalState.hasProperty(StairBlock.FACING)) {
                newState = newState.setValue(StairBlock.FACING, originalState.getValue(StairBlock.FACING));
            }
            if (originalState.hasProperty(StairBlock.HALF)) {
                newState = newState.setValue(StairBlock.HALF, originalState.getValue(StairBlock.HALF));
            }
            if (originalState.hasProperty(SlabBlock.TYPE)) {
                newState = newState.setValue(SlabBlock.TYPE, originalState.getValue(SlabBlock.TYPE));
            }
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), newState, relativeBlockInfo.nbt());
        }
    }

    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.HOLYSTONE_REPLACE.get();
    }
}
