package com.gildedgames.aether.world.structurepiece;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

import java.util.function.Function;

public abstract class FeatureStructurePiece extends StructurePiece {

    private static final Logger LOGGER = LogUtils.getLogger();
    protected PlacedFeature feature;
    protected BlockPos featurePosition;

    public FeatureStructurePiece(StructurePieceType type, PlacedFeature feature, BlockPos pos) {
        super(type, 0, new BoundingBox(pos.getX() - 20, pos.getY() - 20, pos.getZ() - 20, pos.getX() + 20, pos.getY() + 20, pos.getZ() + 20));
        this.setOrientation(Direction.NORTH);
        this.feature = feature;
    }
    public FeatureStructurePiece(StructurePieceType pType, CompoundTag pTag) {
        super(pType, pTag);
        this.featurePosition = new BlockPos(pTag.getInt("FX"), pTag.getInt("FY"), pTag.getInt("FZ"));
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
        pTag.putInt("FX", this.featurePosition.getX());
        pTag.putInt("FY", this.featurePosition.getY());
        pTag.putInt("FZ", this.featurePosition.getZ());
        pTag.putString("Feature", this.feature.toString());
    }

    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
        this.feature.place(pLevel, pGenerator, pRandom, pPos);
    }

    protected abstract void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox);

    /** @deprecated */
    @Deprecated
    public void move(int pX, int pY, int pZ) {
        super.move(pX, pY, pZ);
        this.featurePosition = this.featurePosition.offset(pX, pY, pZ);
    }

    public PlacedFeature feature() {
        return this.feature;
    }
}
