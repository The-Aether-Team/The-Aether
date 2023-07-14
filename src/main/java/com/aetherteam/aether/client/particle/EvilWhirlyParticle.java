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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class EvilWhirlyParticle extends WhirlyParticle<EvilWhirlwind> {
    float smokeParticleScale;

    public EvilWhirlyParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        this(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, 3.5F, sprite);
    }

    public EvilWhirlyParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, sprite);
        float f = (float) (Math.random() * 0.30000001192092896);
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
    public void render(@Nonnull VertexConsumer consumer, @Nonnull Camera camera, float partialTicks) {
        float f = ((float)this.age + partialTicks) / (float)this.lifetime * 32.0F;
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
        this.xd *= 0.9599999785423279;
        this.yd *= 0.9599999785423279;
        this.zd *= 0.9599999785423279;
        if (this.onGround) {
            this.xd *= 0.699999988079071;
            this.zd *= 0.699999988079071;
        }
    }

    @Override
    public double getBaseSpeedModifier() {
        return 0.10000000149011612;
    }

    @Override
    public Class<EvilWhirlwind> getWhirlwindType() {
        return EvilWhirlwind.class;
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
            EvilWhirlyParticle evilWhirlyParticle = new EvilWhirlyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            evilWhirlyParticle.pickSprite(this.spriteSet);
            return evilWhirlyParticle;
        }
    }
}
