package com.aetherteam.aether.network;

import com.aetherteam.aether.Aether;

import com.aetherteam.aether.network.packet.AetherPlayerSyncPacket;
import com.aetherteam.aether.network.packet.clientbound.*;
import com.aetherteam.aether.network.packet.serverbound.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class AetherPacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(Aether.MODID, "main"),
		() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
	);

	private static int index;
	
	public static synchronized void register() {
		// CLIENTBOUND
		register(AetherTravelPacket.class, AetherTravelPacket::decode);
		register(BossInfoPacket.Display.class, BossInfoPacket.Display::decode);
		register(BossInfoPacket.Remove.class, BossInfoPacket.Remove::decode);
		register(CloudMinionPacket.class, CloudMinionPacket::decode);
		register(ClientDeveloperGlowPacket.Apply.class, ClientDeveloperGlowPacket.Apply::decode);
		register(ClientDeveloperGlowPacket.Remove.class, ClientDeveloperGlowPacket.Remove::decode);
		register(ClientDeveloperGlowPacket.Sync.class, ClientDeveloperGlowPacket.Sync::decode);
		register(ClientGrabItemPacket.class, ClientGrabItemPacket::decode);
        register(ClientHaloPacket.Apply.class, ClientHaloPacket.Apply::decode);
        register(ClientHaloPacket.Remove.class, ClientHaloPacket.Remove::decode);
        register(ClientHaloPacket.Sync.class, ClientHaloPacket.Sync::decode);
		register(ClientMoaSkinPacket.Apply.class, ClientMoaSkinPacket.Apply::decode);
		register(ClientMoaSkinPacket.Remove.class, ClientMoaSkinPacket.Remove::decode);
		register(ClientMoaSkinPacket.Sync.class, ClientMoaSkinPacket.Sync::decode);
		register(EternalDayPacket.class, EternalDayPacket::decode);
		register(ExplosionParticlePacket.class, ExplosionParticlePacket::decode);
		register(HealthResetPacket.class, HealthResetPacket::decode);
		register(LeavingAetherPacket.class, LeavingAetherPacket::decode);
		register(MoaInteractPacket.class, MoaInteractPacket::decode);
		register(OpenNpcDialoguePacket.class, OpenNpcDialoguePacket::decode);
		register(OpenSunAltarPacket.class, OpenSunAltarPacket::decode);
		register(PhoenixArrowPacket.class, PhoenixArrowPacket::decode);
		register(PortalTravelSoundPacket.class, PortalTravelSoundPacket::decode);
		register(RemountAerbunnyPacket.class, RemountAerbunnyPacket::decode);
		register(SetVehiclePacket.class, SetVehiclePacket::decode);
		register(SwetAttackPacket.class, SwetAttackPacket::decode);
		register(SwetDeathParticlePacket.class, SwetDeathParticlePacket::decode);
		register(ToolDebuffPacket.class, ToolDebuffPacket::decode);
		register(ZephyrSnowballHitPacket.class, ZephyrSnowballHitPacket::decode);

		// SERVERBOUND
		register(AerbunnyPuffPacket.class, AerbunnyPuffPacket::decode);
		register(ClearItemPacket.class, ClearItemPacket::decode);
		register(LoreExistsPacket.class, LoreExistsPacket::decode);
		register(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
		register(OpenAccessoriesPacket.class, OpenAccessoriesPacket::decode);
		register(OpenInventoryPacket.class, OpenInventoryPacket::decode);
		register(ServerDeveloperGlowPacket.Apply.class, ServerDeveloperGlowPacket.Apply::decode);
		register(ServerDeveloperGlowPacket.Remove.class, ServerDeveloperGlowPacket.Remove::decode);
        register(ServerHaloPacket.Apply.class, ServerHaloPacket.Apply::decode);
        register(ServerHaloPacket.Remove.class, ServerHaloPacket.Remove::decode);
		register(ServerMoaSkinPacket.Apply.class, ServerMoaSkinPacket.Apply::decode);
		register(ServerMoaSkinPacket.Remove.class, ServerMoaSkinPacket.Remove::decode);
		register(StepHeightPacket.class, StepHeightPacket::decode);
		register(SunAltarUpdatePacket.class, SunAltarUpdatePacket::decode);

		// BOTH
		register(AetherPlayerSyncPacket.class, AetherPlayerSyncPacket::decode);
	}

	private static <MSG extends AetherPacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
		INSTANCE.messageBuilder(packet, index++).encoder(AetherPacket::encode).decoder(decoder).consumerMainThread(AetherPacket::handle).add();
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public static <MSG> void sendToNear(MSG message, double x, double y, double z, double radius, ResourceKey<Level> dimension) {
		INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(x, y, z, radius, dimension)), message);
	}

	public static <MSG> void sendToAll(MSG message) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}

	public static <MSG> void sendToServer(MSG message)
	{
		INSTANCE.sendToServer(message);
	}

	public static <MSG> void sendToDimension(MSG message, ResourceKey<Level> dimension) {
		INSTANCE.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
	}
}
