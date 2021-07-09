package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class DartCountPacket extends AetherPacket
{
    private final int playerID;
    private final int goldenDartCount, poisonDartCount, enchantedDartCount;

    public DartCountPacket(int playerID, int goldenDartCount, int poisonDartCount, int enchantedDartCount) {
        this.playerID = playerID;
        this.goldenDartCount = goldenDartCount;
        this.poisonDartCount = poisonDartCount;
        this.enchantedDartCount = enchantedDartCount;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.playerID);
        buf.writeInt(this.goldenDartCount);
        buf.writeInt(this.poisonDartCount);
        buf.writeInt(this.enchantedDartCount);
    }

    public static DartCountPacket decode(PacketBuffer buf) {
        int playerID = buf.readInt();
        int goldenDartCount = buf.readInt();
        int poisonDartCount = buf.readInt();
        int enchantedDartCount = buf.readInt();
        return new DartCountPacket(playerID, goldenDartCount, poisonDartCount, enchantedDartCount);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Entity entity = Minecraft.getInstance().player.level.getEntity(this.playerID);
            if (entity instanceof PlayerEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> {
                    aetherPlayer.setGoldenDartCount(this.goldenDartCount);
                    aetherPlayer.setPoisonDartCount(this.poisonDartCount);
                    aetherPlayer.setEnchantedDartCount(this.enchantedDartCount);
                });
            }
        }
    }
}
