package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public class RemountAerbunnyPacket extends AetherPacket
{
    private final int entityID;
    private final int aerbunnyID;

    public RemountAerbunnyPacket(int entityID, int aerbunnyID) {
        this.entityID = entityID;
        this.aerbunnyID = aerbunnyID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.aerbunnyID);
    }

    public static RemountAerbunnyPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int aerbunnyID = buf.readInt();
        return new RemountAerbunnyPacket(entityID, aerbunnyID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level;
            if (world.getEntity(this.entityID) instanceof Player player && world.getEntity(this.aerbunnyID) instanceof AerbunnyEntity aerbunny) {
                aerbunny.startRiding(player);
            }
        }
    }
}
