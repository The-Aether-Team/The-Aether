package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class WingedAnimal extends MountableAnimal {
    public float wingFold;
    public float wingAngle;

    public WingedAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

   
    @Override
    protected PathNavigation createNavigation(Level level) {
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
    }

    @Override
    public void riderTick() {
        super.riderTick();
        if (this.getControllingPassenger() instanceof Player) {
            this.checkSlowFallDistance();
        }
    }

    @Override
    public float getFlyingSpeed() {
        if (this.isEffectiveAi() && !this.isOnGround() && this.getPassengers().isEmpty()) {
            return this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
        } else {
            return super.getFlyingSpeed();
        }
    }

    @Override
    public boolean canJump() {
        return this.isSaddled();
    }

    @Override
    public int getMaxFallDistance() {
        return this.isOnGround() ? super.getMaxFallDistance() : 14;
    }
}
