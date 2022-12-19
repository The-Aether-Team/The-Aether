package com.gildedgames.aether.world.structurepiece;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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
    protected ResourceKey<PlacedFeature> feature;
    protected BlockPos featurePosition;

    public FeatureStructurePiece(StructurePieceType type, ResourceKey<PlacedFeature> feature, BlockPos pos) {
        super(type, 0, new BoundingBox(pos.getX() - 20, pos.getY() - 20, pos.getZ() - 20, pos.getX() + 20, pos.getY() + 20, pos.getZ() + 20));
        this.setOrientation(Direction.NORTH);
        this.feature = feature;
    }
    public FeatureStructurePiece(StructurePieceType pType, CompoundTag pTag) {
        super(pType, pTag);
        this.featurePosition = new BlockPos(pTag.getInt("FX"), pTag.getInt("FY"), pTag.getInt("FZ"));
    }

    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putInt("FX", this.featurePosition.getX());
        tag.putInt("FY", this.featurePosition.getY());
        tag.putInt("FZ", this.featurePosition.getZ());
        tag.putString("Feature", this.feature.toString());
    }

    public void postProcess(WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, RandomSource random, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
        PlacedFeature placed = level.registryAccess().registryOrThrow(Registry.PLACED_FEATURE_REGISTRY).getOrCreateHolderOrThrow(feature).get();
        placed.place(level, generator, random, pos);
    }

    protected abstract void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box);

    /** @deprecated */
    @Deprecated
    public void move(int pX, int pY, int pZ) {
        super.move(pX, pY, pZ);
        this.featurePosition = this.featurePosition.offset(pX, pY, pZ);
    }
}