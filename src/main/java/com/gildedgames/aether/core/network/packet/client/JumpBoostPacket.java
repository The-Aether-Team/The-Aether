package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class JumpBoostPacket extends AetherPacket
{
    private final int playerID;
    private final boolean boost;

    public JumpBoostPacket(int playerID, boolean boost) {
        this.playerID = playerID;
        this.boost = boost;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.boost);
    }

    public static JumpBoostPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        boolean boost = buf.readBoolean();
        return new JumpBoostPacket(playerID, boost);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setJumpBoost(this.boost));
            }
        }
    }
}
