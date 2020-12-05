package com.aether.entity.passive;

import com.aether.entity.AetherEntityTypes;
import com.aether.item.AetherItems;
import com.aether.util.AetherSoundEvents;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//TODO: Can't figure out how to let the player sneak without dismounting the entity.
public class PhygEntity extends SaddleableEntity {
    public float wingFold;
    public float wingAngle;
    public int ticks;

    public PhygEntity(EntityType<? extends PhygEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.fromItems(AetherItems.BLUEBERRY), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        if (!this.isSaddled()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
        }
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
        this.wingFold += (aimingForFold - this.wingFold) / 5.0F;

        this.fallDistance = 0.0F;
        if (this.getMotion().y < -0.1 && !this.isRiderSneaking()) {
            this.setMotion(getMotion().x, -0.1, getMotion().z);
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
        return AetherEntityTypes.PHYG.create(this.world);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_PHYG_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_PHYG_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_PHYG_AMBIENT;
    }

    /**
     * Override to handle the change in health when applying a saddle to the entity.
     * @param flag - Value for whether its saddled or not
     */
    @Override
    public void setSaddled(boolean flag) {
        super.setSaddled(flag);
        if(flag) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0F);
        }
        else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0);
            this.setHealth(10.0F);
        }
    }
}
