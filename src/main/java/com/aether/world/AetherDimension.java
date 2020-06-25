package com.aether.world;

import com.aether.world.gen.AetherChunkGenerator;
import com.aether.world.gen.AetherGenSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class AetherDimension extends Dimension {

    public AetherDimension(World world, DimensionType type) {
        super(world, type);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        AetherGenSettings aetherGen = new AetherGenSettings();
        AetherBiomeProviderSettings providerSettings = new AetherBiomeProviderSettings();
        AetherBiomeProvider provider = new AetherBiomeProvider(providerSettings);
        return new AetherChunkGenerator(world, provider, aetherGen);
    }

    @OnlyIn(Dist.CLIENT)
    public float getCloudHeight() {
        return 1F;
    }

    @Override
    public double getHorizon() {
        return 0;
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return null;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 1F;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getSkyColor(BlockPos p_217382_1_, float p_217382_2_) {
        return new Vec3d(0.74, 0.74, 0.98);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(0.57, 0.57, 0.74);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}
