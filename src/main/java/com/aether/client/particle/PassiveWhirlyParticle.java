package com.aether.client.particle;

import com.aether.entity.monster.WhirlwindEntity;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PassiveWhirlyParticle extends SpriteTexturedParticle {
    WhirlwindEntity whirlwind;
    IAnimatedSprite animatedSprite;
    public PassiveWhirlyParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IAnimatedSprite sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.animatedSprite = sprite;
        whirlwind = worldIn.getClosestEntityWithinAABB(WhirlwindEntity.class, EntityPredicate.DEFAULT, null, xCoordIn, yCoordIn, zCoordIn, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1));
        this.setPosition(whirlwind.getPosX(), whirlwind.getPosY(), whirlwind.getPosZ());
        this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
        this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 0.5F /* * 6.0F + 1.0F*/;
        this.maxAge = (int)(16.0D / ((double)this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
        int color = whirlwind.getColorData();
        this.setColor((((color >> 16) & 0xFF) / 255F), (((color >> 8) & 0xFF) / 255F), ((color & 0xFF) / 255F));
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.age++ >= this.maxAge) {
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
        this.motionX *= 0.8999999761581421D;
        this.motionY *= 0.8999999761581421D;
        this.motionZ *= 0.8999999761581421D;
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
            PassiveWhirlyParticle whirlyParticle = new PassiveWhirlyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            whirlyParticle.selectSpriteRandomly(this.spriteSet);
            return whirlyParticle;
        }
    }
}
