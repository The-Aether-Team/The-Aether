package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public class ResetMaxUpStepPacket extends AetherPacket
{
    private final int entityID;

    public ResetMaxUpStepPacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static ResetMaxUpStepPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new ResetMaxUpStepPacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.entityID);
            if (entity != null) {
                entity.maxUpStep = 0.6F;
            }
        }
    }
}
