package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetProjectileImpactedPacket extends AetherPacket
{
    private final int max, timer;

    public SetProjectileImpactedPacket(int max, int timer) {
        this.max = max;
        this.timer = timer;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.max);
        buf.writeInt(this.timer);
    }

    public static SetProjectileImpactedPacket decode(PacketBuffer buf) {
        int maxAmount = buf.readInt();
        int timerAmount = buf.readInt();
        return new SetProjectileImpactedPacket(maxAmount, timerAmount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> {
                aetherPlayer.setProjectileImpactedMaximum(this.max);
                aetherPlayer.setProjectileImpactedTimer(this.timer);
            });
        }
    }
}
