package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

/**
 * Sets the player's Life Shard data and refreshes the modifier and health values. This is called by {@link com.aetherteam.aether.command.PlayerCapabilityCommand}.
 */
public record HealthResetPacket(int entityID, int value) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "reset_health");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
            var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
            data.setSynched(player.getId(), INBTSynchable.Direction.SERVER, "setLifeShardCount", this.value());
            AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
            if (attribute != null) {
                attribute.removeModifier(data.getLifeShardHealthAttributeModifier().getId());
            }
            player.setHealth(player.getMaxHealth());
        }
    }
}
