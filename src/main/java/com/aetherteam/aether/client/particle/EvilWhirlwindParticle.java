package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.entity.monster.EvilWhirlwind;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class EvilWhirlwindParticle extends AbstractWhirlwindParticle<EvilWhirlwind> {
    private final float smokeParticleScale;

    public EvilWhirlwindParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        this(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, 3.5F, sprite);
    }

    public EvilWhirlwindParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, sprite);
        float f = (float) (Math.random() * 0.3);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F;
        this.quadSize *= scale;
        this.smokeParticleScale = this.quadSize;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.lifetime = (int) ((float) this.lifetime * scale);
        if (this.whirlwind != null) {
            this.setPos(this.whirlwind.getX(), this.whirlwind.getY(), this.whirlwind.getZ());
        }
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        float f = ((float) this.age + partialTicks) / (float) this.lifetime * 32.0F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        this.quadSize = this.smokeParticleScale * f;
        super.render(consumer, camera, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
        }
        this.xd *= 0.96;
        this.yd *= 0.96;
        this.zd *= 0.96;
        if (this.onGround) {
            this.xd *= 0.7;
            this.zd *= 0.7;
        }
    }

    @Override
    public double getBaseSpeedModifier() {
        return 0.1;
    }

    @Override
    public Class<EvilWhirlwind> getWhirlwindType() {
        return EvilWhirlwind.class;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EvilWhirlwindParticle particle = new EvilWhirlwindParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet());
            particle.pickSprite(this.spriteSet());
            return particle;
        }
    }
}
