package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetRemedyPacket extends AetherPacket
{
    private final int playerID;
    private final int max, timer;

    public SetRemedyPacket(int playerID, int max, int timer) {
        this.playerID = playerID;
        this.max = max;
        this.timer = timer;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeInt(this.max);
        buf.writeInt(this.timer);
    }

    public static SetRemedyPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        int maxAmount = buf.readInt();
        int timerAmount = buf.readInt();
        return new SetRemedyPacket(playerID, maxAmount, timerAmount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> {
                    aetherPlayer.setRemedyMaximum(this.max);
                    aetherPlayer.setRemedyTimer(this.timer);
                });
            }
        }
    }
}
