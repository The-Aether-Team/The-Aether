package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.FriendlyByteBuf;

public class PhoenixArrowPacket extends IAetherPacket.AetherPacket
{
    private final int entityID;
    private final boolean isPhoenix;

    public PhoenixArrowPacket(int entityID, boolean isPhoenix) {
        this.entityID = entityID;
        this.isPhoenix = isPhoenix;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.isPhoenix);
    }

    public static PhoenixArrowPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        boolean isPhoenix = buf.readBoolean();
        return new PhoenixArrowPacket(entityID, isPhoenix);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.entityID);
            if (entity instanceof AbstractArrow) {
                IPhoenixArrow.get((AbstractArrow) entity).ifPresent(phoenixArrow -> phoenixArrow.setPhoenixArrow(this.isPhoenix));
            }
        }
    }
}
