package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.common.entity.passive.Aerbunny;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import static com.gildedgames.aether.core.network.AetherPacket.*;

public class AerbunnyPuffPacket extends AbstractAetherPacket {
    private final int entityID;

    public AerbunnyPuffPacket(int entityID) {
        this.entityID = entityID;
    }

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
