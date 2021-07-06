package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class FlameParticlePacket extends AetherPacket
{
    private final int entityID;

    public FlameParticlePacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
    }

    public static FlameParticlePacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        return new FlameParticlePacket(entityID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            if (entity != null) {
                for (int i = 0; i < 20; i++) {
                    double d0 = world.getRandom().nextGaussian() * 0.02;
                    double d1 = world.getRandom().nextGaussian() * 0.02;
                    double d2 = world.getRandom().nextGaussian() * 0.02;
                    double d3 = 5.0;
                    world.addParticle(ParticleTypes.FLAME,
                            entity.getX() + (world.getRandom().nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d0 * d3,
                            entity.getY() + (world.getRandom().nextFloat() * entity.getBbHeight()) - d1 * d3,
                            entity.getZ() + (world.getRandom().nextFloat() * entity.getBbWidth() * 2.0) - entity.getBbWidth() - d2 * d3,
                            d0, d1, d2);
                }
            }
        }
    }
}
