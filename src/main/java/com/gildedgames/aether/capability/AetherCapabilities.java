package com.gildedgames.aether.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.capability.arrow.PhoenixArrow;
import com.gildedgames.aether.capability.arrow.PhoenixArrowCapability;
import com.gildedgames.aether.capability.arrow.PhoenixArrowProvider;
import com.gildedgames.aether.capability.lightning.LightningTracker;
import com.gildedgames.aether.capability.lightning.LightningTrackerCapability;
import com.gildedgames.aether.capability.lightning.LightningTrackerProvider;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.capability.player.AetherPlayerProvider;
import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.gildedgames.aether.capability.rankings.AetherRankingsCapability;
import com.gildedgames.aether.capability.rankings.AetherRankingsProvider;
import com.gildedgames.aether.capability.time.AetherTime;
import com.gildedgames.aether.capability.time.AetherTimeCapability;
import com.gildedgames.aether.capability.time.AetherTimeProvider;
import com.gildedgames.aether.data.resources.AetherDimensions;

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
	public static final Capability<AetherPlayer> AETHER_PLAYER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<AetherRankings> AETHER_RANKINGS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<AetherTime> AETHER_TIME_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<PhoenixArrow> PHOENIX_ARROW_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });
	public static final Capability<LightningTracker> LIGHTNING_TRACKER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
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
			if (event.getObject().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
				event.addCapability(new ResourceLocation(Aether.MODID, "aether_time"), new AetherTimeProvider(new AetherTimeCapability(event.getObject())));
			}
		}
	}
}
