package com.gildedgames.aether.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FreezerParticle extends SpriteTexturedParticle
{
    IAnimatedSprite animatedSprite;
    float snowDigParticleScale;

    public FreezerParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite sprite)
    {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, 1.0F, sprite);
    }

    public FreezerParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, float scale, IAnimatedSprite sprite)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += xSpeed;
        this.motionY += ySpeed;
        this.motionZ += zSpeed;
        float f = 1.0F - (float)(Math.random() * 0.30000001192092896D);
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.snowDigParticleScale = this.particleScale;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.maxAge = (int) ((float) this.maxAge * scale);
        this.animatedSprite = sprite;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        float f = ((float) this.age + partialTicks) / (float) this.age * 32.0F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        this.particleScale = this.snowDigParticleScale * f;
        super.renderParticle(buffer, renderInfo, partialTicks);
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.age++ >= this.maxAge)
        {
            this.setExpired();
        }

        this.selectSpriteWithAge(animatedSprite);

        this.motionY -= 0.03D;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9900000095367432D;
        this.motionY *= 0.9900000095367432D;
        this.motionZ *= 0.9900000095367432D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FreezerParticle freezerParticle = new FreezerParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            freezerParticle.selectSpriteRandomly(this.spriteSet);
            return freezerParticle;
        }
    }
}
