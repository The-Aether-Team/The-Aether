package com.aether.world.gen;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.NoiseChunkGenerator;

public class AetherChunkGenerator extends NoiseChunkGenerator<AetherGenerationSettings> {

    public AetherChunkGenerator(IWorld world, BiomeProvider biomeProvider, AetherGenerationSettings gen) {
        super(world, biomeProvider, 4, 8, 128, gen, true);
    }

    @Override
    protected void fillNoiseColumn(double[] p_222548_1_, int p_222548_2_, int p_222548_3_) {
        this.calcNoiseColumn(p_222548_1_, p_222548_2_, p_222548_3_, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 64, -3000);
    }

    @Override
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
        return new double[]{this.biomeProvider.func_222365_c(noiseX, noiseZ), 0.0D};
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return 8.0D - p_222545_1_;
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
