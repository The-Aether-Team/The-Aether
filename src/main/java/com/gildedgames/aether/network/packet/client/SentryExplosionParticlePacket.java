package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.entity.monster.dungeon.Sentry;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record SentryExplosionParticlePacket(int entityID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static SentryExplosionParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new SentryExplosionParticlePacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.entityID) instanceof Sentry sentry) {
            for (int i = 0; i < 40; i++) {
                double x = sentry.getX() + ((double) sentry.random.nextFloat() * 0.25);
                double y = sentry.getY() + 0.5;
                double z = sentry.getZ() + ((double) sentry.random.nextFloat() * 0.25);
                float f1 = sentry.random.nextFloat() * 360.0F;
                sentry.level.addParticle(ParticleTypes.POOF, x, y, z, -Math.sin((float) (Math.PI / 180.0F) * f1) * 0.75, 0.125, Math.cos((float) (Math.PI / 180.0F) * f1) * 0.75);
            }
        }
    }
}
