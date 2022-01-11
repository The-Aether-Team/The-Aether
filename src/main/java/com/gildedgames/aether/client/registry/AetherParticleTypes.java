package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.particle.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherParticleTypes
{
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Aether.MODID);

	public static final RegistryObject<SimpleParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> FROZEN = PARTICLES.register("frozen", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> ZEPHYR_SNOWFLAKE = PARTICLES.register("zephyr_snowflake", ()-> new SimpleParticleType(false));

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		ParticleEngine particleManager = Minecraft.getInstance().particleEngine;

		particleManager.register(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		particleManager.register(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
		particleManager.register(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
		particleManager.register(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		particleManager.register(FROZEN.get(), FrozenParticle.Factory::new);
		particleManager.register(PASSIVE_WHIRLWIND.get(), PassiveWhirlyParticle.Factory::new);
		particleManager.register(EVIL_WHIRLWIND.get(), EvilWhirlyParticle.Factory::new);
		particleManager.register(ZEPHYR_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
	}
}
