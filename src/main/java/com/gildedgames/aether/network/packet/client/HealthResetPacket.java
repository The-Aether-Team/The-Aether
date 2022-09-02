package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public record HealthResetPacket(int entityID, int value) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.value);
    }

    public static HealthResetPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int value = buf.readInt();
        return new HealthResetPacket(entityID, value);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.entityID) instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                aetherPlayer.setLifeShardCount(this.value);
                AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
                if (attribute != null) {
                    attribute.removeModifier(aetherPlayer.getLifeShardHealthAttributeModifier());
                }
                player.setHealth(player.getMaxHealth());
            });
        }
    }
}
