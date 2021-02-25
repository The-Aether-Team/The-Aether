package com.gildedgames.aether.world.gen.feature;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;

import java.util.Random;

public class CrystalTreeFeature extends Feature<NoFeatureConfig> {

    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "crystal_tree/crystal_tree");
    private static final ResourceLocation FRUIT = new ResourceLocation(Aether.MODID, "crystal_tree/crystal_fruit_tree");

    public CrystalTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Rotation rotation = Rotation.randomRotation(rand);
        TemplateManager templatemanager = reader.getWorld().getServer().getTemplateManager();
        Template tree = templatemanager.getTemplateDefaulted(TREE);
        Template fruit = templatemanager.getTemplateDefaulted(FRUIT);
        ChunkPos chunkpos = new ChunkPos(pos);
        MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockpos = tree.transformedSize(rotation);
        int x = rand.nextInt(16 - blockpos.getX());
        int z = rand.nextInt(16 - blockpos.getZ());

        int y = Math.max(Math.min(reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + x, pos.getZ() + z) + rand.nextInt(20)+40, 200), 100);

        BlockPos blockpos1 = tree.getZeroPositionWithTransform(pos.add(x, y, z), Mirror.NONE, rotation);
        tree.func_237146_a_(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        IntegrityProcessor integrityprocessor = new IntegrityProcessor(0.2F);
        placementsettings.clearProcessors().addProcessor(integrityprocessor);
        fruit.func_237146_a_(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        return true;
    }
}
