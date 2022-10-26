package com.gildedgames.aether.entity.monster.dungeon;

import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class FireMinion extends Monster {
    public FireMinion(EntityType<? extends FireMinion> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new ContinuousMeleeAttackGoal(this, 1.5, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 40.0);
    }

    @Override
    public void tick() {
        super.tick();
        ParticleOptions particle = ParticleTypes.FLAME;
        if (this.hasCustomName()) {
            String name = this.getName().getContents().toString();
            if (name.equals("JorgeQ") || name.equals("Jorge_SunSpirit")) {
                particle = ParticleTypes.ITEM_SNOWBALL;
            }
        }
        for (int i = 0; i < 1; i++) {
            double d = this.random.nextFloat() - 0.5F;
            double d1 = this.random.nextFloat();
            double d2 = this.random.nextFloat() - 0.5F;
            double x = this.getX() + d * d1;
            double y = this.getBoundingBox().minY + d1 + 0.5;
            double z = this.getZ() + d2 * d1;
            this.level.addParticle(particle, x, y, z, 0.0, -0.075, 0.0);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.getEntity() != null && source.getEntity().getType() == AetherEntityTypes.SUN_SPIRIT.get();
    }
}
