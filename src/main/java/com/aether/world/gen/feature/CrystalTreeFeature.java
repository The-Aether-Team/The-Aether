package com.aether.world.gen.feature;

import com.aether.Aether;
import com.aether.registry.AetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class CrystalTreeFeature extends Feature<NoFeatureConfig> {

    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "crystal_tree");

    public CrystalTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        TemplateManager templatemanager = reader.getWorld().getServer().getTemplateManager();
        Template tree = templatemanager.getTemplateDefaulted(TREE);
        ChunkPos chunkpos = new ChunkPos(pos);
        MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementsettings = (new PlacementSettings()).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
        BlockPos blockpos = tree.getSize();
        int x = rand.nextInt(16 - blockpos.getX());
        int z = rand.nextInt(16 - blockpos.getZ());
        int y = 75 + rand.nextInt(50);


        BlockPos blockpos1 = tree.getZeroPositionWithTransform(pos.add(x, y, z), Mirror.NONE, Rotation.NONE);

        tree.func_237146_a_(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        for (int x1 = blockpos1.getX(); x1 < blockpos1.add(blockpos).getX(); x1++) {
            for (int y1 = blockpos1.getY(); y1 < blockpos1.add(blockpos).getY(); y1++) {
                for (int z1 = blockpos1.getZ(); z1 < blockpos1.add(blockpos).getZ(); z1++) {
                    BlockPos blockpos2 = new BlockPos(x1, y1, z1);
                    if (reader.getBlockState(blockpos2).getBlock() == AetherBlocks.CRYSTAL_LEAVES.get().getBlock() && rand.nextInt(5) == 2) {
                        reader.setBlockState(blockpos2, AetherBlocks.CRYSTAL_FRUIT_LEAVES.get().getDefaultState(), 19);
                    }
                }
            }
        }
        return true;
    }
}
