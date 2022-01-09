package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

public class JumpPacket extends AetherPacket
{
	private final int playerID;
	private final boolean isJumping;
	
	public JumpPacket(int playerID, boolean isJumping) {
		this.playerID = playerID;
		this.isJumping = isJumping;
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(this.playerID);
		buf.writeBoolean(this.isJumping);
	}

	public static JumpPacket decode(FriendlyByteBuf buf) {
		int playerID = buf.readInt();
		boolean jumping = buf.readBoolean();
		return new JumpPacket(playerID, jumping);
	}

	@Override
	public void execute(Player playerEntity) {
		if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
			IAetherPlayer.get(serverPlayer).ifPresent(aetherPlayer -> aetherPlayer.setJumping(this.isJumping));
		}
	}
}
