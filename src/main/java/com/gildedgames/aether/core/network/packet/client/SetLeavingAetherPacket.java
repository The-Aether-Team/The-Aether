package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class SetLeavingAetherPacket extends AetherPacket
{
    private final int playerID;
    private final boolean leavingAether;

    public SetLeavingAetherPacket(int playerID, boolean leavingAether) {
        this.playerID = playerID;
        this.leavingAether = leavingAether;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.leavingAether);
    }

    public static SetLeavingAetherPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        boolean leavingAether = buf.readBoolean();
        return new SetLeavingAetherPacket(playerID, leavingAether);
    }

    @Override
    public void execute(PlayerEntity player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setLeavingAether(this.leavingAether));
            }
        }
    }
}
