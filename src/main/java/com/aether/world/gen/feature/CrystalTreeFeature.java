package com.aether.world.gen.feature;

import com.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
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

    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "crystal_tree");

    public CrystalTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Rotation rotation = Rotation.randomRotation(rand);

        TemplateManager templatemanager = reader.getWorld().getServer().getTemplateManager();
        Template tree = templatemanager.getTemplateDefaulted(TREE);
        ChunkPos chunkpos = new ChunkPos(pos);
        MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockpos = tree.transformedSize(rotation);
        int x = rand.nextInt(16 - blockpos.getX());
        int z = rand.nextInt(16 - blockpos.getZ());
        int y = 75 + rand.nextInt(50);

        /*
        for(int i1 = 0; i1 < blockpos.getX(); ++i1) {
            for(int j1 = 0; j1 < blockpos.getZ(); ++j1) {
                y = Math.min(y, reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + i1 + x, pos.getZ() + j1 + z));
            }
        }

        int k1 = Math.max(y - 15 - rand.nextInt(10), 10);
        */

        BlockPos blockpos1 = tree.getZeroPositionWithTransform(pos.add(x, y, z), Mirror.NONE, rotation);

//        IntegrityProcessor integrityprocessor = new IntegrityProcessor(0.9F);
//        placementsettings.clearProcessors().addProcessor(integrityprocessor);
        tree.func_237146_a_(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        //placementsettings.removeProcessor(integrityprocessor);

        return true;
    }
}
