package com.aether.entity.monster;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
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

    protected final Random rand = new Random();

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

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return CreatureEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 16.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28000000417232513D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 10.0D);
    }

    //@Override
    //public void move(MoverType typeIn, Vec3d pos) {
        //super.move(typeIn, new Vec3d(0, pos.getY(), 0));
    //}


    public static boolean canCockatriceSpawn(EntityType<? extends CockatriceEntity> type, IServerWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(45) == 0 && canMonsterSpawnInLight(type, worldIn, reason, pos, randomIn); //TODO: change the bounds of nextInt to a config value.
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ArrowEntity arrow = new ArrowEntity(this.world, this);
        double d0 = target.getPosX() - this.getPosX();
        double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - arrow.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        arrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float)(14 - this.world.getDifficulty().getId() * 4));
        //this.playSound(SoundsAether.cockatrice_attack, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.world.addEntity(arrow);
    }
    @SuppressWarnings("unused")
    @Override
    public void tick() {
        super.tick();

        if (this.isJumping) {
            this.addVelocity(0.0, 0.05, 0.0);
        }

        updateWingRotation: {
            if (!this.onGround) {
                if (this.ticksUntilFlap == 0) {
                    this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), AetherSoundEvents.ENTITY_MOA_FLAP.get(), SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7F, 1.0F) + MathHelper.clamp(this.rand.nextFloat(), 0.0F, 0.3F));
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

            Vector3d vec3d = this.getMotion();
            if (!this.onGround && vec3d.y < 0.0) {
                this.setMotion(vec3d.mul(1.0, 0.6, 1.0));
            }
        }

        this.fallDistance = 0.0F;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
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
