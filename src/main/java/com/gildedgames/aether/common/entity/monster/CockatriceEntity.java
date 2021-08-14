package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.entity.projectile.PoisonNeedleEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.Random;

public class CockatriceEntity extends MonsterEntity implements IRangedAttackMob {

    public float wingRotation, prevWingRotation, destPos, prevDestPos;
    protected int ticksOffGround, ticksUntilFlap, secsUntilFlying;

    public CockatriceEntity(EntityType<? extends CockatriceEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public CockatriceEntity(World worldIn) {
        this(AetherEntityTypes.COCKATRICE.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2,  new RangedAttackGoal(this, 1.0, 60, 5));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createMonsterAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28000000417232513D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    //@Override
    //public void move(MoverType typeIn, Vec3d pos) {
        //super.move(typeIn, new Vec3d(0, pos.getY(), 0));
    //}


    public static boolean canCockatriceSpawn(EntityType<? extends CockatriceEntity> type, IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(45) == 0 && checkMonsterSpawnRules(type, worldIn, reason, pos, randomIn); //TODO: change the bounds of nextInt to a config value.
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        PoisonNeedleEntity needle = new PoisonNeedleEntity(this.level, this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY + (double)(target.getBbHeight() / 3.0F) - needle.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        needle.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(AetherSoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.level.addFreshEntity(needle);
    }

    @Override
    public boolean canBeAffected(EffectInstance effect) {
        return effect.getEffect() != Effects.POISON && super.canBeAffected(effect);
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
                    this.level.playSound(null, this.getX(), this.getY(), this.getZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.random.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.random.nextFloat(), 0.0F, 0.3F));
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
                this.destPos = MathHelper.clamp(this.destPos, 0.01F, 1.0F);
            }

            this.wingRotation += 1.233F;
        }

        fall: {
//			boolean blockBeneath = !this.world.isAirBlock(this.getPositionUnderneath());

            Vector3d vec3d = this.getDeltaMovement();
            if (!this.onGround && vec3d.y < 0.0) {
                this.setDeltaMovement(vec3d.multiply(1.0, 0.6, 1.0));
            }
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
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
