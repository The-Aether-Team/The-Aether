package com.gildedgames.aether.common.entity.ai.goal.target;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Used by dungeon mobs.
 * This goal allows the mob to aggro onto the greatest threat to it at any time. It targets
 * the entity that has recently dealt the most damage to it.
 */
public class MostDamageTargetGoal extends TargetGoal { //TODO: Finish implementing this!
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
    /** Holds the damage values from all recent attackers. */
    private final Map<LivingEntity, Double> attackers = new HashMap<>();
    /** Store the previous revengeTimer value. */
    private int timestamp;
    /** How long it takes for the mob to forget about any damage it has taken once it disengages from combat. */
    private final int forgetTime;
    /** The current target. */
    private LivingEntity target;

    public MostDamageTargetGoal(Mob pMob, int forgetTime) {
        super(pMob, true);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.forgetTime = forgetTime;
    }

    /**
     * @see HurtByTargetGoal#canUse()
     * This is where we'll set the data in this goal.
     */
    @Override
    public boolean canUse() {
        int i = this.mob.getLastHurtByMobTimestamp();
        LivingEntity lastAttacker = this.mob.getLastHurtByMob();
        if (lastAttacker != null && i != this.timestamp) {

        } else {
            for (Map.Entry<LivingEntity, Double> entry : attackers.entrySet()) {
                LivingEntity attacker = entry.getKey();
                if (!attacker.isAlive()) {
                    attackers.remove(attacker);
                } else {

                }
            }
        }

        return this.canAttack(lastAttacker, HURT_BY_TARGETING);
    }

    @Override
    public void start() {
        this.mob.setTarget(target);
        super.start();
    }

    /**
     * Returns the entity that has dealt the most damage.
     */
    private LivingEntity getStrongestAttacker() {
        Map.Entry<LivingEntity, Double> entry = attackers.entrySet().stream().max(Comparator.comparingDouble(Map.Entry::getValue)).orElse(null);
        if (entry == null) {
            return null;
        } else {
            return entry.getKey();
        }
    }
}
