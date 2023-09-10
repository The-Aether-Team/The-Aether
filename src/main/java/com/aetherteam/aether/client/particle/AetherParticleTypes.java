package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.Aether;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Aether.MODID);

	public static final RegistryObject<SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> BOSS_DOORWAY_BLOCK = PARTICLES.register("door", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> FROZEN = PARTICLES.register("frozen", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", () -> new SimpleParticleType(false));

	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.register(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		event.register(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
		event.register(BOSS_DOORWAY_BLOCK.get(), DungeonBlockOverlayParticle.Factory::new);
		event.register(EVIL_WHIRLWIND.get(), EvilWhirlwindParticle.Factory::new);
		event.register(FROZEN.get(), FrozenParticle.Factory::new);
		event.register(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
		event.register(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		event.register(PASSIVE_WHIRLWIND.get(), PassiveWhirlwindParticle.Factory::new);
		event.register(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
	}
}
