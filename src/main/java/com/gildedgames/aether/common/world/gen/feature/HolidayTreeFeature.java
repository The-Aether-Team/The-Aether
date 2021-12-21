package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.AetherConfig;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.gen.feature.template.*;

import java.util.Calendar;
import java.util.Random;

import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class HolidayTreeFeature extends Feature<NoneFeatureConfiguration>
{
    private static final ResourceLocation TREE = new ResourceLocation(Aether.MODID, "holiday_tree/holiday_tree");
    private static final ResourceLocation DECORATION = new ResourceLocation(Aether.MODID, "holiday_tree/holiday_decorated_tree");

    public HolidayTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos, NoneFeatureConfiguration config) {
        Calendar calendar = Calendar.getInstance();
        if ((AetherConfig.COMMON.generate_holiday_tree_seasonally.get() && (calendar.get(Calendar.MONTH) == Calendar.DECEMBER || calendar.get(Calendar.MONTH) == Calendar.JANUARY)) || AetherConfig.COMMON.generate_holiday_tree_always.get()) {

            Rotation rotation = Rotation.getRandom(rand);
            StructureManager templatemanager = reader.getLevel().getServer().getStructureManager();
            StructureTemplate tree = templatemanager.getOrCreate(TREE);
            StructureTemplate decor = templatemanager.getOrCreate(DECORATION);
            ChunkPos chunkpos = new ChunkPos(pos);
            BoundingBox mutableboundingbox = new BoundingBox(chunkpos.getMinBlockX(), 0, chunkpos.getMinBlockZ(), chunkpos.getMaxBlockX(), 256, chunkpos.getMaxBlockZ());
            StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(rotation).setBoundingBox(mutableboundingbox).setRandom(rand).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);

            BlockPos size = tree.getSize(rotation);
            int rx = Math.floorDiv(size.getX(), 2);
            int rz = Math.floorDiv(size.getZ(), 2);
            int x = rand.nextInt(16 - size.getX());
            int z = rand.nextInt(16 - size.getZ());
            int y = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX() + x + rx, pos.getZ() + z + rz);
            if (y <= 48) return true;

            pos = pos.offset(x, y, z);

            for (int i = -rx*2; i < rx*2 + 1; i++) {
                for (int j = -rz*2; j < rz*2 + 1; j++) {
                    if (i * i + j * j <= 3.5 * rx * rx) {
                        y = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX() + rx + i, pos.getZ() + rz + j);
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
            BlockRotProcessor integrityprocessor = new BlockRotProcessor(0.2F);
            placementsettings.clearProcessors().addProcessor(integrityprocessor);
            decor.placeInWorld(reader, blockpos1, blockpos1, placementsettings, rand, 4);
        }
        return true;
    }
}
