package com.aetherteam.aether.entity.ai.navigator;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

/**
 * A path navigator that doesn't require the entity to be on the ground to update the path.
 */
public class FallPathNavigation extends GroundPathNavigation {
    public FallPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
