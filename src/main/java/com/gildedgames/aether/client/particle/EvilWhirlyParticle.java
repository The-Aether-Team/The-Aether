package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.common.entity.monster.EvilWhirlwind;
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

public class EvilWhirlyParticle extends WhirlyParticle<EvilWhirlwind> {

    float smokeParticleScale;

    public EvilWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite)
    {
        this(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, 3.5F, sprite);
    }

    public EvilWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeed, double ySpeed, double zSpeed, float scale, SpriteSet sprite)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed, sprite);
        float f = (float)(Math.random() * 0.30000001192092896D);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F;
        this.quadSize *= scale;
        this.smokeParticleScale = this.quadSize;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.lifetime = (int)((float)this.lifetime * scale);
        if(this.whirlwind != null) {
            this.setPos(this.whirlwind.getX(), this.whirlwind.getY(), this.whirlwind.getZ());
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        float f = ((float)this.age + partialTicks) / (float)this.lifetime * 32.0F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        this.quadSize = this.smokeParticleScale * f;
        super.render(buffer, renderInfo, partialTicks);
    }


    @Override
    public void tick() {
        super.tick();
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
    public double getBaseSpeedModifier() {
        return 0.10000000149011612D;
    }

    @Override
    public Class<EvilWhirlwind> getWhirlwindType() {
        return EvilWhirlwind.class;
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
