package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.entity.ai.navigator.FallPathNavigation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForgeMod;

public abstract class WingedAnimal extends MountableAnimal {
    /**
     * Used for wing animations.
     *
     * @see com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer
     */
    private float wingFold;
    private float wingAngle;

    public WingedAnimal(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /**
     * Navigation for falling entities.
     *
     * @param level The {@link Level}.
     * @return The {@link PathNavigation} class.
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FallPathNavigation(this, level);
    }

    /**
     * Makes this entity fall slowly.
     */
    @Override
    public void tick() {
        super.tick();
        AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1); // Entity isn't allowed to fall too slowly from gravity.
            if (this.getDeltaMovement().y() < fallSpeed && !this.playerTriedToCrouch()) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.hasImpulse = true;
                this.setEntityOnGround(false);
            }
        }
    }

    /**
     * Resets the passenger's fall distance.
     */
    @Override
    public void riderTick() {
        super.riderTick();
        if (this.getControllingPassenger() instanceof Player) {
            this.checkSlowFallDistance();
        }
    }

    /**
     * @return The {@link Float} for the wings' fold amount.
     */
    public float getWingFold() {
        return this.wingFold;
    }

    /**
     * Sets the wings' fold amount.
     *
     * @param wingFold The {@link Float} amount.
     */
    public void setWingFold(float wingFold) {
        this.wingFold = wingFold;
    }

    /**
     * @return The {@link Float} for the wings' angle.
     */
    public float getWingAngle() {
        return this.wingAngle;
    }

    /**
     * Sets the wings' angle
     *
     * @param wingAngle The {@link Float} amount.
     */
    public void setWingAngle(float wingAngle) {
        this.wingAngle = wingAngle;
    }

    /**
     * @return A {@link Float} for the calculated movement speed, both when mounted and not mounted.
     */
    @Override
    public float getFlyingSpeed() {
        if (this.isEffectiveAi() && !this.onGround() && this.getPassengers().isEmpty()) {
            return this.getSpeed() * (0.24F / ((float) Math.pow(0.91F, 3)));
        } else {
            return super.getFlyingSpeed();
        }
    }

    /**
     * @return A {@link Boolean} for whether this entity can perform a boosted jump, depending on whether it is saddled according to {@link MountableAnimal#isSaddled()}.
     */
    @Override
    public boolean canJump() {
        return this.isSaddled();
    }

    /**
     * @return The maximum height from where the entity is allowed to jump (used in pathfinder), as a {@link Integer}.
     */
    @Override
    public int getMaxFallDistance() {
        return this.onGround() ? super.getMaxFallDistance() : 14;
    }
}
