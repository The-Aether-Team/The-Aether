package com.aetherteam.aether.network.packet.server;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record AerbunnyPuffPacket(int entityID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static AerbunnyPuffPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new AerbunnyPuffPacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.entityID) instanceof Aerbunny aerbunny) {
            aerbunny.puff();
        }
    }
}
