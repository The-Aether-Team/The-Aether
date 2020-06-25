package com.aether.world;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

import javax.annotation.Nullable;
import java.util.*;

public class AetherBiomeProvider extends BiomeProvider {
    private final Biome biome;

    public AetherBiomeProvider(AetherBiomeProviderSettings p_i48972_1_) {
        this.biome = p_i48972_1_.getBiome();
    }

    public Biome getBiome(int p_201545_1_, int p_201545_2_) {
        return this.biome;
    }

    public Biome[] getBiomes(int p_201537_1_, int p_201537_2_, int p_201537_3_, int p_201537_4_, boolean p_201537_5_) {
        Biome[] lvt_6_1_ = new Biome[p_201537_3_ * p_201537_4_];
        Arrays.fill(lvt_6_1_, 0, p_201537_3_ * p_201537_4_, this.biome);
        return lvt_6_1_;
    }

    @Nullable
    public BlockPos findBiomePosition(int p_180630_1_, int p_180630_2_, int p_180630_3_, List<Biome> p_180630_4_, Random p_180630_5_) {
        return p_180630_4_.contains(this.biome) ? new BlockPos(p_180630_1_ - p_180630_3_ + p_180630_5_.nextInt(p_180630_3_ * 2 + 1), 0, p_180630_2_ - p_180630_3_ + p_180630_5_.nextInt(p_180630_3_ * 2 + 1)) : null;
    }

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return false;
    }


    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            this.topBlocksCache.add(this.biome.getSurfaceBuilderConfig().getTop());
        }

        return this.topBlocksCache;
    }

    public Set<Biome> getBiomesInSquare(int p_201538_1_, int p_201538_2_, int p_201538_3_) {
        return Sets.newHashSet(new Biome[]{this.biome});
    }
}
