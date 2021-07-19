package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class EnchantedDartCountPacket extends AetherPacket
{
    private final int playerID;
    private final int enchantedDartCount;

    public EnchantedDartCountPacket(int playerID, int enchantedDartCount) {
        this.playerID = playerID;
        this.enchantedDartCount = enchantedDartCount;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeInt(this.enchantedDartCount);
    }

    public static EnchantedDartCountPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        int enchantedDartCount = buf.readInt();
        return new EnchantedDartCountPacket(playerID, enchantedDartCount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> aetherPlayer.setEnchantedDartCount(this.enchantedDartCount));
            }
        }
    }
}
