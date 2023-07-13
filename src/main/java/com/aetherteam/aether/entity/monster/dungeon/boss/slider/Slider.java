package com.aetherteam.aether.entity.monster.dungeon.boss.slider;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.dungeon.boss.BossNameGenerator;
import com.aetherteam.aether.api.BossRoomTracker;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.BossMob;
import com.aetherteam.aether.entity.ai.controller.BlankMoveControl;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.BossInfoPacket;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Slider extends PathfinderMob implements BossMob<Slider>, Enemy, IEntityAdditionalSpawnData {
    public static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_X_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_HURT_ANGLE_Z_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);

    private BossRoomTracker<Slider> bronzeDungeon;
    private final ServerBossEvent bossFight;

    private int chatCooldown;

    public Slider(EntityType<? extends Slider> entityType, Level level) {
        super(entityType, level);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setVisible(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        this.moveControl = new BlankMoveControl(this);
        this.setPersistenceRequired();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return SliderAi.makeBrain(dynamic);
    }

    /**
     * Generates a name for the boss and adjusts its position.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.alignSpawnPos();
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        this.setBossName(BossNameGenerator.generateSliderName());
        return data;
    }

    /**
     * Aligns the slider with the blocks below it
     */
    protected void alignSpawnPos() {
        this.moveTo(Mth.floor(this.getX()), this.getY(), Mth.floor(this.getZ()));
    }

    public static AttributeSupplier.Builder createSliderAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AWAKE_ID, false);
        this.entityData.define(DATA_BOSS_NAME_ID, Component.literal("Slider"));
        this.entityData.define(DATA_HURT_ANGLE_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_X_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_Z_ID, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isAwake() || (this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        this.evaporate();

        if (this.getChatCooldown() > 0) {
            this.chatCooldown--;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
        Brain<Slider> brain = (Brain<Slider>) this.getBrain();
        brain.tick((ServerLevel)this.level, this);
        SliderAi.updateActivity(this);
        SliderAi.tick(this);
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            super.hurt(source, amount);
        } else if (source.getDirectEntity() instanceof LivingEntity attacker && this.level.getDifficulty() != Difficulty.PEACEFUL) {
            if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) {
                if (attacker.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
                    if (super.hurt(source, amount) && this.getHealth() > 0) {
                        if (!this.isBossFight()) {
                            this.start();
                        }
                        this.setDeltaMovement(this.getDeltaMovement().scale(0.75F));

                        double a = Math.abs(this.position().x() - attacker.position().x());
                        double c = Math.abs(this.position().z() - attacker.position().z());
                        if (a > c) {
                            this.setHurtAngleZ(1);
                            this.setHurtAngleX(0);
                            if (this.position().x() > attacker.position().x()) {
                                this.setHurtAngleZ(-1);
                            }
                        } else {
                            this.setHurtAngleX(1);
                            this.setHurtAngleZ(0);
                            if (this.position().z() > attacker.position().z()) {
                                this.setHurtAngleX(-1);
                            }
                        }
                        this.setHurtAngle(0.7F - (this.getHealth() / 875.0F));

                        SliderAi.wasHurtBy(this, attacker, amount);

                        return true;
                    }
                } else {
                    if (!this.level.isClientSide && attacker instanceof Player player) {
                        if (this.getChatCooldown() <= 0) {
                            this.displayInvalidToolMessage(player);
                            this.setChatCooldown(15);
                            return false;
                        }
                    }
                }
            } else {
                if (!this.level.isClientSide && attacker instanceof Player player) {
                    if (this.getChatCooldown() <= 0) {
                        this.displayTooFarMessage(player);
                        this.setChatCooldown(15);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void displayInvalidToolMessage(Player player) {
        player.sendSystemMessage(Component.translatable("gui.aether.slider.message.attack.invalid"));
    }

    @Override
    public void die(@Nonnull DamageSource damageSource) {
        this.setDeltaMovement(Vec3.ZERO);
        this.explode();
        if (this.level instanceof ServerLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(damageSource);
                this.tearDownRoom();
            }
        }
        super.die(damageSource);
    }

    private void start() {
        if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.closeRoom();
        }
    }

    private void evaporate() {
        if (ForgeEventFactory.getMobGriefingEvent(this.getLevel(), this)) {
            AABB entity = this.getBoundingBox();
            BlockPos min = BlockPos.containing(entity.minX - 1, entity.minY - 1, entity.minZ - 1);
            BlockPos max = BlockPos.containing(Math.ceil(entity.maxX - 1) + 1, Math.ceil(entity.maxY - 1) + 1, Math.ceil(entity.maxZ - 1) + 1);
            for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
                if (this.level.getBlockState(pos).getBlock() instanceof LiquidBlock) {
                    this.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    this.evaporateEffects(pos);
                } else if (!this.level.getFluidState(pos).isEmpty() && this.level.getBlockState(pos).hasProperty(BlockStateProperties.WATERLOGGED)) {
                    this.level.setBlockAndUpdate(pos, this.level.getBlockState(pos).setValue(BlockStateProperties.WATERLOGGED, false));
                    this.evaporateEffects(pos);
                }
            }
        }
    }

    private void evaporateEffects(BlockPos pos) {
        this.blockDestroySmoke(pos);
        this.level.playSound(null, pos, AetherSoundEvents.WATER_EVAPORATE.get(), SoundSource.BLOCKS, 0.5F, 2.6F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.8F);
    }

    private void stop() {
        this.setDeltaMovement(Vec3.ZERO);
    }

    public void reset() {
        this.stop();
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        this.setHealth(this.getMaxHealth());
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
            this.openRoom();
        }
    }

    /**
     * Called on every block in the dungeon when the boss is defeated.
     */
    @Override
    @Nullable
    public BlockState convertBlock(BlockState state) {
        if (state.is(AetherBlocks.LOCKED_CARVED_STONE.get())) {
            return AetherBlocks.CARVED_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.LOCKED_SENTRY_STONE.get())) {
            return AetherBlocks.SENTRY_STONE.get().defaultBlockState();
        }
        if (state.is(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get())) {
            return Blocks.AIR.defaultBlockState();
        }
        if (state.is(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get())) {
            return AetherBlocks.SKYROOT_TRAPDOOR.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING));
        }
        return null;
    }

    private void explode() {
        for (int i = 0; i < (this.getHealth() <= 0 ? 16 : 48); i++) {
            double x = this.position().x() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            double y = this.getBoundingBox().minY + 1.75 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            double z = this.position().z() + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
            this.level.addParticle(ParticleTypes.POOF, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    protected void blockDestroySmoke(BlockPos pos) {
        double a = pos.getX() + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        double b = pos.getY() + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        double c = pos.getZ() + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.POOF, a, b, c, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in order
     * to view its associated boss bar.
     */
    @Override
    public void startSeenByPlayer(@Nonnull ServerPlayer player) {
        super.startSeenByPlayer(player);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Display(this.bossFight.getId()), player);
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(player)) {
            this.bossFight.addPlayer(player);
        }
    }

    /**
     * Removes the given player from the list of players tracking this entity.
     */
    @Override
    public void stopSeenByPlayer(@Nonnull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        AetherPacketHandler.sendToPlayer(new BossInfoPacket.Remove(this.bossFight.getId()), player);
        this.bossFight.removePlayer(player);
    }

    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
        }
    }

    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
        }
    }

    public boolean isAwake() {
        return this.entityData.get(DATA_AWAKE_ID);
    }

    public void setAwake(boolean ready) {
        this.entityData.set(DATA_AWAKE_ID, ready);
    }

    @Override
    public Component getBossName() {
        return this.entityData.get(DATA_BOSS_NAME_ID);
    }

    @Override
    public void setBossName(Component component) {
        this.entityData.set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    public float getHurtAngleX() {
        return this.entityData.get(DATA_HURT_ANGLE_X_ID);
    }

    public void setHurtAngleX(float hurtAngleX) {
        this.entityData.set(DATA_HURT_ANGLE_X_ID, hurtAngleX);
    }

    public float getHurtAngleZ() {
        return this.entityData.get(DATA_HURT_ANGLE_Z_ID);
    }

    public void setHurtAngleZ(float hurtAngleZ) {
        this.entityData.set(DATA_HURT_ANGLE_Z_ID, hurtAngleZ);
    }

    public float getHurtAngle() {
        return this.entityData.get(DATA_HURT_ANGLE_ID);
    }

    public void setHurtAngle(float hurtAngle) {
        this.entityData.set(DATA_HURT_ANGLE_ID, hurtAngle);
    }

    @Override
    public BossRoomTracker<Slider> getDungeon() {
        return this.bronzeDungeon;
    }

    @Override
    public void setDungeon(BossRoomTracker<Slider> dungeon) {
        this.bronzeDungeon = dungeon;
    }

    @Override
    public int getDeathScore() {
        return this.deathScore;
    }

    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }

    public boolean isCritical() {
        return this.getHealth() <= 100;
    }

    public int calculateMoveDelay() {
        return this.isCritical() ? 1 + this.random.nextInt(10) : 2 + this.random.nextInt(14);
    }

    protected float getVelocityIncrease() {
        return this.isCritical() ? 0.045F - (this.getHealth()/10000) : 0.035F - (this.getHealth()/30000);
    }

    protected float getMaxVelocity() {
        return 2.5F;
    }

    protected SoundEvent getAwakenSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AWAKEN.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_SLIDER_AMBIENT.get();
    }

    public SoundEvent getCollideSound() {
        return AetherSoundEvents.ENTITY_SLIDER_COLLIDE.get();
    }

    public SoundEvent getMoveSound() {
        return AetherSoundEvents.ENTITY_SLIDER_MOVE.get();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SLIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SLIDER_DEATH.get();
    }

    @Nonnull
    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.setBossName(name);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target.canBeSeenAsEnemy();
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {

    }

    @Override
    public void push(double x, double y, double z) {

    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public float getYRot() {
        return 0;
    }

    @Override
    protected boolean canRide(@Nonnull Entity vehicle) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isAwake();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldDiscardFriction() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isFullyFrozen() {
        return false;
    }

    // The slider should not be making footstep sounds.
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addBossSaveData(tag);
        tag.putBoolean("Awake", this.isAwake());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        if (tag.contains("Awake")) {
            this.setAwake(tag.getBoolean("Awake"));
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();
        this.addBossSaveData(tag);
        buffer.writeNbt(tag);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        CompoundTag tag = additionalData.readNbt();
        if (tag != null) {
            this.readBossSaveData(tag);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getChatCooldown() {
        return this.chatCooldown;
    }

    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }
}