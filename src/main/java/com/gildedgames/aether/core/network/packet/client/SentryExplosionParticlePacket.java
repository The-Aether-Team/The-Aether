package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.monster.dungeon.Sentry;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class SentryExplosionParticlePacket extends AbstractAetherPacket {
    private final int sentryID;

    public SentryExplosionParticlePacket(int id) {
        this.sentryID = id;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.sentryID);
    }

    public static SentryExplosionParticlePacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new SentryExplosionParticlePacket(id);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.sentryID) instanceof Sentry sentry) {
            for (int i = 0; i < 40; i++) {
                double x = sentry.getX() + ((double) sentry.random.nextFloat() * 0.25);
                double y = sentry.getY() + 0.5;
                double z = sentry.getZ() + ((double) sentry.random.nextFloat() * 0.25);
                float f1 = sentry.random.nextFloat() * 360.0F;
                sentry.level.addParticle(ParticleTypes.POOF, x, y, z, -Math.sin(0.01745329F * f1) * 0.75, 0.125, Math.cos(0.01745329F * f1) * 0.75);
            }
        }
    }
}
