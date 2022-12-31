package com.gildedgames.aether.data.resources.registries;

import com.gildedgames.aether.Aether;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class AetherNoises {
	
	public static final ResourceKey<NoiseParameters> TEMPERATURE = createKey("temperature");
	public static final ResourceKey<NoiseParameters> VEGETATION = createKey("vegetation");
	
	private static ResourceKey<NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(Aether.MODID, name));
    }
	
	public static void bootstrap(BootstapContext<NoiseParameters> ctx) {
		register(ctx, TEMPERATURE, -10, 1.5D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D);
		register(ctx, VEGETATION, -8, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }
	
	public static void register(BootstapContext<NoiseParameters> ctx, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
		ctx.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}

}
