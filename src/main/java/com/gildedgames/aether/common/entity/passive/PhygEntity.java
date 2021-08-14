package com.gildedgames.aether.common.entity.passive;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomWalkingGoal;
import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

//TODO: Can't figure out how to let the player sneak without dismounting the entity.
public class PhygEntity extends SaddleableEntity {
    public float wingFold;
    public float wingAngle;
    public int ticks;

    public PhygEntity(EntityType<? extends PhygEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public PhygEntity(World worldIn) {
        this(AetherEntityTypes.PHYG.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, Ingredient.of(AetherItems.BLUE_BERRY.get()), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new FallingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected PathNavigator createNavigation(World p_175447_1_) {
        return new FallPathNavigator(this, p_175447_1_);
    }

    @Override
    public void tick() {
        super.tick();
        float aimingForFold;
        if (this.onGround) {
            this.wingAngle *= 0.8F;
            aimingForFold = 0.1F;
        } else {
            aimingForFold = 1.0F;
        }
        this.ticks++;
        this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.83098862F);
        this.wingFold += (aimingForFold - this.wingFold) / 5.0F;

        this.fallDistance = 0.0F;
        if (this.getDeltaMovement().y < -0.1 && !this.isRiderSneaking()) {
            this.setDeltaMovement(getDeltaMovement().x, -0.1, getDeltaMovement().z);
        }
    }

    @Override
    public void travel(Vector3d vector3d1) {
        float f = this.flyingSpeed;
        if (this.isEffectiveAi() && !this.isOnGround() && this.getPassengers().isEmpty()) {
            this.flyingSpeed = this.getSpeed() * (0.24F / (0.91F * 0.91F * 0.91F));
            super.travel(vector3d1);
            this.flyingSpeed = f;
        } else {
            this.flyingSpeed = f;
            super.travel(vector3d1);
        }
    }

    @Override
    public int getMaxFallDistance() {
        return this.isOnGround() ? super.getMaxFallDistance() : 14;
    }

    @Override
    public void handleStartJump(int jumpPower) {
        this.setMountJumping(true);
        this.onMountedJump();
    }

    public void onMountedJump() {
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().x(), 2.0F, this.getDeltaMovement().z());
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.PHYG.get().create(this.level);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_PHYG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_PHYG_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_PHYG_AMBIENT.get();
    }
}
