package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public abstract class WingedEntity extends MountableEntity
{
    public float wingFold;
    public float wingAngle;

    public WingedEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new FallPathNavigator(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        double fallSpeed = this.hasEffect(MobEffects.SLOW_FALLING) ? -0.05 : -0.1;
        if (this.getDeltaMovement().y < fallSpeed && !this.playerTriedToCrouch()) {
            this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
            this.hasImpulse = true;
            this.setEntityOnGround(false);
        }
    }

    @Override
    public void travel(@Nonnull Vec3 vector3d) {
        float f = this.flyingSpeed;
        if (this.isEffectiveAi() && !this.isOnGround() && this.getPassengers().isEmpty()) {
            this.flyingSpeed = this.getSpeed() * (0.24F / (0.91F * 0.91F * 0.91F));
            super.travel(vector3d);
            this.flyingSpeed = f;
        } else {
            this.flyingSpeed = f;
            super.travel(vector3d);
        }
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public int getMaxFallDistance() {
        return this.isOnGround() ? super.getMaxFallDistance() : 14;
    }
}
