package com.aetherteam.aether.entity.monster.dungeon.boss.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SliderNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public SliderNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
        this(mob, targetType, 10, mustSee, false, null);
    }

    public SliderNearestAttackableTargetGoal(Mob mob, Class<T> targetType, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetType, randomInterval, mustSee, mustReach, targetPredicate);
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).ignoreLineOfSight().selector(targetPredicate);
    }

}
