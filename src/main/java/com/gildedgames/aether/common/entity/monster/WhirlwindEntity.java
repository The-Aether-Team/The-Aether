package com.gildedgames.aether.common.entity.monster;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.core.api.AetherRankings;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WhirlwindEntity extends MobEntity {
    public static final DataParameter<Boolean> IS_EVIL = EntityDataManager.defineId(WhirlwindEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> COLOR_DATA = EntityDataManager.defineId(WhirlwindEntity.class, DataSerializers.INT);

    public int lifeLeft;
    public int actionTimer;
    public float movementAngle;
    public float movementCurve;

    public WhirlwindEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public WhirlwindEntity(World worldIn) {
        this(AetherEntityTypes.WHIRLWIND.get(), worldIn);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.movementAngle = this.random.nextFloat() * 360F;
        this.movementCurve = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        this.lifeLeft = this.random.nextInt(512) + 512;
        if(this.random.nextInt(10) == 0) {
            this.lifeLeft /= 2;
            this.setEvil(true);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_EVIL, false);
        this.entityData.define(COLOR_DATA, 0xFFFFFF);
    }

    public static boolean canWhirlwindSpawn(EntityType<? extends WhirlwindEntity> typeIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(450) == 0
                && worldIn.getBlockState(pos.below()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get()
                && worldIn.getMaxLocalRawBrightness(pos) > 8
                && MobEntity.checkMobSpawnRules(typeIn, worldIn, reason, pos, randomIn);
    }

    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.025D + 0.025D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    public void setColorData(Integer data) {
        this.entityData.set(COLOR_DATA, data);
    }

    public Integer getColorData() {
        return this.entityData.get(COLOR_DATA);
    }

    public void setEvil(boolean isEvil) {
        this.entityData.set(IS_EVIL, isEvil);
    }

    public boolean isEvil() {
        return this.entityData.get(IS_EVIL);
    }

    @Override
    public void aiStep() {
        PlayerEntity closestPlayer = this.findClosestPlayer();

        if(this.isEvil()) {
            if(closestPlayer != null && !closestPlayer.hasImpulse) {
                this.setTarget(closestPlayer);
            }
        }

        if(this.getTarget() == null) {
            this.setDeltaMovement(Math.cos(0.01745329F * this.movementAngle) * this.getAttribute(Attributes.MOVEMENT_SPEED).getValue(), this.getDeltaMovement().y, -Math.sin(0.01745329F * this.movementAngle) * this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            this.movementAngle += this.movementCurve;
        }
        else {
            super.aiStep();
        }

        if((this.lifeLeft-- <= 0 || isInWater()) && !this.level.isClientSide) {
            this.removeAfterChangingDimensions();
        }

        if (!this.level.isClientSide) {
            if(closestPlayer != null) {
                this.actionTimer++;
            }

            if(this.actionTimer >= 128) {
                if(this.isEvil()) {
                    CreeperEntity entitycreeper = new CreeperEntity(EntityType.CREEPER, this.level);

                    entitycreeper.moveTo(this.getX(), this.getY() + 0.5D, this.getZ(), this.random.nextFloat() * 360F, 0.0F);
                    entitycreeper.setDeltaMovement((double)(this.random.nextFloat() - this.random.nextFloat()) * 0.125D, entitycreeper.getDeltaMovement().y, (double)(this.random.nextFloat() - this.random.nextFloat()) * 0.125D);

                    this.level.addFreshEntity(entitycreeper);
                    this.actionTimer = 0;
                    this.level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundCategory.HOSTILE, 0.5F, 1.0F);
                }
                else if (this.random.nextInt(4) == 0) {
                    this.spawnAtLocation(this.getRandomDrop(), 1);
                    this.actionTimer = 0;
                    this.level.playSound(null, this.blockPosition(), SoundEvents.ITEM_PICKUP, SoundCategory.HOSTILE, 0.5F, 1.0F);
                }
            }
        }
        else {
            this.updateParticles();
        }

        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().expandTowards(2.5D, 2.5D, 2.5D));

        for(int l = 0; l < list.size(); l++) {
            Entity entity = (Entity)list.get(l);

            double d9 = (float)entity.getX();
            double d11 = (float)entity.getY() - entity.getMyRidingOffset() * 0.6F;
            double d13 = (float)entity.getZ();
            double d15 = this.distanceTo(entity);
            double d17 = d11 - this.getY();

            if(d15 <= 1.5D + d17) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, 0.15000000596046448D, entity.getDeltaMovement().z);
                entity.fallDistance = 0.0F;

                if(d17 > 1.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, -0.44999998807907104D + d17 * 0.34999999403953552D, entity.getDeltaMovement().z);
                    d15 += d17 * 1.5D;
                }
                else {
                    entity.setDeltaMovement(entity.getDeltaMovement().x, 0.125D, entity.getDeltaMovement().z);
                }

                double d19 = Math.atan2(this.getX() - d9, this.getZ() - d13) / 0.01745329424738884D;
                d19 += 160D;
                entity.setDeltaMovement(-Math.cos(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D, entity.getDeltaMovement().y, Math.sin(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D);

                if(entity instanceof WhirlwindEntity) {
                    entity.remove();

                    if(!this.isEvil()) {
                        this.lifeLeft /= 2;
                        this.setEvil(true);
                        this.level.playSound(null, this.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundCategory.HOSTILE, this.random.nextFloat() - this.random.nextFloat() * 0.2F + 1.2F,1.0F);
                    }
                }
            }
            else {
                double d20 = Math.atan2(this.getX() - d9, this.getZ() - d13) / 0.01745329424738884D;
                entity.setDeltaMovement(entity.getDeltaMovement().add(Math.sin(0.01745329424738884D * d20) * 0.0099999997764825821D, entity.getDeltaMovement().y, Math.cos(0.01745329424738884D * d20) * 0.0099999997764825821D));
            }

            if(!this.level.isEmptyBlock(this.blockPosition())) {
                this.lifeLeft -= 50;
            }

            if (this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && !level.isClientSide) {
                int i2 = (MathHelper.floor(this.getX()) - 1) + this.random.nextInt(3);
                int j2 = MathHelper.floor(this.getY()) + this.random.nextInt(5);
                int k2 = (MathHelper.floor(this.getZ()) - 1) + this.random.nextInt(3);

                if(this.level.getBlockState(new BlockPos.Mutable().set(i2, j2, k2)).getBlock() instanceof LeavesBlock) {
                    this.level.destroyBlock(new BlockPos(i2, j2, k2), false);
                }
            }
        }
    }

    /**
     * This method is called when a player right-clicks the entity.
     */
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.getItem() instanceof DyeItem && !AetherRankings.getRanksOf(player.getUUID()).isEmpty()) {
            this.setColorData(((DyeItem) heldItem.getItem()).getDyeColor().getColorValue());

            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isMovementNoisy() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void updateParticles() {
        if(!this.isEvil()) {
            for(int k = 0; k < 2; k++) {
                double d1 = this.getX() + this.random.nextDouble() * 0.25;
                double d4 = getY() + getBbHeight() + 0.125;
                double d7 = this.getZ() + this.random.nextDouble() * 0.25;
                float f = this.random.nextFloat() * 360;
                this.level.addParticle(AetherParticleTypes.PASSIVE_WHIRLWIND.get(), d1, d4 - 0.25, d7, -Math.sin(0.01745329F * f) * 0.75, 0.125, Math.cos(0.01745329F * f) * 0.75);

            }
        }
        else {
            for(int k = 0; k < 3; k++) {
                double d2 = getX() + this.random.nextDouble() * 0.25;
                double d5 = getY() + getBbHeight() + 0.125;
                double d8 = getZ() + this.random.nextDouble() * 0.25;
                float f1 = this.random.nextFloat() * 360;
                this.level.addParticle(AetherParticleTypes.EVIL_WHIRLWIND.get(), d2, d5 - 0.25, d8, -Math.sin(0.01745329F * f1) * 0.75, 0.125, Math.cos(0.01745329F * f1) * 0.75);
            }
        }
    }

    public Item getRandomDrop() {
        int i = this.random.nextInt(100) + 1;

        if(i == 100) {
            return Items.DIAMOND;
        }

        if(i >= 96) {
            return Items.IRON_INGOT;
        }

        if(i >= 91) {
            return Items.GOLD_INGOT;
        }

        if(i >= 82) {
            return Items.COAL;
        }

        if (i >= 80) {
            return Item.byBlock(Blocks.PUMPKIN);
        }

        if(i >= 75) {
            return Item.byBlock(Blocks.GRAVEL);
        }

        if(i >= 64) {
            return Item.byBlock(Blocks.CLAY);
        }

        if(i >= 52) {
            return Items.STICK;
        }

        if(i >= 38) {
            return Items.FLINT;
        }

        if(i > 20) {
            return Item.byBlock(Blocks.OAK_LOG);
        }
        else {
            return Item.byBlock(Blocks.SAND);
        }
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason spawnReasonIn) {
        return this.level.isUnobstructed(this) && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0 && !this.level.containsAnyLiquid(this.getBoundingBox());
    }

    public PlayerEntity findClosestPlayer() {
        return this.level.getNearestPlayer(this, 16D);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putFloat("movementAngle", this.movementAngle);
        nbttagcompound.putFloat("movementCurve", this.movementCurve);
        nbttagcompound.putInt("lifeLeft", this.lifeLeft);
        nbttagcompound.putBoolean("evil", this.isEvil());
        nbttagcompound.putInt("color", this.getColorData());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        this.movementAngle = nbttagcompound.getFloat("movementAngle");
        this.movementCurve = nbttagcompound.getFloat("movementCurve");
        this.lifeLeft = nbttagcompound.getInt("lifeLeft");
        this.setEvil(nbttagcompound.getBoolean("evil"));
        this.setColorData(nbttagcompound.getInt("color"));
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public boolean onClimbable() {
        return horizontalCollision;
    }
}
