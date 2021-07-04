package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetRemedyPacket extends AetherPacket
{
    private final int max, timer;

    public SetRemedyPacket(int max, int timer) {
        this.max = max;
        this.timer = timer;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.max);
        buf.writeInt(this.timer);
    }

    public static SetRemedyPacket decode(PacketBuffer buf) {
        int maxAmount = buf.readInt();
        int timerAmount = buf.readInt();
        return new SetRemedyPacket(maxAmount, timerAmount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> {
                aetherPlayer.setRemedyMaximum(this.max);
                aetherPlayer.setRemedyTimer(this.timer);
            });
        }
    }
}
