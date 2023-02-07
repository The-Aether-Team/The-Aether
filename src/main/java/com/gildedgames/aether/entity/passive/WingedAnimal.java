package com.gildedgames.aether.entity.passive;

import com.gildedgames.aether.entity.ai.navigator.FallPathNavigation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public abstract class WingedAnimal extends MountableAnimal {
    public float wingFold;
    public float wingAngle;

    public WingedAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Nonnull
    @Override
    protected PathNavigation createNavigation(@Nonnull Level level) {
        return new FallPathNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1);
            if (this.getDeltaMovement().y < fallSpeed && !this.playerTriedToCrouch()) {
                this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                this.hasImpulse = true;
                this.setEntityOnGround(false);
            }
        }
        if (this.getControllingPassenger() instanceof Player) {
            this.resetFallDistance();
        }
    }

    @Override
    public void travel(@Nonnull Vec3 vector3d) {
        float f = this.flyingSpeed;
        if (this.isEffectiveAi() && !this.isOnGround() && this.getPassengers().isEmpty()) {
            this.flyingSpeed = this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
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
