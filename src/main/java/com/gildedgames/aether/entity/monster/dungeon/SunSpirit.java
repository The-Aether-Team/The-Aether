package com.gildedgames.aether.entity.monster.dungeon;

import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.capability.AetherCapabilities;
import com.gildedgames.aether.entity.projectile.crystal.AbstractCrystal;
import com.gildedgames.aether.entity.projectile.crystal.FireCrystal;
import com.gildedgames.aether.entity.projectile.crystal.IceCrystal;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.BossInfoPacket;
import com.gildedgames.aether.api.BossNameGenerator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

/**
 * Implementation for the sun spirit, the final boss of the Aether. When the sun spirit is defeated, eternal day will
 * end in the dimension.
 */
public class SunSpirit extends Monster implements BossMob {
    public static final EntityDataAccessor<Boolean> DATA_IS_FROZEN = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.COMPONENT);

    /** Boss health bar manager */
    private final ServerBossEvent bossFight;
    /** The sun spirit will return here when not in a fight. */
    private BlockPos originPos;

    private int xMax;
    private int zMax;

    private float moveRot = 0;
    private float velocity;

    public SunSpirit(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(true);
        this.xpReward = XP_REWARD_BOSS;
        this.setNoGravity(true);
        this.noPhysics = true;
    }

    /**
     * Generates a name for the boss.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSunSpiritName());
        this.originPos = new BlockPos(this.position());
        return data;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 48.0F));
        this.goalSelector.addGoal(2, new ShootFireballGoal(this));
        this.goalSelector.addGoal(3, new SummonFireGoal(this));
    }

    public static AttributeSupplier.Builder createSunSpiritAttributes() {
        return AbstractValkyrie.createAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_FROZEN, false);
        this.entityData.define(DATA_BOSS_NAME, Component.literal("Sun Spirit"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() > 0) {
            double x = this.getX() + (this.random.nextFloat() - 0.5F) * this.random.nextFloat();
            double y = this.getBoundingBox().minY + this.random.nextFloat() - 0.5;
            double z = this.getZ() + (this.random.nextFloat() - 0.5F) * this.random.nextFloat();
            this.level.addParticle(ParticleTypes.FLAME, x, y, z, 0, -0.07500000298023224, 0);

            this.burnEntities();
        }
    }

    /**
     * Burns all entities directly under the sun spirit
     */
    public void burnEntities() {
        List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().expandTowards(0, 4, 0));
        for (Entity target : entities) {
            if (target instanceof LivingEntity) {
                target.hurt(new EntityDamageSource("incineration", this), 10);
                target.setSecondsOnFire(15);
            }
        }
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.setFrozen(this.hurtTime > 0);

        /*this.velocity = 0.5F - (this.getHealth() / 350F);
        if (this.getX() >= this.originPos.getX() + this.xMax || this.getX() <= this.originPos.getX() - this.xMax) {
            this.moveRot = 360 - this.moveRot;
        }
        if (this.getZ() >= this.originPos.getZ() + this.zMax || this.getZ() <= this.originPos.getZ() - this.zMax) {
            this.moveRot = 180 - this.moveRot;
        }
        this.moveRot = Mth.wrapDegrees(this.moveRot);

        this.setDeltaMovement(Mth.sin(moveRot) * this.velocity, 0, Mth.cos(moveRot) * this.velocity);*/
    }

    /**
     * The sun spirit is immune to effects, but there is an event fired in case addons want to change that.
     */
    @Override
    public boolean canBeAffected(@Nonnull MobEffectInstance pEffectInstance) {
        net.minecraftforge.event.entity.living.MobEffectEvent.Applicable event = new net.minecraftforge.event.entity.living.MobEffectEvent.Applicable(this, pEffectInstance);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() != net.minecraftforge.eventbus.api.Event.Result.DEFAULT) return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        return false;
    }

    /**
     * Plays the sun spirit's defeat message and ends eternal day.
     */
    @Override
    public void die(@Nonnull DamageSource pCause) {
        if (!this.level.isClientSide) {
            this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.dead").withStyle(ChatFormatting.AQUA));
            this.level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> {
                aetherTime.setEternalDay(false);
                aetherTime.updateEternalDay();
            });
        }
        super.die(pCause);
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     */
    protected void chatWithNearby(Component message) {
        this.level.getNearbyPlayers(NON_COMBAT, this, this.getBoundingBox().inflate(16, 16, 16)).forEach(player ->
                player.sendSystemMessage(message));
    }

    /**
     * The sun spirit doesn't take knockback
     */
    @Override
    public void knockback(double strength, double ratioX, double ratioZ) { }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), pPlayer);
        this.bossFight.addPlayer(pPlayer);
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), pPlayer);
        this.bossFight.removePlayer(pPlayer);
    }

    public boolean isFrozen() {
        return this.entityData.get(DATA_IS_FROZEN);
    }

    public void setFrozen(boolean frozen) {
        this.entityData.set(DATA_IS_FROZEN, frozen);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME, component);
        this.bossFight.setName(component);
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("BossName", Component.Serializer.toJson(this.getBossName()));
        tag.putInt("OriginX", this.originPos.getX());
        tag.putInt("OriginY", this.originPos.getY());
        tag.putInt("OriginZ", this.originPos.getZ());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        Component name = Component.Serializer.fromJson(tag.getString("BossName"));
        if (name != null) {
            this.setBossName(name);
        }
        if (tag.contains("OriginX")) {
            this.originPos = new BlockPos(tag.getInt("OriginX"), tag.getInt("OriginY"), tag.getInt("OriginZ"));
        }
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    /**
     * Sets the wanted position of the sun spirit during the fight.
     */
    public static class FlyAroundGoal extends Goal {

        public FlyAroundGoal(SunSpirit sunSpirit) {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return false;
        }
    }

    /**
     * The sun spirit will stay at its origin point when not in a boss fight.
     */
    public static class DoNothingGoal extends Goal {
        private final SunSpirit sunSpirit;
        public DoNothingGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return !this.sunSpirit.isBossFight();
        }

        /**
         * Returns the sun spirit to its original position.
         */
        @Override
        public void start() {
            this.sunSpirit.navigation.moveTo(this.sunSpirit.originPos.getX(), this.sunSpirit.originPos.getY(), this.sunSpirit.originPos.getZ(), 1);
        }
    }

    /**
     * This goal makes the sun spirit shoot fire crystals and ice crystals randomly. It shoots more crystals as
     * its health gets lower.
     */
    public static class ShootFireballGoal extends Goal {
        private SunSpirit sunSpirit;
        private int shootInterval;
        private int crystalCount = 3;

        public ShootFireballGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.shootInterval = (int) (55 + sunSpirit.getHealth() / 2);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.sunSpirit.isBossFight() && --this.shootInterval <= 0;
        }

        @Override
        public void start() {
            AbstractCrystal crystal;
            if (--this.crystalCount <= 0) {
                crystal = new IceCrystal(this.sunSpirit.level, this.sunSpirit);
                this.crystalCount = 3 + this.sunSpirit.random.nextInt(3);
            } else {
                crystal = new FireCrystal(this.sunSpirit.level, this.sunSpirit);
            }
            this.sunSpirit.level.addFreshEntity(crystal);
            this.shootInterval = (int) (55 + sunSpirit.getHealth() / 2);
        }
    }

    /**
     * Randomly places fire below the sun spirit
     */
    public static class SummonFireGoal extends Goal {
        private SunSpirit sunSpirit;
        private int shootInterval;

        public SummonFireGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.shootInterval = 0;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return ++this.shootInterval >= 40;
        }

        @Override
        public void start() {
            BlockPos pos = new BlockPos(this.sunSpirit.getX(), this.sunSpirit.getY(), this.sunSpirit.getZ());
            for (int i = 0; i <= 3; i++) {
                if (this.sunSpirit.level.isEmptyBlock(pos) && !this.sunSpirit.level.isEmptyBlock(pos.below())) {
                    this.sunSpirit.level.setBlock(pos, Blocks.FIRE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    break;
                }
                pos = pos.below();
            }
            this.shootInterval = 0;
        }
    }
}
