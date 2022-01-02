package com.gildedgames.aether.common.world.structure.bronze;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.world.structure.AetherStructurePieces;
import com.gildedgames.aether.common.world.structure.BuriedBox;
import com.gildedgames.aether.core.util.BlockPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

import java.util.Random;

public class HolystoneTunnel extends BuriedBox {
    private static final BlockStateProvider HOLYSTONE = new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
            .add(AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 9)
            .add(AetherBlocks.MOSSY_HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true), 1)
            .build());

    public HolystoneTunnel(BoundingBox boundingBox, int recursionDepth, Direction direction) {
        super(AetherStructurePieces.HOLYSTONE_TUNNEL, boundingBox, recursionDepth);

        this.setOrientation(direction);
    }

    public HolystoneTunnel(StructurePieceSerializationContext context, CompoundTag tag) {
        super(AetherStructurePieces.HOLYSTONE_TUNNEL, context, tag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

    }

    @Override
    public void postProcess(WorldGenLevel level, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        BlockPlacers.shelledTunnel(level, HOLYSTONE, this.getOrientation().getAxis(), this.getBoundingBox(), box, random);
    }
}
