package com.aether.world.gen;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.NoiseChunkGenerator;

import java.util.Random;

public class AetherChunkGenerator extends NoiseChunkGenerator<AetherGenerationSettings> {

    public AetherChunkGenerator(IWorld world, BiomeProvider biomeProvider, AetherGenerationSettings gen) {
        super(world, biomeProvider, 4, 8, 192, gen, true);
    }

    @Override
    protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {
        this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, 684.412F, 684.412F, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
        return new double[]{this.biomeProvider.func_222365_c(noiseX, noiseZ), 0.0D};
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return 8.0D;
    }

    @Override
    protected double func_222551_g() {
        return (int)super.func_222551_g() / 2;
    }

    @Override
    protected double func_222553_h() {
        return 8.0D;
    }

    @Override
    public int getGroundHeight() {
        return 64;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }
}
