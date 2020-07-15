package com.aether.world.dimension;

import javax.annotation.Nullable;

import com.aether.client.renderer.AetherSkyRenderer;
import com.aether.world.biome.AetherBiomes;
import com.aether.world.gen.AetherChunkGenerator;
import com.aether.world.gen.AetherGenerationSettings;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

public class AetherDimension extends Dimension {

	public AetherDimension(World world, DimensionType type) {
		super(world, type, 0);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		AetherGenerationSettings aetherGen = new AetherGenerationSettings();
		SingleBiomeProviderSettings providerSettings = new SingleBiomeProviderSettings(this.world.getWorldInfo()).setBiome(AetherBiomes.AETHER_VOID.get());
		BiomeProvider provider = new SingleBiomeProvider(providerSettings);
		return new AetherChunkGenerator(this.world, provider, aetherGen);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getCloudHeight() {
		return 1F;
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

	// TODO: implement the eternal day thing
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		double d0 = MathHelper.frac(worldTime / 24000.0D - 0.25D);
		double d1 = 0.5D - Math.cos(d0 * Math.PI) / 2.0D;
		return (float)(d0 * 2.0D + d1) / 3.0F;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
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

	@Override
	@OnlyIn(Dist.CLIENT)
	public IRenderHandler getSkyRenderer() {
		IRenderHandler skyRenderer = super.getSkyRenderer();
		if (skyRenderer == null) {
			this.setSkyRenderer(skyRenderer = new AetherSkyRenderer());
		}
		return skyRenderer;
	}
	
}
