package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.capabilities.arrow.PhoenixArrow;
import com.gildedgames.aether.core.capability.capabilities.arrow.PhoenixArrowProvider;
import com.gildedgames.aether.core.capability.capabilities.arrow.PhoenixArrowStorage;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntity;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntityProvider;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntityStorage;
import com.gildedgames.aether.core.capability.capabilities.eternal_day.EternalDay;
import com.gildedgames.aether.core.capability.capabilities.eternal_day.EternalDayProvider;
import com.gildedgames.aether.core.capability.capabilities.eternal_day.EternalDayStorage;
import com.gildedgames.aether.core.capability.capabilities.lightning.LightningTracker;
import com.gildedgames.aether.core.capability.capabilities.lightning.LightningTrackerProvider;
import com.gildedgames.aether.core.capability.capabilities.lightning.LightningTrackerStorage;
import com.gildedgames.aether.core.capability.capabilities.player.AetherPlayer;
import com.gildedgames.aether.core.capability.capabilities.player.AetherPlayerProvider;
import com.gildedgames.aether.core.capability.capabilities.player.AetherPlayerStorage;
import com.gildedgames.aether.core.capability.interfaces.*;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class AetherCapabilities
{
	@CapabilityInject(ICapeEntity.class)
	public static final Capability<ICapeEntity> CAPE_ENTITY_CAPABILITY = null;

	@CapabilityInject(IAetherPlayer.class)
	public static final Capability<IAetherPlayer> AETHER_PLAYER_CAPABILITY = null;

	@CapabilityInject(IPhoenixArrow.class)
	public static final Capability<IPhoenixArrow> PHOENIX_ARROW_CAPABILITY = null;

	@CapabilityInject(ILightningTracker.class)
	public static final Capability<ILightningTracker> LIGHTNING_TRACKER_CAPABILITY = null;

	@CapabilityInject(IEternalDay.class)
	public static final Capability<IEternalDay> ETERNAL_DAY_CAPABILITY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(ICapeEntity.class, new CapeEntityStorage(), () -> null);
		CapabilityManager.INSTANCE.register(IAetherPlayer.class, new AetherPlayerStorage(), () -> null);
		CapabilityManager.INSTANCE.register(IPhoenixArrow.class, new PhoenixArrowStorage(), () -> null);
		CapabilityManager.INSTANCE.register(ILightningTracker.class, new LightningTrackerStorage(), () -> null);
		CapabilityManager.INSTANCE.register(IEternalDay.class, new EternalDayStorage(), () -> null);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration
	{
		@SubscribeEvent
		public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity) {
				event.addCapability(new ResourceLocation(Aether.MODID, "cape_entity"), new CapeEntityProvider(new CapeEntity((LivingEntity) event.getObject())));
				if (event.getObject() instanceof Player) {
					event.addCapability(new ResourceLocation(Aether.MODID, "aether_player"), new AetherPlayerProvider(new AetherPlayer((Player) event.getObject())));
				}
			}
			if (event.getObject() instanceof AbstractArrow) {
				event.addCapability(new ResourceLocation(Aether.MODID, "phoenix_arrow"), new PhoenixArrowProvider(new PhoenixArrow((AbstractArrow) event.getObject())));
			}
			if (event.getObject() instanceof LightningBolt) {
				event.addCapability(new ResourceLocation(Aether.MODID, "lightning_tracker"), new LightningTrackerProvider(new LightningTracker((LightningBolt) event.getObject())));
			}
		}

		@SubscribeEvent
		public static void attachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {
			if (event.getObject() instanceof ServerLevel) {
				event.addCapability(new ResourceLocation(Aether.MODID, "eternal_day"), new EternalDayProvider(new EternalDay(event.getObject())));
			}
		}
	}
}
