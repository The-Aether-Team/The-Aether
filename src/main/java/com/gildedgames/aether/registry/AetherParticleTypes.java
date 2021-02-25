package com.gildedgames.aether.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.particle.AetherPortalParticle;
import com.gildedgames.aether.client.particle.EvilWhirlyParticle;
import com.gildedgames.aether.client.particle.HolidayLeavesParticle;

import com.gildedgames.aether.client.particle.PassiveWhirlyParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherParticleTypes
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
