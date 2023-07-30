package com.aetherteam.aether.entity.ai.controller;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

/**
 * A move control that allows an entity to choose a path after it has jumped when it is falling.
 */
public class FallingMoveControl extends MoveControl {
    public FallingMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.JUMPING) {
            this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.isOnGround()) {
                this.operation = MoveControl.Operation.WAIT;
            } else {
                this.operation = MoveControl.Operation.MOVE_TO;
            }
        } else {
            super.tick();
        }
    }
}
