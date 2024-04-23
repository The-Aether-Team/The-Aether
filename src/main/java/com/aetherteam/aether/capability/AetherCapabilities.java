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
import com.aetherteam.aether.capability.time.FakeAetherTime;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

public class AetherCapabilities {
	public static final ComponentKey<AetherPlayer> AETHER_PLAYER_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "aether_player"), AetherPlayer.class);
	public static final ComponentKey<MobAccessory> MOB_ACCESSORY_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "mob_accessory"), MobAccessory.class);
	public static final ComponentKey<PhoenixArrow> PHOENIX_ARROW_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "phoenix_arrow"), PhoenixArrow.class);
	public static final ComponentKey<LightningTracker> LIGHTNING_TRACKER_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "lightning_tracker"), LightningTracker.class);
	public static final ComponentKey<DroppedItem> DROPPED_ITEM_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "dropped_item"), DroppedItem.class);
	public static final ComponentKey<AetherTime> AETHER_TIME_CAPABILITY = ComponentRegistry.getOrCreate(new ResourceLocation(Aether.MODID, "aether_time"), AetherTime.class);

	public static class Entity implements EntityComponentInitializer {
		@Override
		public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
			registry.registerForPlayers(AetherCapabilities.AETHER_PLAYER_CAPABILITY, AetherPlayerCapability::new);
			registry.registerFor(Mob.class, AetherCapabilities.MOB_ACCESSORY_CAPABILITY, MobAccessoryCapability::new);
			registry.registerFor(AbstractArrow.class, AetherCapabilities.PHOENIX_ARROW_CAPABILITY, PhoenixArrowCapability::new);
			registry.registerFor(LightningBolt.class, AetherCapabilities.LIGHTNING_TRACKER_CAPABILITY, LightningTrackerCapability::new);
			registry.registerFor(ItemEntity.class, AetherCapabilities.DROPPED_ITEM_CAPABILITY, DroppedItemCapability::new);
		}
	}

	public static class Level implements WorldComponentInitializer {
		@Override
		public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
			registry.register(AetherCapabilities.AETHER_TIME_CAPABILITY, level -> {
				if (level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
					return new AetherTimeCapability(level);
				}
				return new FakeAetherTime();
			});
		}
	}

}
