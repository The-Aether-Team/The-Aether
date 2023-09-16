package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * This processor sets the {@link AetherBlockStateProperties#DOUBLE_DROPS} property to true for blocks that have it.
 */
public class DoubleDropsProcessor extends StructureProcessor {
    public static final DoubleDropsProcessor INSTANCE = new DoubleDropsProcessor();

    public static final Codec<DoubleDropsProcessor> CODEC = Codec.unit(DoubleDropsProcessor.INSTANCE);

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos origin, BlockPos centerBottom, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo modifiedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        if (modifiedBlockInfo.state().hasProperty(AetherBlockStateProperties.DOUBLE_DROPS)) {
            return new StructureTemplate.StructureBlockInfo(modifiedBlockInfo.pos(), modifiedBlockInfo.state().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), modifiedBlockInfo.nbt());
        }
        return super.process(level, origin, centerBottom, originalBlockInfo, modifiedBlockInfo, settings, template);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.DOUBLE_DROPS.get();
    }
}
