package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SmokeParticlePacket extends AetherPacket
{
    private final BlockPos blockPos;

    public SmokeParticlePacket(BlockPos pos) {
        this.blockPos = pos;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(this.blockPos);
    }

    public static SmokeParticlePacket decode(PacketBuffer buf) {
        BlockPos pos = buf.readBlockPos();
        return new SmokeParticlePacket(pos);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            IWorld world = Minecraft.getInstance().player.level;
            double x = this.blockPos.getX() + 0.5;
            double y = this.blockPos.getY() + 1;
            double z = this.blockPos.getZ() + 0.5;
            for (int i = 0; i < 10; i++) {
                world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.0, 0.0);
            }
            world.playSound(null, this.blockPos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }
}
