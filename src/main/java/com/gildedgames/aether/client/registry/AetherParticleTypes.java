package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.particle.*;

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
	public static final RegistryObject<BasicParticleType> GOLDEN_OAK_LEAVES = PARTICLES.register("golden_oak_leaves", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> CRYSTAL_LEAVES = PARTICLES.register("crystal_leaves", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> HOLIDAY_LEAVES = PARTICLES.register("holiday_leaves", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> FREEZER = PARTICLES.register("freezer", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> PASSIVE_WHIRLWIND = PARTICLES.register("passive_whirlwind", () -> new BasicParticleType(false));
	public static final RegistryObject<BasicParticleType> EVIL_WHIRLWIND = PARTICLES.register("evil_whirlwind", () -> new BasicParticleType(false));

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
		ParticleManager particleManager = Minecraft.getInstance().particleEngine;

		particleManager.register(AETHER_PORTAL.get(), AetherPortalParticle.Factory::new);
		particleManager.register(GOLDEN_OAK_LEAVES.get(), GoldenOakLeavesParticle.Factory::new);
		particleManager.register(CRYSTAL_LEAVES.get(), CrystalLeavesParticle.Factory::new);
		particleManager.register(HOLIDAY_LEAVES.get(), HolidayLeavesParticle.Factory::new);
		particleManager.register(FREEZER.get(), FreezerParticle.Factory::new);
		particleManager.register(PASSIVE_WHIRLWIND.get(), PassiveWhirlyParticle.Factory::new);
		particleManager.register(EVIL_WHIRLWIND.get(), EvilWhirlyParticle.Factory::new);
	}
}
