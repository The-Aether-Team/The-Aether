package com.aetherteam.aether.network.packet.client;

import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record InvisibilityParticlePacket(int entityID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static InvisibilityParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new InvisibilityParticlePacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
            if (entity != null) {
                int i = MobEffects.INVISIBILITY.getColor();
                if (i > 0) {
                    double d0 = (double)(i >> 16 & 255) / 255.0D;
                    double d1 = (double)(i >> 8 & 255) / 255.0D;
                    double d2 = (double)(i & 255) / 255.0D;
                    entity.getLevel().addParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT, entity.getRandomX(0.5D), entity.getRandomY(), entity.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        }
    }
}
