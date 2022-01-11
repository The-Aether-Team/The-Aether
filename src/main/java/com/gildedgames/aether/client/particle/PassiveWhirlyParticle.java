package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.common.entity.monster.PassiveWhirlwind;
import com.gildedgames.aether.common.entity.monster.Whirlwind;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PassiveWhirlyParticle extends WhirlyParticle<PassiveWhirlwind> {
    public PassiveWhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, SpriteSet sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, sprite);
        if(this.whirlwind != null) {
            this.quadSize = this.random.nextFloat() * this.random.nextFloat() * 0.5F /* * 6.0F + 1.0F*/;
            this.lifetime = (int) (16.0D / ((double) this.random.nextFloat() * 0.8D + 0.2D)) + 2;
            int color = whirlwind.getColorData();
            this.setColor((((color >> 16) & 0xFF) / 255F), (((color >> 8) & 0xFF) / 255F), ((color & 0xFF) / 255F));
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.xd *= 0.8999999761581421D;
        this.yd *= 0.8999999761581421D;
        this.zd *= 0.8999999761581421D;
        if (this.onGround) {
            this.xd *= 0.699999988079071D;
            this.zd *= 0.699999988079071D;
        }
    }

    @Override
    protected double getBaseSpeedModifier() {
        return 0.05000000074505806D;
    }

    @Override
    public Class<PassiveWhirlwind> getWhirlwindType() {
        return PassiveWhirlwind.class;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            PassiveWhirlyParticle whirlyParticle = new PassiveWhirlyParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
            whirlyParticle.pickSprite(this.spriteSet);
            return whirlyParticle;
        }
    }
}
