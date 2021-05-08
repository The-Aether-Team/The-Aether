package com.gildedgames.aether.common.entity.passive;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.AetherAnimalEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class AerbunnyEntity extends AetherAnimalEntity {
    public static final DataParameter<Integer> PUFF = EntityDataManager.defineId(AerbunnyEntity.class, DataSerializers.INT);

    public int puffiness = 0;
    private int jumps;
    private int jumpTicks;

    public AerbunnyEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new RandomWalkingGoal(this,2D, 6));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(AetherItems.BLUE_BERRY.get()), false));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(5, new HopGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.MAX_HEALTH, 5.0D);
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(PUFF, 0);
    }

    public int getPuffinessClient()
    {
        return this.puffiness;
    }

    public int getPuffiness()
    {
        return this.entityData.get(PUFF);
    }

    public void setPuffinessClient(int i)
    {
        this.puffiness = i;
    }

    public void setPuffiness(int i)
    {
        this.entityData.set(PUFF, i);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() == Items.NAME_TAG) {
            return super.mobInteract(player, hand);
        }
        else {
            this.level.playSound(player, this, AetherSoundEvents.ENTITY_AERBUNNY_LIFT.get(), SoundCategory.NEUTRAL, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            if (this.isPassenger()) {
                this.stopRiding();
            }
            else {
                this.startRiding(player);
            }

            return ActionResultType.SUCCESS;
        }
    }

    /**
     * this is necessary to allow the player to remove the aerbunny.
     */
    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if(this.getVehicle() == null)
            return super.isInvulnerableTo(damageSource);
        
        return damageSource.getEntity() == this.getVehicle() || super.isInvulnerableTo(damageSource);
    }



    @Override
    public void tick() {
        this.setPuffinessClient(this.getPuffinessClient() - 1);
        this.setPuffiness(this.getPuffiness() - 1);

        if (this.getPuffinessClient() < 0) {
            this.setPuffinessClient(0);
        }

        if (this.getPuffiness() < 0) {
            this.setPuffiness(0);
        }

        super.tick();
    }

    @Override
    public void aiStep() {
        if (this.onGround) {
            this.jumps = 1;
            this.jumpTicks = 10;
        }
        else if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }

        if (this.jumping && !this.isInWater() && !this.isInLava() && !this.onGround && this.jumpTicks == 0 && this.jumps > 0) {
            if(this.getDeltaMovement().x != 0.0F || this.getDeltaMovement().z != 0.0F) {
                this.jumpFromGround();
            }
            this.jumpTicks = 10;
        }

        if (this.getDeltaMovement().y < -0.1D) {
            this.setDeltaMovement(getDeltaMovement().x, -0.1D, getDeltaMovement().z);
        }

        if (this.getVehicle() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) this.getVehicle();

            if (this.level.isClientSide) {
                for(int k = 0; k < 3; k++) {
                    double d2 = (float)getX() + random.nextFloat() * 0.25F;
                    double d5 = (float)getY() + getBbHeight() + 0.125F;
                    double d8 = (float)getZ() + random.nextFloat() * 0.25F;
                    float f1 = random.nextFloat() * 360F;
                    this.level.addParticle(AetherParticleTypes.WHITE_SMOKE.get(), -Math.sin(0.01745329F * f1) * 0.75D, d5 - 0.25D, Math.cos(0.01745329F * f1) * 0.75D, d2, 0.125D, d8);
                }
            }

            this.navigation.recomputePath();

            this.setRot(player.yHeadRot, player.xRot);

            player.fallDistance = 0.0F;

            if(!player.isOnGround() && !player.isFallFlying()) {
                if (!player.abilities.flying) {
                    player.setDeltaMovement(player.getDeltaMovement().add(0, 0.05000000074505806D, 0));
                }

                player.fallDistance = 0.0F;

                if(level.isClientSide && player.getDeltaMovement().y < -0.22499999403953552D && ((ClientPlayerEntity)player).input.jumping) {
                    this.setPuffinessClient(11);
                    this.spawnExplosionParticle();
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.125D, player.getDeltaMovement().z);
                }
            }
        }
        super.aiStep();
    }

    @Override
    protected void jumpFromGround() {
        this.spawnExplosionParticle();
        this.setPuffiness(11);
        --this.jumps;
        super.jumpFromGround();
    }

    private void spawnExplosionParticle() {
        if (this.level.isClientSide)
        {
            for (int i = 0; i < 5; ++i)
            {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                this.level.addParticle(AetherParticleTypes.WHITE_SMOKE.get(),
                        this.getX() + (double)(this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth() - d0 * d3,
                        this.getY() + (double)(this.random.nextFloat() * this.getBbHeight()) - d1 * d3,
                        this.getZ() + (double)(this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth() - d2 * d3,
                        d0, d1, d2);
            }
        }
    }

    @Override
    public boolean isInWall() {
        return !this.isPassenger() && super.isInWall();
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AetherSoundEvents.ENTITY_AERBUNNY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_AERBUNNY_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return AetherEntityTypes.AERBUNNY.get().create(this.level);
    }

    @Override
    public double getMyRidingOffset() {
        return 0.4D;
    }

    public static class HopGoal extends Goal {
        private AerbunnyEntity aerbunny;
        public HopGoal(AerbunnyEntity entity) {
            aerbunny = entity;
            setFlags(EnumSet.of(Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return this.aerbunny.getDeltaMovement().z > 0.0D || this.aerbunny.getDeltaMovement().x > 0.0D || this.aerbunny.onGround;
        }

        @Override
        public void tick() {
            if(aerbunny.getDeltaMovement().x != 0.0F || aerbunny.getDeltaMovement().z != 0.0F) {
                this.aerbunny.jumpControl.jump();
            }
        }
    }
}
