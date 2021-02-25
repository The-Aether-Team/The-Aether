package com.gildedgames.aether.network;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.network.packet.ExtendedAttackPacket;
import com.gildedgames.aether.network.packet.JumpPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AetherPacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		new ResourceLocation(Aether.MODID, "main"),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);
	
	private static boolean initialized = false;
	
	public static synchronized void register() {
		if (initialized) {
			return;
		}
		
		int id = 0;
		INSTANCE.registerMessage(id++, JumpPacket.class, JumpPacket::encode, JumpPacket::decode, JumpPacket::handlePacket);
		INSTANCE.registerMessage(id++, ExtendedAttackPacket.class, ExtendedAttackPacket::encode, ExtendedAttackPacket::decode, ExtendedAttackPacket::handlePacket);

		initialized = true;
	}
	
}
