package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import static com.gildedgames.aether.core.network.AetherPacket.*;

public class ExplosionParticlePacket extends AbstractAetherPacket {
    private final int entityID;

    public ExplosionParticlePacket(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static ExplosionParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new ExplosionParticlePacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
            if (entity != null) {
                EntityUtil.spawnMovementExplosionParticles(entity);
            }
        }
    }
}
