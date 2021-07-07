package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.network.PacketBuffer;

public class PhoenixArrowPacket extends IAetherPacket.AetherPacket
{
    private final int entityID;
    private final boolean isPhoenix;

    public PhoenixArrowPacket(int entityID, boolean isPhoenix) {
        this.entityID = entityID;
        this.isPhoenix = isPhoenix;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.isPhoenix);
    }

    public static PhoenixArrowPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        boolean isPhoenix = buf.readBoolean();
        return new PhoenixArrowPacket(entityID, isPhoenix);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.entityID);
            if (entity instanceof AbstractArrowEntity) {
                IPhoenixArrow.get((AbstractArrowEntity) entity).ifPresent(phoenixArrow -> phoenixArrow.setPhoenixArrow(this.isPhoenix));
            }
        }
    }
}
