package com.gildedgames.aether.common.entity.ai.goal.target;

import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

/**
 * @see net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
 * This class is an alternative to NearestAttackableTargetGoal that allows mobs to specify a tag of entities to target,
 * rather than a specific entity.
 */
public class NearestTaggedTargetGoal extends TargetGoal {
    private final Tag<EntityType<?>> targetTag;
    private final int randomInterval;
    @Nullable
    protected LivingEntity target;
    /** This filter is applied to the Entity search. Only matching entities will be targeted. */
    protected TargetingConditions targetConditions;

    public NearestTaggedTargetGoal(Mob pMob, Tag<EntityType<?>> pTargetTag, boolean pMustSee) {
        this(pMob, pTargetTag, pMustSee, null);
    }

    public NearestTaggedTargetGoal(Mob pMob, Tag<EntityType<?>> pTargetTag, boolean pMustSee, @Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, pMustSee);
        this.targetTag = pTargetTag;
        this.randomInterval = reducedTickDelay(10);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(pTargetPredicate);
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean canUse() {
        if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
            return false;
        } else {
            this.findTarget();
            return this.target != null;
        }
    }

    protected AABB getTargetSearchArea(double pTargetDistance) {
        return this.mob.getBoundingBox().inflate(pTargetDistance, 4.0D, pTargetDistance);
    }

    protected void findTarget() {
        this.target = this.mob.level.getNearestEntity(this.mob.level.getEntities(EntityTypeTest.forClass(LivingEntity.class), this.getTargetSearchArea(this.getFollowDistance()),
                (LivingEntity entity) -> targetTag.contains(entity.getType())), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.target = pTarget;
    }
}
