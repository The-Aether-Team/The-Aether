package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.common.entity.monster.Whirlwind;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.particle.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EvilWhirlyParticle extends TextureSheetParticle {

    float smokeParticleScale;
    Whirlwind whirlwind;
    SpriteSet animatedSprite;

    public EvilWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite)
    {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, 3.5F, sprite);
    }

    public EvilWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprite)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        this.xd *= 0.10000000149011612D;
        this.yd *= 0.10000000149011612D;
        this.zd *= 0.10000000149011612D;
        this.xd += xSpeed;
        this.yd += ySpeed;
        this.zd += zSpeed;
        float f = (float)(Math.random() * 0.30000001192092896D);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F;
        this.quadSize *= scale;
        this.smokeParticleScale = this.quadSize;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.lifetime = (int)((float)this.lifetime * scale);
        this.animatedSprite = sprite;
        whirlwind = worldIn.getNearestEntity(Whirlwind.class, TargetingConditions.DEFAULT, null, xCoordIn, yCoordIn, zCoordIn, new AABB(x, y, z, x + 1, y + 1, z + 1));
        this.setPos(whirlwind.getX(), whirlwind.getY(), whirlwind.getZ());
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float f = ((float)this.age + partialTicks) / (float)this.lifetime * 32.0F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        this.quadSize = this.smokeParticleScale * f;
        super.render(buffer, renderInfo, partialTicks);
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime)
        {
            this.remove();
        }

        this.setSpriteFromAge(animatedSprite);

        if(whirlwind.isAlive()) {
            float f = (float)(whirlwind.getX() - this.x);
            float f1 = (float)(whirlwind.getY() - this.y);
            float f2 = (float)(whirlwind.getZ() - this.z);
            float d16 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);

            double d18 = this.getBoundingBox().minY - this.y;
            double d21 = Math.atan2(whirlwind.getX() - this.x, whirlwind.getZ() - this.z) / 0.01745329424738884D;
            d21 += 160D;
            this.xd = -Math.cos(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.zd = Math.sin(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D;
            this.yd = 0.11500000208616257D;
        }
        this.yd += 0.004D;
        this.move(this.xd, this.yd, this.zd);
        if (this.y == this.yo) {
            this.xd *= 1.1D;
            this.zd *= 1.1D;
        }
        this.xd *= 0.9599999785423279D;
        this.yd *= 0.9599999785423279D;
        this.zd *= 0.9599999785423279D;
        if (this.onGround) {
            this.xd *= 0.699999988079071D;
            this.zd *= 0.699999988079071D;
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EvilWhirlyParticle portalparticle = new EvilWhirlyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            portalparticle.pickSprite(this.spriteSet);
            return portalparticle;
        }
    }
}
