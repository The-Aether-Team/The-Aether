package com.aether.registry;

import com.aether.Aether;
import com.aether.client.particle.AetherPortalParticle;
import com.aether.client.particle.EvilWhirlyParticle;
import com.aether.client.particle.HolidayLeavesParticle;

import com.aether.client.particle.PassiveWhirlyParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AetherParticleTypes
{
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Aether.MODID);

	public static final RegistryObject<BasicParticleType> AETHER_PORTAL = PARTICLES.register("aether_portal", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> new BasicParticleType(false));

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		ParticleManager particleManager = Minecraft.getInstance().particles;

		particleManager.registerFactory(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		particleManager.registerFactory(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		particleManager.registerFactory(PASSIVE_WHIRLWIND.get(), PassiveWhirlyParticle.Factory::new);
		particleManager.registerFactory(EVIL_WHIRLWIND.get(), EvilWhirlyParticle.Factory::new);
	}
}
