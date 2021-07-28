package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class ResetMaxUpStepPacket extends AetherPacket
{
    private final int entityID;

    public ResetMaxUpStepPacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
    }

    public static ResetMaxUpStepPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        return new ResetMaxUpStepPacket(entityID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.entityID);
            if (entity != null) {
                entity.maxUpStep = 0.6F;
            }
        }
    }
}
