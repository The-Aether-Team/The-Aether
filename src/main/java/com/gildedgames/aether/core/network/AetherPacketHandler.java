package com.gildedgames.aether.core.network;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import com.gildedgames.aether.core.network.packet.client.*;
import com.gildedgames.aether.core.network.packet.server.ExtendedAttackPacket;
import com.gildedgames.aether.core.network.packet.server.JumpPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Function;

public class AetherPacketHandler
{
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(Aether.MODID, "main"),
		() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
	);

	private static int index;
	
	public static synchronized void register() {
		// CLIENT
		register(SetLifeShardPacket.class, SetLifeShardPacket::decode);
		register(SetProjectileImpactedTimerPacket.class, SetProjectileImpactedTimerPacket::decode);
		register(SetRemedyTimerPacket.class, SetRemedyTimerPacket::decode);
		register(SetVehiclePacket.class, SetVehiclePacket::decode);
		register(SmokeParticlePacket.class, SmokeParticlePacket::decode);

		// SERVER
		register(ExtendedAttackPacket.class, ExtendedAttackPacket::decode);
		register(JumpPacket.class, JumpPacket::decode);
	}

	private static <MSG extends AetherPacket> void register(final Class<MSG> packet, Function<PacketBuffer, MSG> decoder)
	{
		INSTANCE.messageBuilder(packet, index++).encoder(AetherPacket::encode).decoder(decoder).consumer(AetherPacket::handle).add();
	}

	public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public static <MSG> void sendToAll(MSG message)
	{
		INSTANCE.send(PacketDistributor.ALL.noArg(), message);
	}

	public static <MSG> void sendToServer(MSG message)
	{
		INSTANCE.sendToServer(message);
	}
}
