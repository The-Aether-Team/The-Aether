package com.gildedgames.aether.entity.monster;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.entity.MountableMob;
import com.gildedgames.aether.entity.ai.goal.target.NearestTaggedTargetGoal;
import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.client.SwetAttackPacket;
import com.gildedgames.aether.network.packet.client.SwetDeathParticlePacket;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

public class Swet extends Slime implements MountableMob {
    private static final EntityDataAccessor<Boolean> DATA_PLAYER_JUMPED_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_MOUNT_JUMPING_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_MID_JUMP_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_DEAD_IN_WATER_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_WATER_DAMAGE_SCALE_ID = SynchedEntityData.defineId(Swet.class, EntityDataSerializers.FLOAT);

    public boolean wasOnGround;
    public int jumpTimer;
    public float swetHeight = 1.0F;
    public float oSwetHeight = 1.0F;
    public float swetWidth = 1.0F;
    public float oSwetWidth = 1.0F;

    public Swet(EntityType<? extends Swet> type, Level level) {
        super(type, level);
        this.moveControl = new Swet.MoveHelperController(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ConsumeGoal(this));
        this.goalSelector.addGoal(1, new HuntGoal(this));
        this.goalSelector.addGoal(2, new RandomFacingGoal(this));
        this.goalSelector.addGoal(4, new HopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, (target) -> !this.isFriendlyTowardEntity(target) && !(target.getRootVehicle() instanceof Swet)));
        this.targetSelector.addGoal(2, new NearestTaggedTargetGoal(this, AetherTags.Entities.SWET_TARGETS, true, (target) -> !this.isFriendlyTowardEntity(target) && !(target.getRootVehicle() instanceof Swet)));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 25.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PLAYER_JUMPED_ID, false);
        this.entityData.define(DATA_MOUNT_JUMPING_ID, false);
        this.entityData.define(DATA_MID_JUMP_ID, false);
        this.entityData.define(DATA_DEAD_IN_WATER_ID, false);
        this.entityData.define(DATA_WATER_DAMAGE_SCALE_ID, 0.0F);
    }

    @Override
    public void onSyncedDataUpdated(@Nonnull EntityDataAccessor<?> dataAccessor) {
        if (DATA_WATER_DAMAGE_SCALE_ID.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    public static boolean checkSwetSpawnRules(EntityType<? extends Swet> swet, LevelAccessor level, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && level.getBlockState(pos.below()).is(AetherTags.Blocks.SWET_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getTarget() != null) {
            if (this.hasPrey() || this.isFriendlyTowardEntity(this.getTarget()) || this.getTarget().getRootVehicle() instanceof Swet) {
                this.setTarget(null);
            }
        }
    }

    @Override
    public void tick() {
        if (this.isInWater()) {
            this.dissolveSwetInWater();
        }

        if (this.getDeadInWater()) {
            if (!(this.getWaterDamageScale() >= 1.0F)) {
                this.setWaterDamageScale(this.getWaterDamageScale() + 0.02F);
            }
        }

        this.riderTick(this);
        super.tick();

        if (!this.hasPrey()) {
            double d = (float) this.getX() + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F;
            double d1 = (float) this.getY() + this.getBbHeight();
            double d2 = (float) this.getZ() + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F;
            this.level.addParticle(ParticleTypes.SPLASH, d, d1 - 0.25, d2, 0.0, 0.0, 0.0);
        }

        this.setMidJump(!this.onGround);
        if (this.level.isClientSide) {
            this.oSwetHeight = this.swetHeight;
            this.oSwetWidth = this.swetWidth;
            if (this.getMidJump()) {
                this.jumpTimer++;
            } else {
                this.jumpTimer = 0;
            }
            if (this.onGround) {
                this.swetHeight = this.swetHeight < 1.0F ? this.swetHeight + 0.25F : 1.0F;
                this.swetWidth = this.swetWidth > 1.0F ? this.swetWidth - 0.25F : 1.0F;
            } else {
                this.swetHeight = 1.425F;
                this.swetWidth = 0.875F;

                if (this.getJumpTimer() > 3) {
                    float scale = Math.min(this.getJumpTimer(), 10);
                    this.swetHeight -= 0.05F * scale;
                    this.swetWidth += 0.05F * scale;
                }
            }
        }

        if (this.isFriendly())
            this.resetFallDistance();

        this.wasOnGround = this.onGround;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity livingEntity && this.isFriendlyTowardEntity(livingEntity)) {
            return livingEntity;
        }
        return null;
    }

    @Override
    public void travel(@Nonnull Vec3 motion) {
        this.travel(this, motion);
        if (this.isAlive()) {
            LivingEntity entity = this.getControllingPassenger();
            if (this.isVehicle() && entity != null) {
                if (this.onGround && !this.getPlayerJumped() && (this.getDeltaMovement().x != 0 || this.getDeltaMovement().z != 0)) {
                    this.setDeltaMovement(this.getDeltaMovement().x(), 0.42F, this.getDeltaMovement().z);
                }
                this.resetFallDistance();
            }
        }
    }

    @Override
    public void travelWithInput(Vec3 motion) {
        super.travel(motion);
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(Player player, @Nonnull InteractionHand hand) {
        if (!this.level.isClientSide) {
            if (!this.hasPrey() && this.isFriendlyTowardEntity(player)) {
                this.capturePrey(player);
            }
        }
        return InteractionResult.PASS;
    }

    public void capturePrey(LivingEntity livingEntity) {
        this.playSound(AetherSoundEvents.ENTITY_SWET_ATTACK.get(), 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        this.xRotO = this.getXRot();
        this.setXRot(livingEntity.getXRot());
        this.yRotO = this.getYRot();
        this.setYRot(livingEntity.getYRot());
        this.setDeltaMovement(livingEntity.getDeltaMovement());

        this.setPos(livingEntity.getX(), livingEntity.getY() + 0.01, livingEntity.getZ());

        livingEntity.startRiding(this, true);

        this.setXRot(this.random.nextFloat() * 360.0F);
    }

    public boolean hasPrey() {
        return getFirstPassenger() != null;
    }

    public boolean isFriendlyTowardEntity(LivingEntity entity) {
        return EquipmentUtil.hasSwetCape(entity);
    }

    public void dissolveSwetInWater() {
        this.dissolveSwet();
        if (!this.getDeadInWater()) {
            this.setDeadInWater(true);
        }
    }

    public void dissolveSwetNormally() {
        this.dissolveSwet();
        this.discard();
    }

    public void dissolveSwet() {
        if (this.level instanceof ServerLevel level) {
            AetherPacketHandler.sendToNear(new SwetDeathParticlePacket(this.getId()), this.getX(), this.getY(), this.getZ(), 10.0, level.dimension());
        }
    }

    @Override
    protected void tickDeath() {
        //This method changed death mechanic when dead in water.
        if (this.getDeadInWater()) {
            if (this.getWaterDamageScale() >= 1.0F && !this.level.isClientSide()) {
                this.level.broadcastEntityEvent(this, (byte) 60);
                this.remove(Entity.RemovalReason.KILLED);
            }
        } else {
            super.tickDeath();
        }
    }

    @Override
    public boolean isDeadOrDying() {
        return super.isDeadOrDying() || this.getDeadInWater();
    }

    public boolean isFriendly() {
        return this.getControllingPassenger() != null;
    }

    public int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    public void setMidJump(boolean flag) {
        this.entityData.set(DATA_MID_JUMP_ID, flag);
    }

    public boolean getMidJump() {
        return this.entityData.get(DATA_MID_JUMP_ID);
    }

    public void setDeadInWater(boolean flag) {
        this.entityData.set(DATA_DEAD_IN_WATER_ID, flag);
    }

    public boolean getDeadInWater() {
        return this.entityData.get(DATA_DEAD_IN_WATER_ID);
    }

    public void setWaterDamageScale(float scale) {
        float i = Mth.clamp(scale, 0.0F, 1.0F);
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MAX_HEALTH);
        if (attributeInstance != null) {
            attributeInstance.setBaseValue(attributeInstance.getBaseValue() * (1.0F - i));
            if (attributeInstance.getBaseValue() < this.getHealth()) {
                this.setHealth(this.getMaxHealth());
            }
        }
        this.entityData.set(DATA_WATER_DAMAGE_SCALE_ID, i);
    }

    public float getWaterDamageScale() {
        return this.entityData.get(DATA_WATER_DAMAGE_SCALE_ID);
    }

    public int getJumpTimer() {
        return jumpTimer;
    }

    @Override
    public float getJumpPower() {
        return 0.5F;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return AetherSoundEvents.ENTITY_SWET_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_SWET_DEATH.get();
    }

    @Override
    protected SoundEvent getSquishSound() {
        return AetherSoundEvents.ENTITY_SWET_SQUISH.get();
    }

    /**
     * The player can attack the swet to try to kill it before they finish the attack.
     */
    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Nonnull
    @Override
    public Vec3 getDismountLocationForPassenger(@Nonnull LivingEntity livingEntity) {
        if (this.isFriendlyTowardEntity(livingEntity)) {
            return super.getDismountLocationForPassenger(livingEntity);
        } else {
            return this.position();
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("WaterDamageScale", this.getWaterDamageScale());
        tag.putBoolean("DeadInWater", this.getDeadInWater());
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("WaterDamageScale")) {
            this.setWaterDamageScale(tag.getFloat("WaterDamageScale"));
        }
        if (tag.contains("DeadInWater")) {
            this.setDeadInWater(tag.getBoolean("DeadInWater"));
        }
    }

    @Override
    public float getSteeringSpeed() {
        return 0.084F;
    }
    @Override
    public double getMountJumpStrength() {
        return 1.2;
    }

    @Override
    public double jumpFactor() {
        return this.getBlockJumpFactor();
    }


    @Override
    public boolean getPlayerJumped() {
        return this.entityData.get(DATA_PLAYER_JUMPED_ID);
    }

    @Override
    public void setPlayerJumped(boolean playerJumped) {
        this.entityData.set(DATA_PLAYER_JUMPED_ID, playerJumped);
    }

    @Override
    public boolean isMountJumping() {
        return this.entityData.get(DATA_MOUNT_JUMPING_ID);
    }

    @Override
    public void setMountJumping(boolean isMountJumping) {
        this.entityData.set(DATA_MOUNT_JUMPING_ID, isMountJumping);
    }

    @Override
    public float getScale() {
        return super.getScale() - super.getScale() * this.getWaterDamageScale();
    }

    @Nonnull
    @Override
    public EntityDimensions getDimensions(@Nonnull Pose pose) {
        return this.getType().getDimensions().scale(getScale());
    }

    @Override
    public boolean canJump() {
        return this.isOnGround() && this.isFriendly();
    }

    @Override
    public int getSize() {
        return this.isVehicle() ? 2 : 1;
    }

    @Override
    public void setSize(int size, boolean resetHealth) {
        // We don't use the size data, so we don't need this method to run from Slime.
    }

    @Override
    protected boolean isDealsDamage() {
        return false;
    }

    public static class ConsumeGoal extends Goal {
        private final Swet swet;
        private int jumps = 0;

        private float chosenDegrees = 0;

        public ConsumeGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.swet.hasPrey()
                    && this.swet.getPassengers().get(0) instanceof LivingEntity passenger
                    && !this.swet.isFriendlyTowardEntity(passenger);
        }

        @Override
        public void tick() {
            if (this.jumps <= 3) {
                if (this.swet.onGround) {
                    // This is to make sure the swet actually touches the ground on the client.
                    AetherPacketHandler.sendToNear(new SwetAttackPacket(this.swet.getId(), this.swet.getX(), this.swet.getY(), this.swet.getZ()), this.swet.getX(), this.swet.getY(), this.swet.getZ(), 50.0D, this.swet.getLevel().dimension());

                    this.swet.playSound(AetherSoundEvents.ENTITY_SWET_JUMP.get(), 1.0F, ((this.swet.getRandom().nextFloat() - this.swet.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);

                    this.chosenDegrees = (float) this.swet.getRandom().nextInt(360);

                    if (this.jumps == 0) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 0.64999999403953552D, 0));
                    } else if (this.jumps == 1) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 0.74999998807907104D, 0));
                    } else if (this.jumps == 2) {
                        this.swet.setDeltaMovement(this.swet.getDeltaMovement().add(0, 1.55D, 0));
                    } else {
                        this.swet.getPassengers().get(0).stopRiding();
                        this.swet.dissolveSwetNormally();
                    }

                    if (!this.swet.getMidJump()) {
                        this.jumps++;
                    }
                }

                if (!this.swet.wasOnGround) {
                    if (this.swet.getJumpTimer() < 6) {
                        if (this.jumps == 1) {
                            this.moveHorizontal(0.0F, 0.1F, this.chosenDegrees);
                        } else if (this.jumps == 2) {
                            this.moveHorizontal(0.0F, 0.15F, this.chosenDegrees);
                        } else if (this.jumps == 3) {
                            this.moveHorizontal(0.0F, 0.3F, this.chosenDegrees);
                        }
                    }
                }
            }
        }

        public void moveHorizontal(float strafe, float forward, float rotation) {
            float f = strafe * strafe + forward * forward;

            f = Mth.sqrt(f);
            if (f < 1.0F) f = 1.0F;
            strafe = strafe * f;
            forward = forward * f;
            float f1 = Mth.sin(rotation * 0.017453292F);
            float f2 = Mth.cos(rotation * 0.017453292F);

            this.swet.setDeltaMovement((strafe * f2 - forward * f1), this.swet.getDeltaMovement().y, (forward * f2 + strafe * f1));
        }
    }

    public static class HuntGoal extends Goal {
        private final Swet swet;

        public HuntGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.swet.getTarget();
            if (this.swet.hasPrey() || target == null || !target.isAlive() || this.swet.isFriendlyTowardEntity(target) || (target instanceof Player player && player.getAbilities().invulnerable)) {
                return false;
            } else {
                return this.swet.getMoveControl() instanceof Swet.MoveHelperController;
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.swet.getTarget();
            if (swet.hasPrey() || target == null || !target.isAlive()) {
                return false;
            } else if (target instanceof Player player && player.getAbilities().invulnerable) {
                return false;
            } else {
                return !this.swet.isFriendlyTowardEntity(target);
            }
        }

        @Override
        public void tick() {
            LivingEntity target = this.swet.getTarget();
            if (target != null) {
                this.swet.lookAt(target, 10.0F, 10.0F);
                ((Swet.MoveHelperController) this.swet.getMoveControl()).setDirection(this.swet.getYRot(), true);
                if (this.swet.getBoundingBox().intersects(target.getBoundingBox())) {
                    this.swet.capturePrey(target);
                }
            }
        }
    }

    /**
     * Classes down here are based off of the AI classes used in SlimeEntity.
     * @see net.minecraft.world.entity.monster.Slime
     */
    public static class RandomFacingGoal extends Goal {
        private final Swet swet;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public RandomFacingGoal(Swet swet) {
            this.swet = swet;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.swet.getTarget() == null && (this.swet.onGround || this.swet.isInWater() || this.swet.isInLava() || this.swet.hasEffect(MobEffects.LEVITATION)) && this.swet.getMoveControl() instanceof MoveHelperController;
        }

        public void tick() {
            MoveHelperController moveHelperController = (MoveHelperController) this.swet.getMoveControl();
            float rot = moveHelperController.yRot;
            Vec3 offset = new Vec3(-Math.sin(rot * ((float) Math.PI / 180)) * 2, 0.0, Math.cos(rot * ((float) Math.PI / 180)) * 2);
            BlockPos pos = new BlockPos(this.swet.position().add(offset));
            if (this.swet.level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) < pos.getY() - this.swet.getMaxFallDistance()) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.swet.getRandom().nextInt(60));
                this.chosenDegrees += 180;
                moveHelperController.setCanJump(false);
            } else {
                if (--this.nextRandomizeTime <= 0) {
                    this.nextRandomizeTime = this.adjustedTickDelay(40 + this.swet.getRandom().nextInt(60));
                    this.chosenDegrees = (float) this.swet.getRandom().nextInt(360);
                }
                moveHelperController.setCanJump(true);
            }
            moveHelperController.setDirection(this.chosenDegrees, false);
        }
    }

    public static class HopGoal extends Goal {
        private final Swet swet;

        public HopGoal(Swet swetEntity) {
            this.swet = swetEntity;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !this.swet.isPassenger() && this.swet.getMoveControl() instanceof MoveHelperController moveHelperController && moveHelperController.canJump;
        }

        public void tick() {
            ((MoveHelperController) this.swet.getMoveControl()).setWantedMovement(1.0);
        }
    }

    public static class MoveHelperController extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final Swet swet;
        private boolean isAggressive;
        private boolean canJump;

        public MoveHelperController(Swet swet) {
            super(swet);
            this.swet = swet;
            this.yRot = 180.0F * swet.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRotIn, boolean isAggressiveIn) {
            this.yRot = yRotIn;
            this.isAggressive = isAggressiveIn;
        }

        public void setWantedMovement(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        public void setCanJump(boolean canJump) {
            this.canJump = canJump;
        }

        public void tick() {
            if (this.swet.isFriendly()) {
                return;
            }
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.isOnGround()) {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));

                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.swet.getJumpDelay();

                        if (this.isAggressive) {
                            this.jumpDelay /= 6;
                        }

                        this.swet.getJumpControl().jump();

                        this.swet.playSound(AetherSoundEvents.ENTITY_SWET_JUMP.get(), 1.0F, ((this.swet.random.nextFloat() - this.swet.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    } else {
                        this.swet.xxa = 0.0F;
                        this.swet.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }
}