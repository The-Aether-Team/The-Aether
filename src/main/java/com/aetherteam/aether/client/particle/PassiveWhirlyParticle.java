package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.entity.monster.PassiveWhirlwind;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class PassiveWhirlyParticle extends WhirlyParticle<PassiveWhirlwind> {
    public PassiveWhirlyParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, sprite);
        if (this.whirlwind != null) {
            this.quadSize = this.random.nextFloat() * this.random.nextFloat() * 0.5F;
            this.lifetime = (int) (16.0 / ((double) this.random.nextFloat() * 0.8 + 0.2)) + 2;
            int color = this.whirlwind.getColorData();
            float red = ((color >> 16) & 0xFF) / 255.0F;
            float green = ((color >> 8) & 0xFF) / 255.0F;
            float blue = (color & 0xFF) / 255.0F;
            this.setColor(red, green, blue);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.8999999761581421;
        this.yd *= 0.8999999761581421;
        this.zd *= 0.8999999761581421;
        if (this.onGround) {
            this.xd *= 0.699999988079071;
            this.zd *= 0.699999988079071;
        }
    }

    @Override
    protected double getBaseSpeedModifier() {
        return 0.05000000074505806;
    }

    @Override
    public Class<PassiveWhirlwind> getWhirlwindType() {
        return PassiveWhirlwind.class;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(@Nonnull SimpleParticleType particleType, @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PassiveWhirlyParticle passiveWhirlyParticle = new PassiveWhirlyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            passiveWhirlyParticle.pickSprite(this.spriteSet);
            return passiveWhirlyParticle;
        }
    }
}
