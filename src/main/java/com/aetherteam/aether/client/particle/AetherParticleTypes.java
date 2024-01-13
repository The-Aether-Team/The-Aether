package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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

	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		event.registerSpriteSet(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
		event.registerSpriteSet(BOSS_DOORWAY_BLOCK.get(), DungeonBlockOverlayParticle.Factory::new);
		event.registerSpriteSet(EVIL_WHIRLWIND.get(), EvilWhirlwindParticle.Factory::new);
		event.registerSpriteSet(FROZEN.get(), FrozenParticle.Factory::new);
		event.registerSpriteSet(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
		event.registerSpriteSet(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		event.registerSpriteSet(PASSIVE_WHIRLWIND.get(), PassiveWhirlwindParticle.Factory::new);
		event.registerSpriteSet(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
	}
}
