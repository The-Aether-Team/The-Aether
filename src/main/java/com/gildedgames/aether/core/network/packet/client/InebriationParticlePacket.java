package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class InebriationParticlePacket extends AetherPacket
{
    private final int entityID;

    public InebriationParticlePacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
    }

    public static InebriationParticlePacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        return new InebriationParticlePacket(entityID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            if (entity != null) {
                entity.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, Items.RED_DYE.getDefaultInstance()), entity.getX(), entity.getY() + entity.getBbHeight() * 0.8, entity.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }
}
