package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.capability.arrow.PhoenixArrowCapability;
import com.gildedgames.aether.core.capability.arrow.PhoenixArrowProvider;
import com.gildedgames.aether.core.capability.arrow.PhoenixArrow;
import com.gildedgames.aether.core.capability.cape.CapeEntityCapability;
import com.gildedgames.aether.core.capability.cape.CapeEntityProvider;
import com.gildedgames.aether.core.capability.time.AetherTimeCapability;
import com.gildedgames.aether.core.capability.time.AetherTimeProvider;
import com.gildedgames.aether.core.capability.cape.CapeEntity;
import com.gildedgames.aether.core.capability.lightning.LightningTrackerCapability;
import com.gildedgames.aether.core.capability.lightning.LightningTrackerProvider;
import com.gildedgames.aether.core.capability.lightning.LightningTracker;
import com.gildedgames.aether.core.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.core.capability.player.AetherPlayerProvider;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.capability.rankings.AetherRankingsCapability;
import com.gildedgames.aether.core.capability.rankings.AetherRankingsProvider;
import com.gildedgames.aether.core.capability.rankings.AetherRankings;

import com.gildedgames.aether.core.util.LevelUtil;
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
	public static final Capability<CapeEntity> CAPE_ENTITY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<AetherPlayer> AETHER_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<AetherRankings> AETHER_RANKINGS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<AetherTime> AETHER_TIME_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<PhoenixArrow> PHOENIX_ARROW_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<LightningTracker> LIGHTNING_TRACKER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(CapeEntity.class);
		event.register(AetherPlayer.class);
		event.register(AetherRankings.class);
		event.register(AetherTime.class);
		event.register(PhoenixArrow.class);
		event.register(LightningTracker.class);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration {
		@SubscribeEvent
		public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity livingEntity) {
				event.addCapability(new ResourceLocation(Aether.MODID, "cape_entity"), new CapeEntityProvider(new CapeEntityCapability(livingEntity)));
				if (livingEntity instanceof Player player) {
					event.addCapability(new ResourceLocation(Aether.MODID, "aether_player"), new AetherPlayerProvider(new AetherPlayerCapability(player)));
					event.addCapability(new ResourceLocation(Aether.MODID, "aether_rankings"), new AetherRankingsProvider(new AetherRankingsCapability(player)));
				}
			}
			if (event.getObject() instanceof AbstractArrow abstractArrow) {
				event.addCapability(new ResourceLocation(Aether.MODID, "phoenix_arrow"), new PhoenixArrowProvider(new PhoenixArrowCapability(abstractArrow)));
			}
			if (event.getObject() instanceof LightningBolt lightningBolt) {
				event.addCapability(new ResourceLocation(Aether.MODID, "lightning_tracker"), new LightningTrackerProvider(new LightningTrackerCapability(lightningBolt)));
			}
		}

		@SubscribeEvent
		public static void attachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {
			if (LevelUtil.sunSpiritControlsDaycycle(event.getObject())) {
				event.addCapability(new ResourceLocation(Aether.MODID, "aether_time"), new AetherTimeProvider(new AetherTimeCapability(event.getObject())));
			}
		}
	}
}
