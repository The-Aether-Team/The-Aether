package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetLifeShardPacket extends AetherPacket
{
    private final int playerID;
    private final int count;

    public SetLifeShardPacket(int playerID, int count) {
        this.playerID = playerID;
        this.count = count;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeInt(this.count);
    }

    public static SetLifeShardPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        int shardCount = buf.readInt();
        return new SetLifeShardPacket(playerID, shardCount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setLifeShardCount(this.count));
            }
        }
    }
}
