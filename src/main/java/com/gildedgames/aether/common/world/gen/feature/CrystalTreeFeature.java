package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class CrystalTreeFeature extends Feature<NoneFeatureConfiguration>
{
    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "crystal_tree/crystal_tree");
    private static final ResourceLocation FRUIT = new ResourceLocation(Aether.MODID, "crystal_tree/crystal_fruit_tree");

    public CrystalTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel reader = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        Rotation rotation = Rotation.getRandom(rand);
        StructureManager templatemanager = reader.getLevel().getServer().getStructureManager();
        StructureTemplate tree = templatemanager.getOrCreate(TREE);
        StructureTemplate fruit = templatemanager.getOrCreate(FRUIT);
        ChunkPos chunkpos = new ChunkPos(pos);
        BoundingBox mutableboundingbox = new BoundingBox(chunkpos.getMinBlockX(), 0, chunkpos.getMinBlockZ(), chunkpos.getMaxBlockX(), 256, chunkpos.getMaxBlockZ());
        StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        Vec3i vec3i = tree.getSize(rotation);
        int x = rand.nextInt(16 - vec3i.getX());
        int z = rand.nextInt(16 - vec3i.getZ());

        int y = Math.max(Math.min(reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX() + x, pos.getZ() + z) + rand.nextInt(20)+40, 200), 100);

        BlockPos blockpos1 = tree.getZeroPositionWithTransform(pos.offset(x, y, z), Mirror.NONE, rotation);
        tree.placeInWorld(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        BlockRotProcessor integrityprocessor = new BlockRotProcessor(0.2F);
        placementsettings.clearProcessors().addProcessor(integrityprocessor);
        fruit.placeInWorld(reader, blockpos1, blockpos1, placementsettings, rand, 4);

        return true;
    }
}
