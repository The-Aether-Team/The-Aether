package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class PhoenixParticlePacket extends AetherPacket
{
    private final int entityID;

    public PhoenixParticlePacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
    }

    public static PhoenixParticlePacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        return new PhoenixParticlePacket(entityID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            if (entity != null) {
                world.addParticle(ParticleTypes.FLAME,
                        entity.getX() + (world.getRandom().nextGaussian() / 5.0D),
                        entity.getY() + (world.getRandom().nextGaussian() / 3.0D),
                        entity.getZ() + (world.getRandom().nextGaussian() / 5.0D),
                        0.0D, 0.0D, 0.0D);
            }
        }
    }
}
