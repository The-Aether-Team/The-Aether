package com.aetherteam.aether.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FrozenParticle extends TextureSheetParticle
{
    SpriteSet animatedSprite;
    float snowDigParticleScale;

    public FrozenParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, 1.0F, sprite);
    }

    public FrozenParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed);
        this.xd *= 0.10000000149011612;
        this.yd *= 0.10000000149011612;
        this.zd *= 0.10000000149011612;
        this.xd += xSpeed;
        this.yd += ySpeed;
        this.zd += zSpeed;
        float f = 1.0F - (float)(Math.random() * 0.30000001192092896);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F;
        this.quadSize *= scale;
        this.snowDigParticleScale = this.quadSize;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.lifetime = (int) ((float) this.lifetime * scale);
        this.animatedSprite = sprite;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float f = ((float) this.age + partialTicks) / (float) this.age * 32.0F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        this.quadSize = this.snowDigParticleScale * f;
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime)
        {
            this.remove();
        }

        this.setSpriteFromAge(animatedSprite);

        this.yd -= 0.03;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.9900000095367432;
        this.yd *= 0.9900000095367432;
        this.zd *= 0.9900000095367432;

        if (this.onGround)
        {
            this.xd *= 0.699999988079071;
            this.zd *= 0.699999988079071;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FrozenParticle frozenParticle = new FrozenParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            frozenParticle.pickSprite(this.spriteSet);
            return frozenParticle;
        }
    }
}
