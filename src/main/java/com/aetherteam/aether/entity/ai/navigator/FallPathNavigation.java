package com.aetherteam.aether.entity.ai.navigator;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;

public class FallPathNavigation extends GroundPathNavigation {
    public FallPathNavigation(Mob mobEntity, Level world) {
        super(mobEntity, world);
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }
}
