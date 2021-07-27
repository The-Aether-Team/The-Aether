package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class CloudParticlePacket extends AetherPacket
{
    private final int entityID;

    public CloudParticlePacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
    }

    public static CloudParticlePacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        return new CloudParticlePacket(entityID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            if (entity != null) {
                for (int i = 0; i < 20; ++i) {
                    double d0 = (world.getRandom().nextFloat() - 0.5F) * 0.5D;
                    double d1 = (world.getRandom().nextFloat() - 0.5F) * 0.5D;
                    double d2 = (world.getRandom().nextFloat() - 0.5F) * 0.5D;
                    world.addParticle(AetherParticleTypes.FREEZER.get(), entity.getX(), entity.getY(), entity.getZ(), d0 * 0.5D, d1 * 0.5D, d2 * 0.5D);
                }
            }
        }
    }
}
