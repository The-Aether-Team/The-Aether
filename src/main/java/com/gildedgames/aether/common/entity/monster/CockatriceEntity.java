package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.entity.projectile.PoisonNeedleEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

import java.util.Random;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class CockatriceEntity extends Monster implements RangedAttackMob {

    public float wingRotation, prevWingRotation, destPos, prevDestPos;
    protected int ticksOffGround, ticksUntilFlap, secsUntilFlying;

    public CockatriceEntity(EntityType<? extends CockatriceEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public CockatriceEntity(Level worldIn) {
        this(AetherEntityTypes.COCKATRICE.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2,  new RangedAttackGoal(this, 1.0, 60, 5));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMonsterAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28000000417232513D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    //@Override
    //public void move(MoverType typeIn, Vec3d pos) {
        //super.move(typeIn, new Vec3d(0, pos.getY(), 0));
    //}


    public static boolean canCockatriceSpawn(EntityType<? extends CockatriceEntity> type, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(45) == 0 && checkMonsterSpawnRules(type, worldIn, reason, pos, randomIn); //TODO: change the bounds of nextInt to a config value.
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        PoisonNeedleEntity needle = new PoisonNeedleEntity(this.level, this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY + (double)(target.getBbHeight() / 3.0F) - needle.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
        needle.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(AetherSoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.level.addFreshEntity(needle);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    @SuppressWarnings("unused")
    @Override
    public void tick() {
        super.tick();

        if (this.jumping) {
            this.push(0.0, 0.05, 0.0);
        }

        updateWingRotation: {
            if (!this.onGround) {
                if (this.ticksUntilFlap == 0) {
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundSource.NEUTRAL, 0.15F, Mth.clamp(this.random.nextFloat(), 0.7F, 1.0F) + Mth.clamp(this.random.nextFloat(), 0.0F, 0.3F));
                    this.ticksUntilFlap = 8;
                }
                else {
                    --this.ticksUntilFlap;
                }
            }

            this.prevWingRotation = this.wingRotation;
            this.prevDestPos = this.destPos;

            if (this.onGround) {
                this.destPos = 0.0F;
            }
            else {
                this.destPos += 0.2;
                this.destPos = Mth.clamp(this.destPos, 0.01F, 1.0F);
            }

            this.wingRotation += 1.233F;
        }

        fall: {
//			boolean blockBeneath = !this.world.isAirBlock(this.getPositionUnderneath());

            Vec3 vec3d = this.getDeltaMovement();
            if (!this.onGround && vec3d.y < 0.0) {
                this.setDeltaMovement(vec3d.multiply(1.0, 0.6, 1.0));
            }
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_MOA_AMBIENT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
    }
}
