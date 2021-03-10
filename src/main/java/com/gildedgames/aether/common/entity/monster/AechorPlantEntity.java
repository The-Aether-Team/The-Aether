package com.gildedgames.aether.common.entity.monster;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class AechorPlantEntity extends CreatureEntity implements IRangedAttackMob {

    public float sinage;
    public int size/*, poisonRemaining*/;


    public AechorPlantEntity(EntityType<? extends AechorPlantEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2,  new RangedAttackGoal(this, 1.0, 60, 5));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return CreatureEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0F);
    }

    public static boolean canAechorSpawn(EntityType<? extends AechorPlantEntity> animal, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get() && worldIn.getRawBrightness(pos, 0) > 8;
    }

    public void performRangedAttack (LivingEntity target, float distanceFactor) {
        ArrowEntity arrow = new ArrowEntity(this.level, this);
        double d0 = target.getX() - this.getX();
        double d1 = target.getBoundingBox().minY + (double)(target.getBbHeight() / 3.0F) - arrow.getY();
        double d2 = target.getZ() - this.getZ();
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        arrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float)(14 - this.level.getDifficulty().getId() * 4));
        //this.playSound(SoundsAether.cockatrice_attack, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.level.addFreshEntity(arrow);
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
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = MathHelper.sqrt(d2);
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
                    d0 = d0 * (double) (1.0F - entityIn.pushthrough);
                    d1 = d1 * (double) (1.0F - entityIn.pushthrough);

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
