package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.entity.monster.WhirlwindEntity;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EvilWhirlyParticle extends SpriteTexturedParticle {

    float smokeParticleScale;
    WhirlwindEntity whirlwind;
    IAnimatedSprite animatedSprite;

    public EvilWhirlyParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite sprite)
    {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, 3.5F, sprite);
    }

    public EvilWhirlyParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, float scale, IAnimatedSprite sprite)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += xSpeed;
        this.motionY += ySpeed;
        this.motionZ += zSpeed;
        float f = (float)(Math.random() * 0.30000001192092896D);
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
        this.particleScale *= 0.75F;
        this.particleScale *= scale;
        this.smokeParticleScale = this.particleScale;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.maxAge = (int)((float)this.maxAge * scale);
        this.animatedSprite = sprite;
        whirlwind = worldIn.getClosestEntityWithinAABB(WhirlwindEntity.class, EntityPredicate.DEFAULT, null, xCoordIn, yCoordIn, zCoordIn, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1));
        this.setPosition(whirlwind.getPosX(), whirlwind.getPosY(), whirlwind.getPosZ());
    }

    /**
     * Renders the particle
     */
    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        float f = ((float)this.age + partialTicks) / (float)this.maxAge * 32.0F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        this.particleScale = this.smokeParticleScale * f;
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

        if(whirlwind.isAlive()) {
            float f = (float)(whirlwind.getPosX() - this.posX);
            float f1 = (float)(whirlwind.getPosY() - this.posY);
            float f2 = (float)(whirlwind.getPosZ() - this.posZ);
            float d16 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);

            double d18 = this.getBoundingBox().minY - this.posY;
            double d21 = Math.atan2(whirlwind.getPosX() - this.posX, whirlwind.getPosZ() - this.posZ) / 0.01745329424738884D;
            d21 += 160D;
            this.motionX = -Math.cos(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.motionZ = Math.sin(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.motionY = 0.11500000208616257D;
        }
        this.motionY += 0.004D;
        this.move(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }
        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;
        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EvilWhirlyParticle portalparticle = new EvilWhirlyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            portalparticle.selectSpriteRandomly(this.spriteSet);
            return portalparticle;
        }
    }
}
