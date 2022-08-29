package com.gildedgames.aether.entity.monster.dungeon.boss;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.api.DungeonTracker;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.entity.BossMob;
import com.gildedgames.aether.capability.AetherCapabilities;
import com.gildedgames.aether.entity.ai.controller.BlankMoveControl;
import com.gildedgames.aether.entity.monster.dungeon.AbstractValkyrie;
import com.gildedgames.aether.entity.monster.dungeon.FireMinion;
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
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

/**
 * Implementation for the sun spirit, the final boss of the Aether. When the sun spirit is defeated, eternal day will
 * end in the dimension.
 */
public class SunSpirit extends Monster implements BossMob<SunSpirit> {
    public static final EntityDataAccessor<Boolean> DATA_IS_FROZEN = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME = SynchedEntityData.defineId(SunSpirit.class, EntityDataSerializers.COMPONENT);

    private DungeonTracker<SunSpirit> goldDungeon;
    /** Boss health bar manager */
    private final ServerBossEvent bossFight;
    /** The sun spirit will return here when not in a fight. */
    private Vec3 originPos;

    private final int xMax = 11;
    private final int zMax = 11;

    private int chatLine = 0;
    private int chatCooldown = 0;

    protected double velocity;

    public SunSpirit(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
//        this.lookControl = new SunSpiritLookControl(this);
        this.moveControl = new BlankMoveControl(this);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.setBossFight(false);
        this.xpReward = XP_REWARD_BOSS;
        this.noPhysics = true;
        this.velocity =  1 - this.getHealth() / 700;
    }

