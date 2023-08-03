package com.aetherteam.aether.entity.monster.dungeon;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;

public class FireMinion extends Monster {
    public FireMinion(EntityType<? extends FireMinion> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new ContinuousMeleeAttackGoal(this, 1.5, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.MAX_HEALTH, 40.0);
    }

    /**
     * Handles particle spawning for Fire Minions.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.getLevel().isClientSide()) {
            ParticleOptions particle = ParticleTypes.FLAME;
            if (this.hasCustomName()) {
                String name = this.getName().getString();
                if (name.equals("JorgeQ") || name.equals("Jorge_SunSpirit")) { // Frozen Fire Minion Easter Egg.
                    particle = ParticleTypes.ITEM_SNOWBALL;
                }
            }
            for (int i = 0; i < 1; i++) {
                double d = this.getRandom().nextFloat() - 0.5F;
                double d1 = this.getRandom().nextFloat();
                double d2 = this.getRandom().nextFloat() - 0.5F;
                double x = this.getX() + d * d1;
                double y = this.getBoundingBox().minY + d1 + 0.5;
                double z = this.getZ() + d2 * d1;
                this.getLevel().addParticle(particle, x, y, z, 0.0, -0.075, 0.0);
            }
        }
    }

    /**
     * Increases damage from Snowballs.
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof Snowball) {
            amount += 3;
        }
        return super.hurt(source, amount);
    }

    /**
     * Prevents Fire Minions from being damaged by the Sun Spirit.
     * @param source The {@link DamageSource}.
     * @return Whether the Fire Minion is invulnerable to the damage, as a {@link Boolean}.
     */
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.getEntity() != null && source.getEntity().getType() == AetherEntityTypes.SUN_SPIRIT.get();
    }
}
