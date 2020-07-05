package com.aether.world;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * @deprecated Use {@linkplain SingleBiomeProvider}
 */
@Deprecated
public class AetherBiomeProvider extends BiomeProvider {
    private final Biome biome;

    public AetherBiomeProvider(AetherBiomeProviderSettings settings) {
        this.biome = settings.getBiome();
    }

    @Override
	public Biome getBiome(int x, int y) {
        return this.biome;
    }

    @Override
	public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        Biome[] biomes = new Biome[width * length];
        Arrays.fill(biomes, this.biome);
        return biomes;
    }

    @Override
	@Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random rand) {
        return biomes.contains(this.biome) ? new BlockPos(x - range + rand.nextInt(range * 2 + 1), 0, z - range + rand.nextInt(range * 2 + 1)) : null;
    }

    @Override
    public boolean hasStructure(Structure<?> structure) {
        return false;
    }


    @Override
	public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            this.topBlocksCache.add(this.biome.getSurfaceBuilderConfig().getTop());
        }

        return this.topBlocksCache;
    }

    @Override
	public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        return Collections.singleton(this.biome);
    }
    
}
