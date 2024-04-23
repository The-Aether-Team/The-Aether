package com.aetherteam.aether.network;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.network.packet.AetherPlayerSyncPacket;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.aether.network.packet.clientbound.*;
import com.aetherteam.aether.network.packet.serverbound.*;
import com.aetherteam.nitrogen.network.BasePacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class AetherPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = new SimpleChannel(new ResourceLocation(Aether.MODID, "main"));

	private static int index;
	
	public static synchronized void register() {
		// CLIENTBOUND
		registerClientbound(AetherTravelPacket.class, AetherTravelPacket::decode);
		registerClientbound(ClientDeveloperGlowPacket.Apply.class, ClientDeveloperGlowPacket.Apply::decode);
		registerClientbound(ClientDeveloperGlowPacket.Remove.class, ClientDeveloperGlowPacket.Remove::decode);
		registerClientbound(ClientDeveloperGlowPacket.Sync.class, ClientDeveloperGlowPacket.Sync::decode);
		registerClientbound(ClientGrabItemPacket.class, ClientGrabItemPacket::decode);
        registerClientbound(ClientHaloPacket.Apply.class, ClientHaloPacket.Apply::decode);
        registerClientbound(ClientHaloPacket.Remove.class, ClientHaloPacket.Remove::decode);
        registerClientbound(ClientHaloPacket.Sync.class, ClientHaloPacket.Sync::decode);
		registerClientbound(ClientMoaSkinPacket.Apply.class, ClientMoaSkinPacket.Apply::decode);
		registerClientbound(ClientMoaSkinPacket.Remove.class, ClientMoaSkinPacket.Remove::decode);
		registerClientbound(ClientMoaSkinPacket.Sync.class, ClientMoaSkinPacket.Sync::decode);
		registerClientbound(CloudMinionPacket.class, CloudMinionPacket::decode);
		registerClientbound(HealthResetPacket.class, HealthResetPacket::decode);
		registerClientbound(LeavingAetherPacket.class, LeavingAetherPacket::decode);
		registerClientbound(MoaInteractPacket.class, MoaInteractPacket::decode);
		registerClientbound(OpenSunAltarPacket.class, OpenSunAltarPacket::decode);
		registerClientbound(PortalTravelSoundPacket.class, PortalTravelSoundPacket::decode);
		registerClientbound(QueenDialoguePacket.class, QueenDialoguePacket::decode);
		registerClientbound(RemountAerbunnyPacket.class, RemountAerbunnyPacket::decode);
		registerClientbound(SetInvisibilityPacket.class, SetInvisibilityPacket::decode);
		registerClientbound(SetVehiclePacket.class, SetVehiclePacket::decode);
		registerClientbound(ToolDebuffPacket.class, ToolDebuffPacket::decode);
		registerClientbound(ZephyrSnowballHitPacket.class, ZephyrSnowballHitPacket::decode);
		registerClientbound(BossInfoPacket.Display.class, BossInfoPacket.Display::decode);
		registerClientbound(BossInfoPacket.Remove.class, BossInfoPacket.Remove::decode);

		// SERVERBOUND
		registerServerbound(AerbunnyPuffPacket.class, AerbunnyPuffPacket::decode);
		registerServerbound(ClearItemPacket.class, ClearItemPacket::decode);
		registerServerbound(HammerProjectileLaunchPacket.class, HammerProjectileLaunchPacket::decode);
		registerServerbound(LoreExistsPacket.class, LoreExistsPacket::decode);
		registerServerbound(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
		registerServerbound(OpenAccessoriesPacket.class, OpenAccessoriesPacket::decode);
		registerServerbound(OpenInventoryPacket.class, OpenInventoryPacket::decode);
		registerServerbound(ServerDeveloperGlowPacket.Apply.class, ServerDeveloperGlowPacket.Apply::decode);
		registerServerbound(ServerDeveloperGlowPacket.Remove.class, ServerDeveloperGlowPacket.Remove::decode);
        registerServerbound(ServerHaloPacket.Apply.class, ServerHaloPacket.Apply::decode);
        registerServerbound(ServerHaloPacket.Remove.class, ServerHaloPacket.Remove::decode);
		registerServerbound(ServerMoaSkinPacket.Apply.class, ServerMoaSkinPacket.Apply::decode);
		registerServerbound(ServerMoaSkinPacket.Remove.class, ServerMoaSkinPacket.Remove::decode);
		registerServerbound(StepHeightPacket.class, StepHeightPacket::decode);
		registerServerbound(SunAltarUpdatePacket.class, SunAltarUpdatePacket::decode);

		// BOTH
		registerBoth(AetherPlayerSyncPacket.class, AetherPlayerSyncPacket::decode);
		registerBoth(AetherTimeSyncPacket.class, AetherTimeSyncPacket::decode);
		registerBoth(PhoenixArrowSyncPacket.class, PhoenixArrowSyncPacket::decode);
	}

	private static <MSG extends BasePacket> void registerClientbound(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
		INSTANCE.registerS2CPacket(packet, index++, decoder);
	}

	private static <MSG extends BasePacket> void registerServerbound(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
		INSTANCE.registerC2SPacket(packet, index++, decoder);
	}

	private static <MSG extends BasePacket> void registerBoth(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
		registerClientbound(packet, decoder);
		registerServerbound(packet, decoder);
	}
}
