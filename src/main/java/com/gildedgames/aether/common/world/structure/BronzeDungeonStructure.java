package com.gildedgames.aether.common.world.structure;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class BronzeDungeonStructure //extends StructureFeature<NoneFeatureConfiguration>
{

//    public BronzeDungeonStructure(Codec<NoneFeatureConfiguration> codec) {
//        super(codec);
//    }
//
//    @Override
//    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//        return BronzeDungeonStructure.Start::new;
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {
//        return GenerationStep.Decoration.SURFACE_STRUCTURES;
//    }
//
//    @Override
//    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig) {
//        BlockPos pos = new BlockPos(chunkX << 4, chunkGenerator.getGenDepth()-1, chunkZ << 4);
//        BlockGetter reader = chunkGenerator.getBaseColumn(pos.getX(), pos.getZ());
//        return !(reader.getBlockState(new BlockPos(pos.getX(), 80, pos.getZ())).isAir()) &&
//                !(reader.getBlockState(new BlockPos(pos.getX(), 40, pos.getZ())).isAir());
//    }
//
//    public static class Start extends StructureStart<NoneFeatureConfiguration> {
//        public Start(StructureFeature<NoneFeatureConfiguration> structureIn, int chunkX, int chunkZ, BoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//        }
//
//        @Override
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManager, int chunkX, int chunkZ, Biome biome, NoneFeatureConfiguration config) {
//            int x = (chunkX << 4) + 7;
//            int z = (chunkZ << 4) + 7;
//
//            BlockPos blockpos = new BlockPos(x, 60, z);
//
//            JigsawPlacement.addPieces(
//                    dynamicRegistryManager,
//                    new JigsawConfiguration(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(Aether.MODID, "bronze_dungeon/start_pool")), 100),
//                    PoolElementStructurePiece::new,
//                    chunkGenerator,
//                    templateManager,
//                    blockpos,
//                    this.pieces,
//                    this.random,
//                    false,
//                    false);
//
//            this.calculateBoundingBox();
//        }
//    }
}
