package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record ExplosionParticlePacket(int entityID, int amount) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.amount);
    }

    public static ExplosionParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int amount = buf.readInt();
        return new ExplosionParticlePacket(entityID, amount);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(this.entityID);
            if (entity != null) {
                for (int i = 0; i < this.amount; i++) {
                    EntityUtil.spawnMovementExplosionParticles(entity);
                }
            }
        }
    }
}
