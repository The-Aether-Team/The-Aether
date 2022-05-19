package com.gildedgames.aether.common.entity.ai.goal.target;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

public class MostDamageTargetGoal extends TargetGoal {
    public MostDamageTargetGoal(Mob pMob, boolean pMustSee) {
        super(pMob, pMustSee);
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
