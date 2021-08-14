package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;

public class JumpPacket extends AetherPacket
{
	private final int playerID;
	private final boolean isJumping;
	
	public JumpPacket(int playerID, boolean isJumping) {
		this.playerID = playerID;
		this.isJumping = isJumping;
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.playerID);
		buf.writeBoolean(this.isJumping);
	}

	public static JumpPacket decode(PacketBuffer buf) {
		int playerID = buf.readInt();
		boolean jumping = buf.readBoolean();
		return new JumpPacket(playerID, jumping);
	}

	@Override
	public void execute(PlayerEntity playerEntity) {
		if (playerEntity != null && playerEntity.level != null && playerEntity.getServer() != null) {
			Entity entity = playerEntity.level.getEntity(this.playerID);
			if (entity instanceof ServerPlayerEntity) {
				IAetherPlayer.get((ServerPlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setJumping(this.isJumping));
			}
		}
	}
}
