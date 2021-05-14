package com.gildedgames.aether.core.network.packet.server;

import java.util.UUID;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class JumpPacket extends AetherPacket
{
	private final UUID playerUUID;
	private final boolean isJumping;
	
	public JumpPacket(UUID uuid, boolean isJumping) {
		this.playerUUID = uuid;
		this.isJumping = isJumping;
	}

	@Override
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

	@Override
	public void execute(PlayerEntity playerEntity) {
		if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
			ServerPlayerEntity player = playerEntity.getServer().getPlayerList().getPlayer(this.playerUUID);
			if (player != null) {
				IAetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.setJumping(this.isJumping));
			}
		}
	}
}
