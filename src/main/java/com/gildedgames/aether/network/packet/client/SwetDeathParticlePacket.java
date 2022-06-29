package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.entity.monster.Swet;
import com.gildedgames.aether.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class SwetDeathParticlePacket extends AbstractAetherPacket {
    private final int swetID;

    public SwetDeathParticlePacket(int id) {
        this.swetID = id;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.swetID);
    }

    public static SwetDeathParticlePacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        return new SwetDeathParticlePacket(id);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.swetID) instanceof Swet swet) {
            for (int i = 0; i < 10; i++) {
                double f = swet.random.nextFloat() * (float) Math.PI * 2.0F;
                double f1 = swet.random.nextFloat() * swet.swetWidth + 0.25F;
                double f2 = (swet.random.nextFloat() * swet.swetHeight) - (swet.random.nextGaussian() * 0.02 * 10.0);
                double f3 = Mth.sin((float) f) * f1;
                double f4 = Mth.cos((float) f) * f1;
                swet.level.addParticle(ParticleTypes.SPLASH, swet.getX() + f3, swet.getY() + f2, swet.getZ() + f4, f3 * 1.5D + swet.getDeltaMovement().x, 4D, f4 * 1.5D + swet.getDeltaMovement().z);
            }
        }
    }
}
