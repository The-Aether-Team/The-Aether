package com.gildedgames.aether.common.entity.passive;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomWalkingGoal;
import com.gildedgames.aether.common.entity.ai.navigator.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PhygEntity extends MountableEntity
{
    public float wingFold;
    public float wingAngle;

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

    @Override
    protected PathNavigator createNavigation(World world) {
        return new FallPathNavigator(this, world);
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getDeltaMovement().y < -0.1 && !this.playerTriedToCrouch) {
            this.setDeltaMovement(this.getDeltaMovement().x, -0.1, this.getDeltaMovement().z);
        }
    }

    @Override
    public void travel(Vector3d vector3d) {
        float f = this.flyingSpeed;
        if (this.isEffectiveAi() && !this.isOnGround() && this.getPassengers().isEmpty()) {
            this.flyingSpeed = this.getSpeed() * (0.24F / (0.91F * 0.91F * 0.91F));
            super.travel(vector3d);
            this.flyingSpeed = f;
        } else {
            this.flyingSpeed = f;
            super.travel(vector3d);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_PHYG_AMBIENT.get();
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
    protected SoundEvent getSaddledSound() {
        return AetherSoundEvents.ENTITY_PHYG_SADDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherSoundEvents.ENTITY_PHYG_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public int getMaxFallDistance() {
        return this.isOnGround() ? super.getMaxFallDistance() : 14;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        return AetherEntityTypes.PHYG.get().create(world);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
