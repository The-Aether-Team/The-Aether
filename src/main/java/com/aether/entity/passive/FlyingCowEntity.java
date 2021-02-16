package com.aether.entity.passive;

import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import com.aether.registry.AetherSoundEvents;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class FlyingCowEntity extends SaddleableEntity {
    public float wingFold;
    public float wingAngle;
    protected int ticks;

    public FlyingCowEntity(EntityType<? extends FlyingCowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public FlyingCowEntity(World worldIn) {
        this(AetherEntityTypes.FLYING_COW.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.fromItems(AetherItems.BLUEBERRY.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return SaddleableEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
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

        this.fallDistance = 0.0F;
        if (this.getMotion().y < -0.1 && !this.isRiderSneaking()) {
            this.setMotion(getMotion().x, -0.1, getMotion().z);
        }
    }

    @Override
    public void handleStartJump(int jumpPower) {
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
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.FLYING_COW.get().create(this.world);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_FLYING_COW_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_AMBIENT.get();
    }
}
