package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class AetherParticleTypes {
	public static final LazyRegistrar<ParticleType<?>> PARTICLES = LazyRegistrar.create(Registries.PARTICLE_TYPE, Aether.MODID);

	public static final RegistryObject<SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> BOSS_DOORWAY_BLOCK = PARTICLES.register("door", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> FROZEN = PARTICLES.register("frozen", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> FabricParticleTypes.simple(false));
	public static final RegistryObject<SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> FabricParticleTypes.simple(false));

	@Environment(EnvType.CLIENT)
	public static void registerParticleFactories() {
		ParticleFactoryRegistry.getInstance().register(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(BOSS_DOORWAY_BLOCK.get(), DungeonBlockOverlayParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(EVIL_WHIRLWIND.get(), EvilWhirlwindParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(FROZEN.get(), FrozenParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(PASSIVE_WHIRLWIND.get(), PassiveWhirlwindParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
	}
}
