package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.ai.FallingRandomWalkingGoal;
import com.gildedgames.aether.common.entity.pathfinding.FallPathNavigator;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
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
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(AetherItems.BLUE_BERRY.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new FallingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return SaddleableEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.20000000298023224D);
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
        ticks++;

        this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.83098862F);
        this.wingFold += (aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;

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
    public void handleStartJump(int jumpPower) {
        this.setMountJumping(true);
        this.onMountedJump();
    }

    public void onMountedJump() {
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().x(), 2.0F, this.getDeltaMovement().z());
        }
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, itemstack1);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.FLYING_COW.get().create(this.level);
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
