package com.aether.entity.monster;

import com.aether.entity.ai.zephyr.ZephyrAttackGoal;
import com.aether.entity.ai.zephyr.ZephyrLookAroundGoal;
import com.aether.entity.ai.zephyr.ZephyrMovementController;
import com.aether.entity.ai.zephyr.ZephyrRandomFlyGoal;
import com.aether.util.AetherSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class ZephyrEntity extends FlyingEntity implements IMob {
    public final ZephyrAttackGoal shootingGoal;
    public ZephyrEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new ZephyrMovementController(this);
        shootingGoal = new ZephyrAttackGoal(this);
        this.goalSelector.addGoal(7, shootingGoal);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(5, new ZephyrRandomFlyGoal(this));
        this.goalSelector.addGoal(7, new ZephyrLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false));
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (getPosY() < -2D || getPosY() > 255D)
            remove();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 3F;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return AetherSoundEvents.ENTITY_ZEPHYR_AMBIENT;
    }

    public static boolean canZephyrSpawn(EntityType<? extends ZephyrEntity> zephyr, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random) {
        AxisAlignedBB boundingBox = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 4F, pos.getY() + 4F, pos.getZ() + 4F);
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(85) == 0 && worldIn.getEntitiesWithinAABB(ZephyrEntity.class, boundingBox).size() == 0 && !worldIn.containsAnyLiquid(boundingBox) && worldIn.getLight(pos) > 8 && canSpawnOn(zephyr, worldIn, reason, pos, random);
    }
}
