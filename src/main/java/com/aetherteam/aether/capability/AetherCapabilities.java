package com.aetherteam.aether.capability;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.capability.accessory.MobAccessory;
import com.aetherteam.aether.capability.accessory.MobAccessoryCapability;
import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import com.aetherteam.aether.capability.arrow.PhoenixArrowCapability;
import com.aetherteam.aether.capability.item.DroppedItem;
import com.aetherteam.aether.capability.item.DroppedItemCapability;
import com.aetherteam.aether.capability.lightning.LightningTracker;
import com.aetherteam.aether.capability.lightning.LightningTrackerCapability;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.capability.time.AetherTimeCapability;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.nitrogen.capability.CapabilityProvider;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
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
	public static final ComponentKey<AetherPlayer> AETHER_PLAYER_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "aether_player"), AetherPlayer.class);
	public static final ComponentKey<MobAccessory> MOB_ACCESSORY_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "mob_accessory"), MobAccessory.class);
	public static final ComponentKey<PhoenixArrow> PHOENIX_ARROW_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "phoenix_arrow"), PhoenixArrow.class);
	public static final ComponentKey<LightningTracker> LIGHTNING_TRACKER_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "lightning_tracker"), LightningTracker.class);
	public static final ComponentKey<DroppedItem> DROPPED_ITEM_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "dropped_item"), DroppedItem.class);
	public static final ComponentKey<AetherTime> AETHER_TIME_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() { });

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(AetherPlayer.class);
		event.register(MobAccessory.class);
		event.register(PhoenixArrow.class);
		event.register(LightningTracker.class);
		event.register(DroppedItem.class);
		event.register(AetherTime.class);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration {
		@SubscribeEvent
		public static void attachEntityCapabilities(EntityComponentFactoryRegistry registry) {
			registry.registerForPlayers(AetherCapabilities.AETHER_PLAYER_CAPABILITY, AetherPlayerCapability::new);
			registry.registerFor(Mob.class, AetherCapabilities.MOB_ACCESSORY_CAPABILITY, MobAccessoryCapability::new);
			registry.registerFor(AbstractArrow.class, AetherCapabilities.PHOENIX_ARROW_CAPABILITY, PhoenixArrowCapability::new);
			registry.registerFor(LightningBolt.class, AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY, LightningTrackerCapability::new);
			registry.registerFor(ItemEntity.class, AetherCapabilities.DROPPED_ITEM_CAPABILITY, DroppedItemCapability::new);
		}

		@SubscribeEvent
		public static void attachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {
			if (event.getObject().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
				event.addCapability(new ResourceLocation(Aether.MODID, "aether_time"), new CapabilityProvider(AetherCapabilities.AETHER_TIME_CAPABILITY, new AetherTimeCapability(event.getObject())));
			}
		}
	}
}
