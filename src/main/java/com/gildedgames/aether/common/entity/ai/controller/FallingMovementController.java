package com.gildedgames.aether.common.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;

public class FallingMovementController extends MovementController
{
    public FallingMovementController(MobEntity mobEntity) {
        super(mobEntity);
    }

    @Override
    public void tick() {
        if (this.operation == MovementController.Action.JUMPING) {
            this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.isOnGround()) {
                this.operation = MovementController.Action.WAIT;
            } else {
                this.operation = MovementController.Action.MOVE_TO;
            }
        } else {
            super.tick();
        }
    }
}
