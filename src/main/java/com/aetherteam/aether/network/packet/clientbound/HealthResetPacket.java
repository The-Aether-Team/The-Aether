package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

/**
 * Sets the player's Life Shard data and refreshes the modifier and health values. This is called by {@link com.aetherteam.aether.command.PlayerCapabilityCommand}.
 */
public record HealthResetPacket(int entityID, int value) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeInt(this.value());
    }

    public static HealthResetPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int value = buf.readInt();
        return new HealthResetPacket(entityID, value);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(this.entityID()) instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                aetherPlayer.setSynched(INBTSynchable.Direction.SERVER, "setLifeShardCount", this.value());
                AttributeInstance attribute = aetherPlayer.getPlayer().getAttribute(Attributes.MAX_HEALTH);
                if (attribute != null) {
                    attribute.removeModifier(aetherPlayer.getLifeShardHealthAttributeModifier());
                }
                aetherPlayer.getPlayer().setHealth(aetherPlayer.getPlayer().getMaxHealth());
            });
        }
    }
}
