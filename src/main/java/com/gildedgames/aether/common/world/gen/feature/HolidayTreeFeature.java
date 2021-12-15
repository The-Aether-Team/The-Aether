package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
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

import java.util.Calendar;
import java.util.Random;

public class HolidayTreeFeature extends Feature<NoFeatureConfig>
{
    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "holiday_tree/holiday_tree");
    private static final ResourceLocation DECORATION = new ResourceLocation(Aether.MODID, "holiday_tree/holiday_decorated_tree");

    public HolidayTreeFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {

            Rotation rotation = Rotation.getRandom(rand);
            TemplateManager templatemanager = reader.getLevel().getServer().getStructureManager();
            Template tree = templatemanager.getOrCreate(TREE);
            Template decor = templatemanager.getOrCreate(DECORATION);
            ChunkPos chunkpos = new ChunkPos(pos);
            MutableBoundingBox mutableboundingbox = new MutableBoundingBox(chunkpos.getMinBlockX(), 0, chunkpos.getMinBlockZ(), chunkpos.getMaxBlockX(), 256, chunkpos.getMaxBlockZ());
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);

            BlockPos size = tree.getSize(rotation);
            int rx = Math.floorDiv(size.getX(), 2);
            int rz = Math.floorDiv(size.getZ(), 2);
            int x = rand.nextInt(16 - size.getX());
            int z = rand.nextInt(16 - size.getZ());
            int y = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + x + rx, pos.getZ() + z + rz);
            if (y <= 48) return true;

            pos = pos.offset(x, y, z);

            for (int i = -rx*2; i < rx*2 + 1; i++) {
                for (int j = -rz*2; j < rz*2 + 1; j++) {
                    if (i * i + j * j <= 3.5 * rx * rx) {
                        y = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX() + rx + i, pos.getZ() + rz + j);
                        if (y <= 48) continue;
                        BlockPos pos1 = new BlockPos(pos.getX() + rx + i, y, pos.getZ() + rz + j);

                        if (rand.nextInt(6) == 0) {
                            this.setBlock(reader, pos1, AetherBlocks.PRESENT.get().defaultBlockState());
                        } else {
                            this.setBlock(reader, pos1, Blocks.SNOW.defaultBlockState());
                        }
                    }
                }
            }

            BlockPos blockpos1 = tree.getZeroPositionWithTransform(pos, Mirror.NONE, rotation);
            tree.placeInWorld(reader, blockpos1, blockpos1, placementsettings, rand, 4);
            IntegrityProcessor integrityprocessor = new IntegrityProcessor(0.2F);
            placementsettings.clearProcessors().addProcessor(integrityprocessor);
            decor.placeInWorld(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        }
        return true;
    }
}
