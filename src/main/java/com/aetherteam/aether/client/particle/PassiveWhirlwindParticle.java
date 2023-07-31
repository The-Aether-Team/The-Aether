package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.entity.monster.PassiveWhirlwind;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class PassiveWhirlwindParticle extends AbstractWhirlwindParticle<PassiveWhirlwind> {
    public PassiveWhirlwindParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
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
        this.xd *= 0.9;
        this.yd *= 0.9;
        this.zd *= 0.9;
        if (this.onGround) {
            this.xd *= 0.7;
            this.zd *= 0.7;
        }
    }

    @Override
    protected double getBaseSpeedModifier() {
        return 0.05;
    }

    @Override
    public Class<PassiveWhirlwind> getWhirlwindType() {
        return PassiveWhirlwind.class;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PassiveWhirlwindParticle particle = new PassiveWhirlwindParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet());
            particle.pickSprite(this.spriteSet());
            return particle;
        }
    }
}
