package com.gildedgames.aether.common.entity.ai.controller;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class FallingMovementController extends MoveControl
{
    public FallingMovementController(Mob mobEntity) {
        super(mobEntity);
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
