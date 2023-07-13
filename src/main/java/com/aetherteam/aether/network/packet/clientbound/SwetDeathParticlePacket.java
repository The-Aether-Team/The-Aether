package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public record SwetDeathParticlePacket(int entityID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
    }

    public static SwetDeathParticlePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new SwetDeathParticlePacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.getLevel().getEntity(this.entityID()) instanceof Swet swet) {
            for (int i = 0; i < 10; i++) {
                double f = swet.getRandom().nextFloat() * Math.PI * 2.0F;
                double f1 = swet.getRandom().nextFloat() * swet.swetWidth + 0.25F;
                double f2 = (swet.getRandom().nextFloat() * swet.swetHeight) - (swet.getRandom().nextGaussian() * 0.02 * 10.0);
                double f3 = Mth.sin((float) f) * f1;
                double f4 = Mth.cos((float) f) * f1;
                swet.getLevel().addParticle(ParticleTypes.SPLASH, swet.getX() + f3, swet.getY() + f2, swet.getZ() + f4, f3 * 1.5 + swet.getDeltaMovement().x, 4.0, f4 * 1.5 + swet.getDeltaMovement().z);
            }
        }
    }
}
