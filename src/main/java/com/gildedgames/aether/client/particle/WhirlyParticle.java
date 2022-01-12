package com.gildedgames.aether.client.particle;

import com.gildedgames.aether.common.entity.monster.Whirlwind;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

public abstract class WhirlyParticle<T extends Whirlwind> extends TextureSheetParticle {
    protected static final TargetingConditions TARGET_CONDITION = TargetingConditions.forNonCombat();
    protected Whirlwind whirlwind;
    protected SpriteSet animatedSprite;
    public WhirlyParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, SpriteSet sprite) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.animatedSprite = sprite;
        whirlwind = worldIn.getNearestEntity(this.getWhirlwindType(), TARGET_CONDITION, null, xCoordIn, yCoordIn, zCoordIn, new AABB(x, y, z, x + 1, y + 1, z + 1));
        if(whirlwind != null) {
            this.setPos(whirlwind.getX(), whirlwind.getY(), whirlwind.getZ());
            this.xd = xSpeedIn + (Math.random() * 2.0D - 1.0D) * this.getBaseSpeedModifier();
            this.yd = ySpeedIn + (Math.random() * 2.0D - 1.0D) * this.getBaseSpeedModifier();
            this.zd = zSpeedIn + (Math.random() * 2.0D - 1.0D) * this.getBaseSpeedModifier();
        }
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        this.setSpriteFromAge(animatedSprite);

        if(whirlwind != null && whirlwind.isAlive()) {
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
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    protected abstract double getBaseSpeedModifier();

    public abstract Class<T> getWhirlwindType();
}
