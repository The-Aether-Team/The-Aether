package com.aether.particles;

import com.aether.Aether;
import com.aether.client.particle.AetherPortalParticle;
import com.aether.client.particle.HolidayLeavesParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Aether.MODID)
public final class AetherParticleTypes {

	public static final BasicParticleType AETHER_PORTAL = null;
	public static final BasicParticleType HOLIDAY_LEAVES = null;
	
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static final class Registration {
		
		@SubscribeEvent
		public static void registerParticles(RegistryEvent.Register<ParticleType<?>> event) {
			event.getRegistry().registerAll(new ParticleType[] {
				
				particle("aether_portal", new BasicParticleType(false)),
				particle("holiday_leaves", new BasicParticleType(false)),
				
			});
		}
		
		private static ParticleType<?> particle(String name, ParticleType<?> particle) {
			particle.setRegistryName(name);
			return particle;
		}
		
	}

	@OnlyIn(Dist.CLIENT)
	@EventBusSubscriber(modid = Aether.MODID, bus = Bus.MOD)
	public static final class ClientRegistration {
		
		@SubscribeEvent
		public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
			@SuppressWarnings("resource")
			ParticleManager mgr = Minecraft.getInstance().particles;
			
			mgr.registerFactory(AETHER_PORTAL, AetherPortalParticle.Factory::new);
			mgr.registerFactory(HOLIDAY_LEAVES, HolidayLeavesParticle.Factory::new);
		}
		
	}
	
}
