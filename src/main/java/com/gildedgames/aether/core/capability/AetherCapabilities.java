package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.capability.capabilities.arrow.PhoenixArrow;
import com.gildedgames.aether.core.capability.capabilities.arrow.PhoenixArrowProvider;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntity;
import com.gildedgames.aether.core.capability.capabilities.cape.CapeEntityProvider;
import com.gildedgames.aether.core.capability.capabilities.aether_time.AetherTime;
import com.gildedgames.aether.core.capability.capabilities.aether_time.AetherTimeProvider;
import com.gildedgames.aether.core.capability.capabilities.lightning.LightningTracker;
import com.gildedgames.aether.core.capability.capabilities.lightning.LightningTrackerProvider;
import com.gildedgames.aether.core.capability.capabilities.player.AetherPlayer;
import com.gildedgames.aether.core.capability.capabilities.player.AetherPlayerProvider;
import com.gildedgames.aether.core.capability.capabilities.rankings.AetherRankings;
import com.gildedgames.aether.core.capability.capabilities.rankings.AetherRankingsProvider;
import com.gildedgames.aether.core.capability.interfaces.*;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherCapabilities {
	public static final Capability<ICapeEntity> CAPE_ENTITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<IAetherPlayer> AETHER_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<IAetherRankings> AETHER_RANKINGS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<IAetherTime> AETHER_TIME_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<IPhoenixArrow> PHOENIX_ARROW_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<ILightningTracker> LIGHTNING_TRACKER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(ICapeEntity.class);
		event.register(IAetherPlayer.class);
		event.register(IAetherRankings.class);
		event.register(IAetherTime.class);
		event.register(IPhoenixArrow.class);
		event.register(ILightningTracker.class);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration {
		@SubscribeEvent
		public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity livingEntity) {
				event.addCapability(new ResourceLocation(Aether.MODID, "cape_entity"), new CapeEntityProvider(new CapeEntity(livingEntity)));
				if (livingEntity instanceof Player player) {
					event.addCapability(new ResourceLocation(Aether.MODID, "aether_player"), new AetherPlayerProvider(new AetherPlayer(player)));
					event.addCapability(new ResourceLocation(Aether.MODID, "aether_rankings"), new AetherRankingsProvider(new AetherRankings(player)));
				}
			}
			if (event.getObject() instanceof AbstractArrow abstractArrow) {
				event.addCapability(new ResourceLocation(Aether.MODID, "phoenix_arrow"), new PhoenixArrowProvider(new PhoenixArrow(abstractArrow)));
			}
			if (event.getObject() instanceof LightningBolt lightningBolt) {
				event.addCapability(new ResourceLocation(Aether.MODID, "lightning_tracker"), new LightningTrackerProvider(new LightningTracker(lightningBolt)));
			}
		}

		@SubscribeEvent
		public static void attachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {
			if (event.getObject().dimension().location() == AetherDimensions.AETHER_WORLD.location()) {
				event.addCapability(new ResourceLocation(Aether.MODID, "aether_time"), new AetherTimeProvider(new AetherTime(event.getObject())));
			}
		}
	}
}
