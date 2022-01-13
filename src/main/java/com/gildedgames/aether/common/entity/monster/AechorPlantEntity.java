package com.gildedgames.aether.common.entity.monster;

import java.util.Random;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.PoisonNeedleEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class AechorPlantEntity extends PathfinderMob implements RangedAttackMob {

    public float sinage;
    public int size/*, poisonRemaining*/;

    public AechorPlantEntity(EntityType<? extends AechorPlantEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public AechorPlantEntity(Level worldIn) {
        this(AetherEntityTypes.AECHOR_PLANT.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2,  new RangedAttackGoal(this, 1.0, 60, 5));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0F);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setPos(Math.floor(this.getX()) + 0.5, this.getY(), Math.floor(this.getZ()) + 0.5);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public static boolean canAechorSpawn(EntityType<? extends AechorPlantEntity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() && worldIn.getRawBrightness(pos, 0) > 8;
    }

    public void performRangedAttack (LivingEntity target, float distanceFactor) {
        PoisonNeedleEntity needle = new PoisonNeedleEntity(this.level, this);
        double x = target.getX() - this.getX();
        double y = target.getBoundingBox().minY + (double)(target.getBbHeight() / 3.0F) - needle.getY();
        double z = target.getZ() - this.getZ();
        double distance = Mth.sqrt((float) (x * x + z * z));
        needle.shoot(x, y + distance * 0.20000000298023224D, z, 1.0F, (float)(14 - this.level.getDifficulty().getId() * 4));
        this.playSound(AetherSoundEvents.ENTITY_COCKATRICE_SHOOT.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.level.addFreshEntity(needle);
    }



    @SuppressWarnings("unused")
    @Override
    public void tick() {
        super.tick();

        if (this.level.getBlockState(this.blockPosition().below()).getBlock() != AetherBlocks.AETHER_GRASS_BLOCK.get()) {
            this.setHealth(0.0F);
        }

        if (this.hurtTime > 0) {
            this.sinage += 0.9F;
        } else {
            if (this.getTarget() != null) {
                this.sinage += 0.3F;
            }
            else {
                this.sinage += 0.1F;
            }
        }
        if (this.sinage > 3.141593F * 2F) {
            this.sinage -= (3.141593F * 2F);
        }
    }

    @Override
    protected void doPush(Entity entityIn) {
        if (!entityIn.isPassengerOfSameVehicle(this)) {
            if (!this.noPhysics && !entityIn.noPhysics){
                double d0 = this.getX() - entityIn.getX();
                double d1 = this.getZ() - entityIn.getZ();
                double d2 = Mth.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = Mth.sqrt((float) d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;

                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
//                    d0 = d0 * (double) (1.0F - entityIn.pushthrough); //TODO: What is pushthrough?
//                    d1 = d1 * (double) (1.0F - entityIn.pushthrough);

                    if (!entityIn.isVehicle()) {
                        entityIn.push(-d0, 0.0D, -d1);
                    }
                }
            }
        }
    }


    @Override
    public boolean isPushable() {
        return false;
    }
}


