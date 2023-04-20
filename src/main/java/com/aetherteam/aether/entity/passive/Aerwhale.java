package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.capability.player.AetherPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Aerwhale extends FlyingMob {
    public static final EntityDataAccessor<Float> DATA_X_ROT_ID = SynchedEntityData.defineId(Aerwhale.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> DATA_Y_ROT_ID = SynchedEntityData.defineId(Aerwhale.class, EntityDataSerializers.FLOAT);
    private static final UUID MOUNT_HEIGHT_UUID = UUID.fromString("B2D5A57A-8DA5-4127-8091-14A4CCD000F1");
    private static final UUID DEFAULT_HEIGHT_UUID = UUID.fromString("31535561-F99D-4E14-ACE7-F636EAAD6180");

    public Aerwhale(EntityType<? extends Aerwhale> type, Level level) {
        super(type, level);
        this.lookControl = new BlankLookControl(this);
        this.moveControl = new AerwhaleMoveControl(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new SetTravelCourseGoal(this));
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FLYING_SPEED, 0.2);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_X_ROT_ID, this.getXRot());
        this.entityData.define(DATA_Y_ROT_ID, this.getYRot());
    }

    public static boolean checkAerwhaleSpawnRules(EntityType<? extends Aerwhale> aerwhale, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return random.nextInt(40) == 0 && level.getFluidState(pos).is(Fluids.EMPTY) && level.getRawBrightness(pos, 0) > 8 && checkMobSpawnRules(aerwhale, level, reason, pos, random);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.setXRot(this.getXRotData());
        this.setYRot(this.getYRotData());
        this.yBodyRot = this.getYRotData();
        this.yHeadRot = this.getYRotData();
    }

    /**
     * The purpose of this method override is to fix the weird movement from flying mobs.
     */
    @Override
    public void travel(@Nonnull Vec3 positionIn) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            List<Entity> passengers = this.getPassengers();
            if (!passengers.isEmpty()) {
                Entity entity = passengers.get(0);
                if (entity instanceof Player player) {
                    this.setYRot(player.getYRot() + 90F);
                    this.yRotO = this.getYRot();
                    this.setXRot(-player.getXRot());
                    this.xRotO = this.getXRot() * 0.5F;

                    this.yHeadRot = player.yHeadRot;

                    positionIn = new Vec3(player.xxa, 0.0, (player.zza <= 0.0F)? player.zza * 0.25F : player.zza);

                    if (AetherPlayer.get(player).map(AetherPlayer::isJumping).orElse(false)) {
                        this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
                    } else {
                        double d0 = Math.toRadians(this.getYRot());
                        double d1 = Math.toRadians(-player.getXRot());
                        double d2 = Math.cos(d1);
                        this.setDeltaMovement(
                                0.98 * (this.getDeltaMovement().x + 0.05 * Math.cos(d0) * d2),
                                0.98 * (this.getDeltaMovement().y + 0.02 * Math.sin(d1)),
                                0.98 * (this.getDeltaMovement().z + 0.05 * Math.sin(d0) * d2)
                        );
                    }

                    AttributeInstance stepHeight = this.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                    if (stepHeight != null) {
                        if (stepHeight.hasModifier(this.getDefaultStepHeightModifier())) {
                            stepHeight.removeModifier(this.getDefaultStepHeightModifier());
                        }
                        if (!stepHeight.hasModifier(this.getMountStepHeightModifier())) {
                            stepHeight.addTransientModifier(this.getMountStepHeightModifier());
                        }
                    }

                    if (!this.level.isClientSide) {
                        super.travel(positionIn);
                    }

                    double d0 = this.getX() - this.xo;
                    double d1 = this.getZ() - this.zo;
                    float f4 = 4.0F * Mth.sqrt((float) (d0*d0 + d1*d1));

                    if (f4 > 1.0F) {
                        f4 = 1.0F;
                    }
                    this.walkAnimation.update(f4, 0.4F);
                }
            } else {
                AttributeInstance stepHeight = this.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
                if (stepHeight != null) {
                    if (stepHeight.hasModifier(this.getMountStepHeightModifier())) {
                        stepHeight.removeModifier(this.getMountStepHeightModifier());
                    }
                    if (!stepHeight.hasModifier(this.getDefaultStepHeightModifier())) {
                        stepHeight.addTransientModifier(this.getDefaultStepHeightModifier());
                    }
                }
                super.travel(positionIn);
            }
        }
    }

    @Override
    protected float getFlyingSpeed() {
        return this.isVehicle() ? this.getSpeed() * 0.6F : 0.02F;
    }

    @Override
    public void tick() {
        super.tick();
        this.clearFire();
    }

    @Override
    @Nonnull
    protected InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (player.getUUID().equals(UUID.fromString("031025bd-0a15-439b-9c55-06a20d0de76f"))) { // SerenityLowes
            player.startRiding(this);
            if (!this.level.isClientSide) {
                MutableComponent msg = Component.literal("Serenity is the queen of W(h)ales!!");
                player.level.players().forEach(p -> p.sendSystemMessage(msg));
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public void setXRotData(float rot) {
        this.entityData.set(DATA_X_ROT_ID, Mth.wrapDegrees(rot));
    }

    public float getXRotData() {
        return this.entityData.get(DATA_X_ROT_ID);
    }

    public void setYRotData(float rot) {
        this.entityData.set(DATA_Y_ROT_ID, Mth.wrapDegrees(rot));
    }

    public float getYRotData() {
        return this.entityData.get(DATA_Y_ROT_ID);
    }

    public AttributeModifier getMountStepHeightModifier() {
        return new AttributeModifier(MOUNT_HEIGHT_UUID, "Mounted step height increase", 0.4, AttributeModifier.Operation.ADDITION);
    }

    public AttributeModifier getDefaultStepHeightModifier() {
        return new AttributeModifier(DEFAULT_HEIGHT_UUID, "Default step height increase", -0.1, AttributeModifier.Operation.ADDITION);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_AERWHALE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn) {
        return AetherSoundEvents.ENTITY_AERWHALE_DEATH.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherSoundEvents.ENTITY_AERWHALE_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return 2.0F;
    }

    @Override
    public int getExperienceReward() {
        return 1 + this.level.random.nextInt(3);
    }

    public static class SetTravelCourseGoal extends Goal {
        private final Mob mob;
        public SetTravelCourseGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }
        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canUse() {
            MoveControl moveControl = this.mob.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double d0 = moveControl.getWantedX() - this.mob.getX();
                double d1 = moveControl.getWantedY() - this.mob.getY();
                double d2 = moveControl.getWantedZ() - this.mob.getZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            RandomSource random = this.mob.getRandom();
            // Set the x, y, and z targets to n * 16, with n being a random number between -1 and 1.
            double x = (random.nextFloat() * 2F - 1F) * 16;
            double z = (random.nextFloat() * 2F - 1F) * 16;
            double y = this.mob.getY() + (random.nextFloat() * 2F - 1F) * 16;

            // Then increase the distance by 32.
            x = x >= 0 ? x + 32 : x - 32;
            z = z >= 0 ? z + 32 : z - 32;

            x += this.mob.getX();
            z += this.mob.getZ();

            // Make sure the mob doesn't fly out of the world.
            y = Mth.clamp(y, this.mob.level.getMinBuildHeight(), this.mob.level.getMaxBuildHeight());

            this.mob.getMoveControl().setWantedPosition(x, y, z, 1.0);
        }
    }

    /**
     * Custom aerwhale move controller to help with keeping a smooth travel course.
     */
    public static class AerwhaleMoveControl extends MoveControl {
        protected final Aerwhale mob;

        public AerwhaleMoveControl(Aerwhale pMob) {
            super(pMob);
            this.mob = pMob;
        }

        @Override
        public void tick() {
            if (this.mob.isVehicle()) {
                return;
            }
            double x = this.getWantedX() - this.mob.getX();
            double y = this.getWantedY() - this.mob.getY();
            double z = this.getWantedZ() - this.mob.getZ();
            double distance = Math.sqrt(x * x + z * z);
            if (distance < 3 || this.isColliding(new Vec3(x, y, z).normalize())) {
                this.operation = Operation.WAIT;
            }

            float xRotTarget = (float) (Mth.atan2(y, distance) * (180F / (float) Math.PI)); // Pitch
            float xRot = Mth.wrapDegrees(this.mob.getXRot());
            xRot = Mth.approachDegrees(xRot, xRotTarget, 0.2F);
            this.mob.setXRot(xRot);
            this.mob.setXRotData(this.mob.getXRot());

            float yRotTarget = Mth.wrapDegrees((float) Mth.atan2(z, x) * (180F / (float) Math.PI)); // Yaw
            float yRot = Mth.wrapDegrees(this.mob.getYRot() + 90F);
            yRot = Mth.approachDegrees(yRot, yRotTarget, 0.5F);
            this.mob.setYRot(yRot - 90F);
            this.mob.setYRotData(this.mob.getYRot());
            this.mob.yBodyRot = yRot;
            this.mob.yHeadRot = yRot;

            x = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.cos(yRot * ((float) Math.PI / 180F));
            y = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.sin(xRot * ((float) Math.PI / 180F));
            z = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.sin(yRot * ((float) Math.PI / 180F));

            Vec3 motion = new Vec3(x, y, z);
            this.mob.setDeltaMovement(motion);
        }

        /**
         * Checks if entity bounding box is not colliding with terrain
         */
        private boolean isColliding(Vec3 pos) {
            AABB axisalignedbb = this.mob.getBoundingBox();

            for (int i = 1; i < 7; ++i) {
                axisalignedbb = axisalignedbb.move(pos);
                if (!this.mob.level.noCollision(this.mob, axisalignedbb)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * This is needed to allow the mob to look up and down in AerwhaleMoveControl.
     */
    public static class BlankLookControl extends LookControl {

        public BlankLookControl(Mob pMob) {
            super(pMob);
        }

        @Override
        public void tick() {}
    }
}