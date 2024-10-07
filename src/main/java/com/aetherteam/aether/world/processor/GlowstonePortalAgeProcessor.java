package com.aetherteam.aether.world.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

/**
 * This processor replaces Cobblestone blocks with Mossy Cobblestone blocks.
 */
public class GlowstonePortalAgeProcessor extends StructureProcessor {
    public static final MapCodec<GlowstonePortalAgeProcessor> CODEC = Codec.FLOAT.fieldOf("mossiness").xmap(GlowstonePortalAgeProcessor::new, (codec) -> codec.mossiness);
    private final float mossiness;

    public GlowstonePortalAgeProcessor(float mossiness) {
        this.mossiness = mossiness;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader level, BlockPos otherPos, BlockPos pos, StructureTemplate.StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        RandomSource random = settings.getRandom(relativeBlockInfo.pos());
        BlockState originalState = relativeBlockInfo.state();
        BlockPos blockPos = relativeBlockInfo.pos();
        BlockState newState = null;
        if (!originalState.is(Blocks.COBBLESTONE)) {
            if (originalState.is(Blocks.COBBLESTONE_STAIRS)) {
                newState = this.maybeReplaceStairs(random, relativeBlockInfo.state());
            } else if (originalState.is(Blocks.COBBLESTONE_SLAB)) {
                newState = this.maybeReplaceSlab(random, relativeBlockInfo.state());
            } else if (originalState.is(Blocks.COBBLESTONE_WALL)) {
                newState = this.maybeReplaceWall(random, relativeBlockInfo.state());
            }
        } else {
            newState = this.maybeReplaceFullStoneBlock(random);
        }

        return newState != null ? new StructureTemplate.StructureBlockInfo(blockPos, newState, relativeBlockInfo.nbt()) : relativeBlockInfo;
    }

    @Nullable
    private BlockState maybeReplaceFullStoneBlock(RandomSource random) {
        return random.nextFloat() < this.mossiness ? Blocks.MOSSY_COBBLESTONE.defaultBlockState() : null;
    }

    @Nullable
    private BlockState maybeReplaceStairs(RandomSource random, BlockState state) {
        return random.nextFloat() < this.mossiness ? Blocks.MOSSY_COBBLESTONE_STAIRS.withPropertiesOf(state) : null;
    }

    @Nullable
    private BlockState maybeReplaceSlab(RandomSource random, BlockState state) {
        return random.nextFloat() < this.mossiness ? Blocks.MOSSY_COBBLESTONE_SLAB.withPropertiesOf(state) : null;
    }

    @Nullable
    private BlockState maybeReplaceWall(RandomSource random, BlockState state) {
        return random.nextFloat() < this.mossiness ? Blocks.MOSSY_COBBLESTONE_WALL.withPropertiesOf(state) : null;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return AetherStructureProcessors.GLOWSTONE_PORTAL_AGE.get();
    }
}