    /**
     * Generates a name for the boss.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSunSpiritName());
        this.originPos = this.position();
        return data;
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 16, 1));
        this.goalSelector.addGoal(2, new ShootFireballGoal(this));
        this.goalSelector.addGoal(3, new SummonFireGoal(this));
        this.goalSelector.addGoal(4, new FlyAroundGoal(this));
    }

    public static AttributeSupplier.Builder createSunSpiritAttributes() {
        return AbstractValkyrie.createAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_FROZEN, false);
        this.entityData.define(DATA_BOSS_NAME, Component.literal("Sun Spirit"));
    }

    @Override
    public void tick() {
        this.setNoGravity(true);
        super.tick();
        if (this.getHealth() > 0) {
            double x = this.getX() + (this.random.nextFloat() - 0.5F) * this.random.nextFloat();
            double y = this.getBoundingBox().minY + this.random.nextFloat() - 0.5;
            double z = this.getZ() + (this.random.nextFloat() - 0.5F) * this.random.nextFloat();
            this.level.addParticle(ParticleTypes.FLAME, x, y, z, 0, -0.07500000298023224, 0);

            this.burnEntities();
        }
        this.setYRot(Mth.rotateIfNecessary(this.getYRot(), this.yHeadRot, 20));
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
        if (this.chatCooldown > 0) {
            this.chatCooldown--;
        }
        this.trackDungeon();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = super.hurt(source, amount);
        if (flag && !this.level.isClientSide) {
            FireMinion minion = new FireMinion(AetherEntityTypes.FIRE_MINION.get(), this.level);
            minion.setPos(this.position());
            this.level.addFreshEntity(minion);
        }
        this.velocity =  1 - this.getHealth() / 700;
        return flag;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return this.isRemoved() || source != DamageSource.OUT_OF_WORLD && !source.getMsgId().equals("ice_crystal");
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
    public void die(@Nonnull DamageSource cause) {
        if (!this.level.isClientSide) {
            this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.dead").withStyle(ChatFormatting.AQUA));
            this.level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> {
                aetherTime.setEternalDay(false);
                aetherTime.updateEternalDay();
            });
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(cause);
            }
        }
        super.die(cause);
    }

    @Override
    @Nonnull
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!this.level.isClientSide && !this.isBossFight()) {
            if (this.chatCooldown <= 0) {
                this.chatCooldown = 100;
                LazyOptional<AetherPlayer> aetherPlayer = player.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
                if (!AetherConfig.COMMON.repeat_sun_spirit_dialogue.get()) {
                    aetherPlayer.ifPresent(cap -> {
                        if (cap.hasSeenSunSpiritDialogue() && this.chatLine == 0) {
                            this.chatLine = 10;
                        }
                    });
                }
                switch (this.chatLine++) {
                    case 0 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line0").withStyle(ChatFormatting.RED));
                    case 1 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line1").withStyle(ChatFormatting.RED));
                    case 2 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line2").withStyle(ChatFormatting.RED));
                    case 3 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line3").withStyle(ChatFormatting.RED));
                    case 4 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line4").withStyle(ChatFormatting.RED));
                    case 5 -> {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line5.1").withStyle(ChatFormatting.RED));
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line5.2").withStyle(ChatFormatting.RED));
                    }
                    case 6 -> {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line6.1").withStyle(ChatFormatting.RED));
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line6.2").withStyle(ChatFormatting.RED));
                    }
                    case 7 -> {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line7.1").withStyle(ChatFormatting.RED));
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line7.2").withStyle(ChatFormatting.RED));
                    }
                    case 8 -> this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line8").withStyle(ChatFormatting.RED));
                    case 9 -> {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line9").withStyle(ChatFormatting.RED));
                        this.setBossFight(true);
                        aetherPlayer.ifPresent(cap -> cap.setSeenSunSpiritDialogue(true));
                    }
                    default -> {
                        this.chatWithNearby(Component.translatable("gui.aether.sun_spirit.line10").withStyle(ChatFormatting.RED));
                        this.chatLine = 9;
                    }
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * Sends a message to nearby players. Useful for boss fights.
     */
    protected void chatWithNearby(Component message) {
        this.level.getNearbyPlayers(NON_COMBAT, this, this.getBoundingBox().inflate(16)).forEach(player ->
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
        tag.putDouble("OriginX", this.originPos.x);
        tag.putDouble("OriginY", this.originPos.y);
        tag.putDouble("OriginZ", this.originPos.z);
        tag.putInt("ChatLine", this.chatLine);
        if (this.getDungeon() != null) {
            tag.put("Dungeon", this.getDungeon().addAdditionalSaveData());
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        Component name = Component.Serializer.fromJson(tag.getString("BossName"));
        if (name != null) {
            this.setBossName(name);
        }
        if (tag.contains("OriginX")) {
            this.originPos = new Vec3(tag.getDouble("OriginX"), tag.getDouble("OriginY"), tag.getDouble("OriginZ"));
        }
        if (tag.contains("ChatLine")) {
            this.chatLine = tag.getInt("ChatLine");
        }
        if (tag.contains("Dungeon") && tag.get("Dungeon") instanceof CompoundTag dungeonTag) {
            this.setDungeon(DungeonTracker.readAdditionalSaveData(dungeonTag, this));
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

    @Override
    public DungeonTracker<SunSpirit> getDungeon() {
        return this.goldDungeon;
    }

    @Override
    public void setDungeon(DungeonTracker<SunSpirit> dungeon) {
        this.goldDungeon = dungeon;
    }

    @Override
    public int getDeathScore() {
        return 0;
    }

    @Override
    public void reset() {
        this.setBossFight(false);
        this.setHealth(this.getMaxHealth());
    }

    /**
     * Sets the wanted movement direction of the sun spirit during the fight.
     */
    public static class FlyAroundGoal extends Goal {
        private final SunSpirit sunSpirit;
        private float rotation;
        private int courseChangeTimer;

        public FlyAroundGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.rotation = sunSpirit.random.nextFloat() * 360;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public void tick() {
            boolean changedCourse = this.outOfBounds();
            double x = Mth.sin(rotation * Mth.PI / 180F) * this.sunSpirit.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.sunSpirit.velocity;
            double z = -Mth.cos(rotation * Mth.PI / 180F) * this.sunSpirit.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.sunSpirit.velocity;
            this.sunSpirit.setDeltaMovement(x,
                    0,
                    z);
            if (changedCourse || ++this.courseChangeTimer >= 20) {
                if (this.sunSpirit.random.nextInt(3) == 0) {
                    this.rotation += this.sunSpirit.random.nextFloat() - this.sunSpirit.random.nextFloat() * 60;
                }
                this.rotation = Mth.wrapDegrees(this.rotation);
                this.courseChangeTimer = 0;
            }
        }

        protected boolean outOfBounds() {
            boolean flag = false;
            if ((this.sunSpirit.getDeltaMovement().x >= 0 && this.sunSpirit.getX() >= this.sunSpirit.originPos.x + this.sunSpirit.xMax) ||
                    (this.sunSpirit.getDeltaMovement().x <= 0 && this.sunSpirit.getX() <= this.sunSpirit.originPos.x - this.sunSpirit.xMax)) {
                this.rotation = 360 - this.rotation;
                flag = true;
            }
            if ((this.sunSpirit.getDeltaMovement().z >= 0 && this.sunSpirit.getZ() >= this.sunSpirit.originPos.z + this.sunSpirit.zMax) ||
                    (this.sunSpirit.getDeltaMovement().z <= 0 && this.sunSpirit.getZ() <= this.sunSpirit.originPos.z - this.sunSpirit.zMax)) {
                this.rotation = 180 - this.rotation;
                flag = true;
            }
            return flag;
        }

        @Override
        public boolean canUse() {
            return this.sunSpirit.isBossFight();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
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
            this.sunSpirit.setDeltaMovement(Vec3.ZERO);
            this.sunSpirit.setPos(this.sunSpirit.originPos.x, this.sunSpirit.originPos.y, this.sunSpirit.originPos.z);
        }
    }

    /**
     * This goal makes the sun spirit shoot fire crystals and ice crystals randomly. It shoots more crystals as
     * its health gets lower.
     */
    public static class ShootFireballGoal extends Goal {
        private final SunSpirit sunSpirit;
        private int shootInterval;
        private int crystalCount = 3;

        public ShootFireballGoal(SunSpirit sunSpirit) {
            this.sunSpirit = sunSpirit;
            this.shootInterval = (int) (28 + sunSpirit.getHealth() / 4);
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
        private final SunSpirit sunSpirit;
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
