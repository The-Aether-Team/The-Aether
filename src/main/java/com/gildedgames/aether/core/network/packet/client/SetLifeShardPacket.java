package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetLifeShardPacket extends AetherPacket
{
    private final int count;

    public SetLifeShardPacket(int count) {
        this.count = count;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.count);
    }

    public static SetLifeShardPacket decode(PacketBuffer buf) {
        int shardCount = buf.readInt();
        return new SetLifeShardPacket(shardCount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            IAetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> aetherPlayer.setLifeShardCount(this.count));
        }
    }
}
