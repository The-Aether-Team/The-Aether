package com.gildedgames.aether.entity.monster;

import com.gildedgames.aether.registry.AetherBlocks;
import com.gildedgames.aether.registry.AetherParticleTypes;
import com.gildedgames.aether.player.AetherRankings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.*;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WhirlwindEntity extends MobEntity {
    public static final DataParameter<Boolean> IS_EVIL = EntityDataManager.createKey(WhirlwindEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> COLOR_DATA = EntityDataManager.createKey(WhirlwindEntity.class, DataSerializers.VARINT);

    public int lifeLeft;
    public int actionTimer;
    public float movementAngle;
    public float movementCurve;

    public WhirlwindEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.movementAngle = this.rand.nextFloat() * 360F;
        this.movementCurve = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
        this.lifeLeft = this.rand.nextInt(512) + 512;
        if(this.rand.nextInt(10) == 0) {
            this.lifeLeft /= 2;
            this.setEvil(true);
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_EVIL, false);
        this.dataManager.register(COLOR_DATA, 0xFFFFFF);
    }

    public static boolean canWhirlwindSpawn(EntityType<? extends WhirlwindEntity> typeIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return randomIn.nextInt(450) == 0
                && worldIn.getBlockState(pos.down()).getBlock() == AetherBlocks.AETHER_GRASS_BLOCK.get()
                && worldIn.getLight(pos) > 8
                && MobEntity.canSpawnOn(typeIn, worldIn, reason, pos, randomIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.025D + 0.025D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 35.0D);
    }

    public void setColorData(Integer data) {
        this.dataManager.set(COLOR_DATA, data);
    }

    public Integer getColorData() {
        return this.dataManager.get(COLOR_DATA);
    }

    public void setEvil(boolean isEvil) {
        this.dataManager.set(IS_EVIL, isEvil);
    }

    public boolean isEvil() {
        return this.dataManager.get(IS_EVIL);
    }

    @Override
    public void livingTick() {
        PlayerEntity closestPlayer = this.findClosestPlayer();

        if(this.isEvil()) {
            if(closestPlayer != null && !closestPlayer.isAirBorne) {
                this.setAttackTarget(closestPlayer);
            }
        }

        if(this.getAttackTarget() == null) {
            this.setMotion(Math.cos(0.01745329F * this.movementAngle) * this.getAttribute(Attributes.MOVEMENT_SPEED).getValue(), this.getMotion().y, -Math.sin(0.01745329F * this.movementAngle) * this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            this.movementAngle += this.movementCurve;
        }
        else {
            super.livingTick();
        }

        if((this.lifeLeft-- <= 0 || isInWater()) && !this.world.isRemote) {
            this.setDead();
        }

        if (!this.world.isRemote) {
            if(closestPlayer != null) {
                this.actionTimer++;
            }

            if(this.actionTimer >= 128) {
                if(this.isEvil()) {
                    CreeperEntity entitycreeper = new CreeperEntity(EntityType.CREEPER, this.world);

                    entitycreeper.setLocationAndAngles(this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), this.rand.nextFloat() * 360F, 0.0F);
                    entitycreeper.setMotion((double)(this.rand.nextFloat() - this.rand.nextFloat()) * 0.125D, entitycreeper.getMotion().y, (double)(this.rand.nextFloat() - this.rand.nextFloat()) * 0.125D);

                    this.world.addEntity(entitycreeper);
                    this.actionTimer = 0;
                    this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.HOSTILE, 0.5F, 1.0F);
                }
                else if (this.rand.nextInt(4) == 0) {
                    this.entityDropItem(this.getRandomDrop(), 1);
                    this.actionTimer = 0;
                    this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.HOSTILE, 0.5F, 1.0F);
                }
            }
        }
        else {
            this.updateParticles();
        }

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox().expand(2.5D, 2.5D, 2.5D));

        for(int l = 0; l < list.size(); l++) {
            Entity entity = (Entity)list.get(l);

            double d9 = (float)entity.getPosX();
            double d11 = (float)entity.getPosY() - entity.getYOffset() * 0.6F;
            double d13 = (float)entity.getPosZ();
            double d15 = this.getDistance(entity);
            double d17 = d11 - this.getPosY();

            if(d15 <= 1.5D + d17) {
                entity.setMotion(entity.getMotion().x, 0.15000000596046448D, entity.getMotion().z);
                entity.fallDistance = 0.0F;

                if(d17 > 1.5D) {
                    entity.setMotion(entity.getMotion().x, -0.44999998807907104D + d17 * 0.34999999403953552D, entity.getMotion().z);
                    d15 += d17 * 1.5D;
                }
                else {
                    entity.setMotion(entity.getMotion().x, 0.125D, entity.getMotion().z);
                }

                double d19 = Math.atan2(this.getPosX() - d9, this.getPosZ() - d13) / 0.01745329424738884D;
                d19 += 160D;
                entity.setMotion(-Math.cos(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D, entity.getMotion().y, Math.sin(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D);

                if(entity instanceof WhirlwindEntity) {
                    entity.remove();

                    if(!this.isEvil()) {
                        this.lifeLeft /= 2;
                        this.setEvil(true);
                        this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F,1.0F);
                    }
                }
            }
            else {
                double d20 = Math.atan2(this.getPosX() - d9, this.getPosZ() - d13) / 0.01745329424738884D;
                entity.setMotion(entity.getMotion().add(Math.sin(0.01745329424738884D * d20) * 0.0099999997764825821D, entity.getMotion().y, Math.cos(0.01745329424738884D * d20) * 0.0099999997764825821D));
            }

            if(!this.world.isAirBlock(this.getPosition())) {
                this.lifeLeft -= 50;
            }

            if (this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && !world.isRemote) {
                int i2 = (MathHelper.floor(this.getPosX()) - 1) + this.rand.nextInt(3);
                int j2 = MathHelper.floor(this.getPosY()) + this.rand.nextInt(5);
                int k2 = (MathHelper.floor(this.getPosZ()) - 1) + this.rand.nextInt(3);

                if(this.world.getBlockState(new BlockPos.Mutable().setPos(i2, j2, k2)).getBlock() instanceof LeavesBlock) {
                    this.world.destroyBlock(new BlockPos(i2, j2, k2), false);
                }
            }
        }
    }

    /**
     * This method is called when a player right-clicks the entity.
     */
    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() instanceof DyeItem && !AetherRankings.getRanksOf(player.getUniqueID()).isEmpty()) {
            this.setColorData(((DyeItem) heldItem.getItem()).getDyeColor().getColorValue());

            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    @Override
    public boolean canTriggerWalking() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void updateParticles() {
        if(!this.isEvil()) {
            for(int k = 0; k < 2; k++) {
                double d1 = (float)this.getPosX() + rand.nextFloat() * 0.25F;
                double d4 = (float)getPosY() + getHeight() + 0.125F;
                double d7 = (float)this.getPosZ() + rand.nextFloat() * 0.25F;
                float f = rand.nextFloat() * 360F;
                this.world.addParticle(AetherParticleTypes.PASSIVE_WHIRLWIND.get(), d1, d4 - 0.25D, d7, -Math.sin(0.01745329F * f) * 0.75D, 0.125D, Math.cos(0.01745329F * f) * 0.75D);

            }
        }
        else {
            for(int k = 0; k < 3; k++) {
                double d2 = (float)getPosX() + rand.nextFloat() * 0.25F;
                double d5 = (float)getPosY() + getHeight() + 0.125F;
                double d8 = (float)getPosZ() + rand.nextFloat() * 0.25F;
                float f1 = rand.nextFloat() * 360F;
                this.world.addParticle(AetherParticleTypes.EVIL_WHIRLWIND.get(), d2, d5 - 0.25D, d8, -Math.sin(0.01745329F * f1) * 0.75D, 0.125D, Math.cos(0.01745329F * f1) * 0.75D);
            }
        }
    }

    public Item getRandomDrop() {
        int i = this.rand.nextInt(100) + 1;

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
            return Item.getItemFromBlock(Blocks.PUMPKIN);
        }

        if(i >= 75) {
            return Item.getItemFromBlock(Blocks.GRAVEL);
        }

        if(i >= 64) {
            return Item.getItemFromBlock(Blocks.CLAY);
        }

        if(i >= 52) {
            return Items.STICK;
        }

        if(i >= 38) {
            return Items.FLINT;
        }

        if(i > 20) {
            return Item.getItemFromBlock(Blocks.OAK_LOG);
        }
        else {
            return Item.getItemFromBlock(Blocks.SAND);
        }
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return this.world.checkNoEntityCollision(this) && this.world.getCollisionShapes(this, this.getBoundingBox()).count() == 0 && !this.world.containsAnyLiquid(this.getBoundingBox());
    }

    public PlayerEntity findClosestPlayer() {
        return this.world.getClosestPlayer(this, 16D);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putFloat("movementAngle", this.movementAngle);
        nbttagcompound.putFloat("movementCurve", this.movementCurve);
        nbttagcompound.putInt("lifeLeft", this.lifeLeft);
        nbttagcompound.putBoolean("evil", this.isEvil());
        nbttagcompound.putInt("color", this.getColorData());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.movementAngle = nbttagcompound.getFloat("movementAngle");
        this.movementCurve = nbttagcompound.getFloat("movementCurve");
        this.lifeLeft = nbttagcompound.getInt("lifeLeft");
        this.setEvil(nbttagcompound.getBoolean("evil"));
        this.setColorData(nbttagcompound.getInt("color"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        return false;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 3;
    }

    @Override
    public boolean isOnLadder() {
        return collidedHorizontally;
    }
}
