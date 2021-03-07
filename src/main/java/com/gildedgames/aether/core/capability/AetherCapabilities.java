package com.gildedgames.aether.core.capability;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.capability.player.IAetherPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class AetherCapabilities {

	@CapabilityInject(IAetherPlayer.class)
	public static final Capability<IAetherPlayer> AETHER_PLAYER_CAPABILITY = null;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IAetherPlayer.class, new AetherPlayerStorage(), () -> null);
	}
	
	@EventBusSubscriber(modid = Aether.MODID)
	public static class Registration {
		
		@SubscribeEvent
		public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity) {
				event.addCapability(new ResourceLocation(Aether.MODID, "aether_player"), new AetherPlayerProvider(new AetherPlayer((PlayerEntity) event.getObject())));
			}
		}
		
	}
	
}
