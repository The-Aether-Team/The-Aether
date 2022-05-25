package com.gildedgames.aether.common.entity.ai.goal.target;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Used by powerful dungeon mobs.
 * This goal allows the mob to aggro onto the greatest threat to it at any time. It targets
 * the entity that has recently dealt the most damage to it. Aggro decreases overtime, and when all potential targets
 * no longer have aggro, the mob will return to its other AI tasks.
 */
public class MostDamageTargetGoal extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    /** Holds the aggro values from all recent attackers. */
    private final Object2DoubleMap<LivingEntity> attackers = new Object2DoubleOpenHashMap<>();
    /** Store the previous revengeTimer value. */
    private int lastHurtTimestamp;
    /** The current target. */
    @Nullable
    private LivingEntity primaryTarget;
    /** How much aggro each mob loses per tick. */
    private final float calmDownRate;

    public MostDamageTargetGoal(Mob pMob) {
        this(pMob, 0.05F);
    }

    public MostDamageTargetGoal(Mob pMob, float calmDownRate) {
        super(pMob, true);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.calmDownRate = calmDownRate;
    }

    /**
     * @see HurtByTargetGoal#canUse()
     * This is where we'll set the data in this goal.
     */
    @Override
    public boolean canUse() {
        this.tickAggro();
        if (this.attackers.isEmpty()) {
            return false;
        }
        int mobTimestamp = this.mob.getLastHurtByMobTimestamp();
        LivingEntity lastAttacker = this.mob.getLastHurtByMob();
        if (lastAttacker != null && mobTimestamp != this.lastHurtTimestamp) {
            this.primaryTarget = this.getStrongestAttacker();
            return this.primaryTarget != null;
        } else {
            return false;
        }
    }

    /**
     * Checks if the target mob needs to be reevaluated.
     */
    @Override
    public boolean canContinueToUse() {
        this.tickAggro();
        return this.mob.getLastHurtByMobTimestamp() == this.lastHurtTimestamp && super.canContinueToUse();
    }

    @Override
    public void start() {
        this.mob.setTarget(this.primaryTarget);
        this.lastHurtTimestamp = this.mob.getLastHurtByMobTimestamp();
        super.start();
    }

    /**
     * Handles adding aggro toward a specific entity.
     */
    public void addAggro(LivingEntity attacker, double damage) {
        this.attackers.mergeDouble(attacker, damage, (Double::sum));
    }

    /**
     * Decreases aggro toward all mobs. Removes mobs that either don't have aggro or are dead.
     */
    private void tickAggro() {
        this.attackers.forEach((livingEntity, oldAggro) -> {
            double aggro = oldAggro - calmDownRate;
            if (!livingEntity.isAlive() || aggro <= 0) {
                this.attackers.removeDouble(livingEntity);
            } else {
                this.attackers.put(livingEntity, aggro);
            }
        });
    }

    /**
     * Returns the entity within the targeting range that has dealt the most damage.
     */
    private LivingEntity getStrongestAttacker() {
        Map.Entry<LivingEntity, Double> entry = attackers.object2DoubleEntrySet().stream().filter((entityEntry) ->
                this.canAttack(entityEntry.getKey(), HURT_BY_TARGETING)
        ).max(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return null;
        } else {
            return entry.getKey();
        }
    }
}
