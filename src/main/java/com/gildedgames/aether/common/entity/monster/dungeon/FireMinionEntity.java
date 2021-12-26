package com.gildedgames.aether.common.entity.monster.dungeon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

public class FireMinionEntity extends Monster
{
    public FireMinionEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
    }
    
//  public FireMinionEntity(World worldIn) {
//      super(AetherEntityTypes.FIRE_MINION.get(), worldIn);
//  }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.5, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.MAX_HEALTH, 40.0);
    }

    @Override
    public void tick() {
        super.tick();

        ParticleOptions particle = ParticleTypes.FLAME;
        if (this.hasCustomName()) {
            String name = ChatFormatting.stripFormatting(this.getName().getString());
            if ("JorgeQ".equals(name) || "Jorge_SunSpirit".equals(name)) {
                particle = ParticleTypes.ITEM_SNOWBALL;
            }
        }

        for (int i = 0; i < 1; i++) {
            double d = random.nextFloat() - 0.5F;
            double d1 = random.nextFloat();
            double d2 = random.nextFloat() - 0.5F;
            double d3 = this.getX() + d*d1;
            double d4 = this.getBoundingBox().minY + d1 + 0.5;
            double d5 = this.getZ() + d2*d1;

            this.level.addParticle(particle, d3, d4, d5, 0.0, -0.075000002980232239, 0.0);
        }
    }
}
