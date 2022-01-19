package com.gildedgames.aether.common.entity.passive;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class WingedEntity extends MountableEntity
{
    public float wingFold;
    public float wingAngle;

    public WingedEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getDeltaMovement().y < -0.1 && !this.playerTriedToCrouch()) {
            this.setDeltaMovement(this.getDeltaMovement().x, -0.1, this.getDeltaMovement().z);
        }
    }

    @Override
    public void travel(Vec3 vector3d) {
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
}
