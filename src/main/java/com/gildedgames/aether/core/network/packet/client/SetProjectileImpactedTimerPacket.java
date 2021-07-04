package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetProjectileImpactedTimerPacket extends AetherPacket
{
    private final int timer;

    public SetProjectileImpactedTimerPacket(int timer) {
        this.timer = timer;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.timer);
    }

    public static SetProjectileImpactedTimerPacket decode(PacketBuffer buf) {
        int timerAmount = buf.readInt();
        return new SetProjectileImpactedTimerPacket(timerAmount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> aetherPlayer.setProjectileImpactedTimer(this.timer));
        }
    }
}
