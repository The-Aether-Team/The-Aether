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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlyingCowEntity extends MountableEntity
{
    public float wingFold;
    public float wingAngle;

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
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(AetherItems.BLUE_BERRY.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new FallingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        return new FallPathNavigator(this, world);
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F);
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

    @Override
    public ActionResultType mobInteract(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
            playerEntity.playSound(AetherSoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, playerEntity, Items.MILK_BUCKET.getDefaultInstance());
            playerEntity.setItemInHand(hand, itemstack1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(playerEntity, hand);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_AMBIENT.get();
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
    protected SoundEvent getSaddledSound() {
        return AetherSoundEvents.ENTITY_FLYING_COW_SADDLE.get();
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(AetherSoundEvents.ENTITY_FLYING_COW_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public float getSteeringSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.75F;
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
        return AetherEntityTypes.FLYING_COW.get().create(world);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return this.isBaby() ? size.height * 0.95F : 1.3F;
    }
}
