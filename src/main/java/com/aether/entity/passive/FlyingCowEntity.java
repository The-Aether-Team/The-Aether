package com.aether.entity.passive;

import javax.annotation.Nullable;

import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;
import com.aether.util.AetherSoundEvents;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class FlyingCowEntity extends SaddleableEntity {
    public float wingFold;
    public float wingAngle;
    protected int ticks;
    
    public FlyingCowEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }
    
    public FlyingCowEntity(World worldIn) {
    	this(AetherEntityTypes.FLYING_COW, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
        if (!this.isSaddled()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(AetherItems.BLUEBERRY), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        float aimingForFold;
        if (this.onGround) {
            this.wingAngle *= 0.8F;
            aimingForFold = 0.1F;
        }
        else {
            aimingForFold = 1.0F;
        }
        ticks++;

        this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.83098862F);
        this.wingFold += (aimingForFold - this.wingFold) / 5F;
        this.fallDistance = 0.0F;

        this.fallDistance = 0.0F;
        if (this.getMotion().y < -0.1D && !this.isRiderSneaking()) {
            this.setMotion(getMotion().x, -0.1D, getMotion().z);
        }
    }

    @Override
    public void handleStartJump(int p_184775_1_) {
        this.setMountJumping(true);
        this.onMountedJump();
    }

    public void onMountedJump() {
        if(this.onGround) {
            this.setMotion(this.getMotion().getX(), 2.0F, this.getMotion().getZ());
        }
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return AetherEntityTypes.FLYING_COW.create(this.world);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_FLYING_COW_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_AMBIENT;
    }

    /**
     * Override to handle the change in health when applying a saddle to the entity.
     * @param flag - Value for whether its saddled or not
     */
    @Override
    public void setSaddled(boolean flag) {
        super.setSaddled(flag);
        if(flag) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
            this.setHealth(10.0F);
        }
    }
}
