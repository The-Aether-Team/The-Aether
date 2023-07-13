package com.aetherteam.aether.client.particle;

import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

public abstract class WhirlyParticle<T extends AbstractWhirlwind> extends TextureSheetParticle {
    protected static final TargetingConditions TARGET_CONDITION = TargetingConditions.forNonCombat();
    protected AbstractWhirlwind whirlwind;
    protected SpriteSet animatedSprite;

    public WhirlyParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        this.animatedSprite = sprite;
        this.whirlwind = level.getNearestEntity(this.getWhirlwindType(), TARGET_CONDITION, null, xCoord, yCoord, zCoord, new AABB(this.x, this.y, this.z, this.x + 1, this.y + 1, this.z + 1));
        if (this.whirlwind != null) {
            this.setPos(this.whirlwind.getX(), this.whirlwind.getY(), this.whirlwind.getZ());
            this.xd = xSpeed + (Math.random() * 2.0 - 1.0) * this.getBaseSpeedModifier();
            this.yd = ySpeed + (Math.random() * 2.0 - 1.0) * this.getBaseSpeedModifier();
            this.zd = zSpeed + (Math.random() * 2.0 - 1.0) * this.getBaseSpeedModifier();
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
        this.setSpriteFromAge(this.animatedSprite);
        if (this.whirlwind != null && this.whirlwind.isAlive()) {
            float x = (float) (this.whirlwind.getX() - this.x);
            float y = (float) (this.whirlwind.getY() - this.y);
            float z = (float) (this.whirlwind.getZ() - this.z);
            float d1 = Mth.sqrt(x * x + y * y + z * z);

            double minY = this.getBoundingBox().minY - this.y;
            double d2 = Math.atan2(this.whirlwind.getX() - this.x, this.whirlwind.getZ() - this.z) / 0.01745329424738884;
            d2 += 160.0;
            this.xd = -Math.cos(0.01745329424738884 * d2) * (d1 * 2.5 - minY) * 0.10000000149011612;
            this.zd = Math.sin(0.01745329424738884 * d2) * (d1 * 2.5 - minY) * 0.10000000149011612;
            this.yd = 0.11500000208616257;
        }
        this.yd += 0.004;
        this.move(this.xd, this.yd, this.zd);
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    protected abstract double getBaseSpeedModifier();

    public abstract Class<T> getWhirlwindType();
}
