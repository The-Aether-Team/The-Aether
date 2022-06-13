package com.gildedgames.aether.core.network;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.core.network.packet.AetherPlayerSyncPacket;
import com.gildedgames.aether.core.network.packet.AetherRankingsSyncPacket;
import com.gildedgames.aether.core.network.packet.client.*;
import com.gildedgames.aether.core.network.packet.server.*;
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
		// CLIENT
		register(AetherTravelPacket.class, AetherTravelPacket::decode);
		register(BossInfoPacket.Display.class, BossInfoPacket.Display::decode);
		register(BossInfoPacket.Remove.class, BossInfoPacket.Remove::decode);
		register(CloudMinionPacket.class, CloudMinionPacket::decode);
		register(ClientGrabItemPacket.class, ClientGrabItemPacket::decode);
		register(DimensionTypeHolderPacket.class, DimensionTypeHolderPacket::decode);
		register(EternalDayPacket.class, EternalDayPacket::decode);
		register(ExplosionParticlePacket.class, ExplosionParticlePacket::decode);
		register(LeavingAetherPacket.class, LeavingAetherPacket::decode);
		register(MoaInteractPacket.class, MoaInteractPacket::decode);
		register(OpenNpcDialoguePacket.class, OpenNpcDialoguePacket::decode);
		register(PhoenixArrowPacket.class, PhoenixArrowPacket::decode);
		register(PortalTravelSoundPacket.class, PortalTravelSoundPacket::decode);
		register(RemountAerbunnyPacket.class, RemountAerbunnyPacket::decode);
		register(SentryExplosionParticlePacket.class, SentryExplosionParticlePacket::decode);
		register(SetVehiclePacket.class, SetVehiclePacket::decode);
		register(SwetAttackPacket.class, SwetAttackPacket::decode);
		register(SwetDeathParticlePacket.class, SwetDeathParticlePacket::decode);
		register(ZephyrSnowballHitPacket.class, ZephyrSnowballHitPacket::decode);

		// SERVER
		register(AerbunnyPuffPacket.class, AerbunnyPuffPacket::decode);
		register(HittingPacket.class, HittingPacket::decode);
		register(JumpPacket.class, JumpPacket::decode);
		register(LoreExistsPacket.class, LoreExistsPacket::decode);
		register(MilkCowPacket.class, MilkCowPacket::decode);
		register(MovementPacket.class, MovementPacket::decode);
		register(NpcPlayerInteractPacket.class, NpcPlayerInteractPacket::decode);
		register(OpenAccessoriesPacket.class, OpenAccessoriesPacket::decode);
		register(OpenInventoryPacket.class, OpenInventoryPacket::decode);
		register(RankingsForcePacket.class, RankingsForcePacket::decode);
		register(SunAltarUpdatePacket.class, SunAltarUpdatePacket::decode);

		// BOTH
		register(AetherPlayerSyncPacket.class, AetherPlayerSyncPacket::decode);
		register(AetherRankingsSyncPacket.class, AetherRankingsSyncPacket::decode);
	}

	private static <MSG extends AetherPacket> void register(final Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
		INSTANCE.messageBuilder(packet, index++).encoder(AetherPacket::encode).decoder(decoder).consumer(AetherPacket::handle).add();
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
