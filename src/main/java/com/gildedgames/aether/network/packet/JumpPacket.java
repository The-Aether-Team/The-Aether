package com.gildedgames.aether.network.packet;

import java.util.UUID;
import java.util.function.Supplier;

import com.gildedgames.aether.api.AetherAPI;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class JumpPacket {
	private UUID playerUUID;
	private boolean isJumping;
	
	public JumpPacket() {}
	
	public JumpPacket(UUID uuid, boolean isJumping) {
		this.playerUUID = uuid;
		this.isJumping = isJumping;
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeLong(this.playerUUID.getMostSignificantBits())
		   .writeLong(this.playerUUID.getLeastSignificantBits())
		   .writeBoolean(this.isJumping);
	}
	
	public static JumpPacket decode(PacketBuffer buf) {
		UUID uuid = new UUID(buf.readLong(), buf.readLong());
		boolean jumping = buf.readBoolean();
		return new JumpPacket(uuid, jumping);
	}
	
	public void handlePacket(Supplier<NetworkEvent.Context> ctxt) {
		if (ctxt.get().getDirection() != NetworkDirection.PLAY_TO_SERVER) {
			return;
		}
		ctxt.get().enqueueWork(() -> {
			ServerPlayerEntity player = ctxt.get().getSender();
			ServerPlayerEntity target = player.getServer().getPlayerList().getPlayerByUUID(this.playerUUID);
			if (target != null) {
				AetherAPI.get(target).ifPresent(aetherPlayer -> aetherPlayer.setJumping(this.isJumping));
			}
		});
	}
	
}
