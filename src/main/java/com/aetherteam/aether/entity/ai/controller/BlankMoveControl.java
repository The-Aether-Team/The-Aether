package com.aetherteam.aether.entity.ai.controller;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;

/**
 * An empty move control for entities that have other methods of controlling their movement.
 */
public class BlankMoveControl extends MoveControl {
    public BlankMoveControl(Mob mob) {
        super(mob);
    }

    public void tick() {
    }
}
