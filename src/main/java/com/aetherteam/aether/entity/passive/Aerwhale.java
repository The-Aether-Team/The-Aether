package com.aetherteam.aether.entity.passive;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherSoundEvents;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Aerwhale extends FlyingMob {
    private static final EntityDataAccessor<Float> DATA_X_ROT_O_ID = SynchedEntityData.defineId(Aerwhale.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_X_ROT_ID = SynchedEntityData.defineId(Aerwhale.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_Y_ROT_ID = SynchedEntityData.defineId(Aerwhale.class, EntityDataSerializers.FLOAT);

    public Aerwhale(EntityType<? extends Aerwhale> type, Level level) {
        super(type, level);
        this.lookControl = new BlankLookControl(this);
        this.moveControl = new AerwhaleMoveControl(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new SetTravelCourseGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FLYING_SPEED, 0.2)
                .add(NeoForgeMod.STEP_HEIGHT.value(), 0.4);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_X_ROT_O_ID, this.getXRot());
        this.getEntityData().define(DATA_X_ROT_ID, this.getXRot());
        this.getEntityData().define(DATA_Y_ROT_ID, this.getYRot());
    }

    /**
     * Aerwhales can spawn if {@link Mob#checkMobSpawnRules(EntityType, LevelAccessor, MobSpawnType, BlockPos, RandomSource)} is true, if they aren't spawning in fluid,
     * if they are spawning at a light level above 8, if they are spawning in view of the sky, and they spawn with a random chance of 1/40.
     * @param aerwhale The {@link Aerwhale} {@link EntityType}.
     * @param level The {@link LevelAccessor}.
     * @param reason The {@link MobSpawnType} reason.
     * @param pos The spawn {@link BlockPos}.
     * @param random The {@link RandomSource}.
     * @return Whether this entity can spawn, as a {@link Boolean}.
     */
    public static boolean checkAerwhaleSpawnRules(EntityType<? extends Aerwhale> aerwhale, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(aerwhale, level, reason, pos, random)
                && level.getFluidState(pos).is(Fluids.EMPTY)
                && level.getRawBrightness(pos, 0) > 8
                && level.canSeeSky(pos)
                && (reason != MobSpawnType.NATURAL || random.nextInt(40) == 0);
    }

    /**
     * Sets entity rotations from rotation data.
     */
    @Override
    public void aiStep() {
        super.aiStep();
        this.setXRot(this.getXRotData());
        this.setYRot(this.getYRotData());
        this.setYBodyRot(this.getYRotData());
        this.setYHeadRot(this.getYRotData());
    }

    /**
     * The purpose of this method override is to fix the weird movement from flying mobs.
     * @param vector The {@link Vec3} for travel movement.
     */
    @Override
    public void travel(Vec3 vector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            List<Entity> passengers = this.getPassengers();
            if (!passengers.isEmpty()) {
                Entity entity = passengers.get(0);
                if (entity instanceof Player player) {
                    this.setYRot(player.getYRot() + 90F);
                    this.yRotO = this.getYRot();
                    this.setXRot(-player.getXRot());
                    this.xRotO = this.getXRot() * 0.5F;
                    this.setYHeadRot(player.yHeadRot);

                    vector = new Vec3(player.xxa, 0.0, (player.zza <= 0.0F)? player.zza * 0.25F : player.zza);

                    if (AetherPlayer.get(player).map(AetherPlayer::isJumping).orElse(false)) {
                        this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
                    } else {
                        double d0 = Math.toRadians(this.getYRot());
                        double d1 = Math.toRadians(-player.getXRot());
                        double d2 = Math.cos(d1);
                        this.setDeltaMovement(
                                0.98 * (this.getDeltaMovement().x() + 0.05 * Math.cos(d0) * d2),
                                0.98 * (this.getDeltaMovement().y() + 0.02 * Math.sin(d1)),
                                0.98 * (this.getDeltaMovement().z() + 0.05 * Math.sin(d0) * d2)
                        );
                    }

                    if (!this.level().isClientSide()) {
                        super.travel(vector);
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
                super.travel(vector);
            }
        }
    }

    @Override
    public void tick() {
        this.setXRotOData(this.getXRotData());
        super.tick();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getUUID().equals(UUID.fromString("031025bd-0a15-439b-9c55-06a20d0de76f"))) { // SerenityLowes
            player.startRiding(this);
            if (!this.level().isClientSide()) {
                MutableComponent msg = Component.literal("Serenity is the queen of W(h)ales!!");
                player.level().players().forEach(p -> p.sendSystemMessage(msg));
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    /**
     * @return The old x-rotation {@link Float} data value.
     */
    public float getXRotOData() {
        return this.getEntityData().get(DATA_X_ROT_O_ID);
    }

    /**
     * Sets the old x-rotation data value, used for animation.
     * @param rot The {@link Float} value.
     */
    public void setXRotOData(float rot) {
        this.getEntityData().set(DATA_X_ROT_O_ID, Mth.wrapDegrees(rot));
    }

    /**
     * @return The x-rotation data {@link Float} data value.
     */
    public float getXRotData() {
        return this.getEntityData().get(DATA_X_ROT_ID);
    }

    /**
     * Sets the x-rotation data value, used for animation.
     * @param rot The {@link Float} value.
     */
    public void setXRotData(float rot) {
        this.getEntityData().set(DATA_X_ROT_ID, Mth.wrapDegrees(rot));
    }

    /**
     * @return The y-rotation {@link Float} data value.
     */
    public float getYRotData() {
        return this.getEntityData().get(DATA_Y_ROT_ID);
    }

    /**
     * Sets the y-rotation data value, used for animation.
     * @param rot The {@link Float} value.
     */
    public void setYRotData(float rot) {
        this.getEntityData().set(DATA_Y_ROT_ID, Mth.wrapDegrees(rot));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AetherSoundEvents.ENTITY_AERWHALE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
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

    /**
     * @return A {@link Float} for the midair speed of this entity.
     */
    @Override
    protected float getFlyingSpeed() {
        return this.isVehicle() ? this.getSpeed() * 0.6F : 0.02F;
    }

    /**
     * [CODE COPY] - {@link Animal#getExperienceReward()}.
     */
    @Override
    public int getExperienceReward() {
        return 1 + this.level().getRandom().nextInt(3);
    }

    /**
     * @return An expanded {@link AABB} for render culling, so that Aerwhales don't de-render
     * when their model is still in camera view while their bounding box isn't.
     */
    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(3.0);
    }

    /**
     * [CODE COPY] - {@link PathfinderMob#shouldStayCloseToLeashHolder()}.
     */
    protected boolean shouldStayCloseToLeashHolder() {
        return true;
    }

    /**
     * [CODE COPY] - {@link PathfinderMob#followLeashSpeed()}.
     */
    protected double followLeashSpeed() {
        return 1.0;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    /**
     * Sets the next position that the Aerwhale should travel to.
     */
    public static class SetTravelCourseGoal extends Goal {
        private final Mob mob;

        public SetTravelCourseGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

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
            y = Mth.clamp(y, this.mob.level().getMinBuildHeight(), this.mob.level().getMaxBuildHeight());

            this.mob.getMoveControl().setWantedPosition(x, y, z, 1.0);
        }
    }

    /**
     * Custom Aerwhale move controller to help with keeping a smooth travel course.
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

            float xRotTarget = (float) (Mth.atan2(y, distance) * Mth.RAD_TO_DEG); // Pitch
            float xRot = Mth.wrapDegrees(this.mob.getXRot());
            xRot = Mth.approachDegrees(xRot, xRotTarget, 0.2F);
            this.mob.setXRot(xRot);
            this.mob.setXRotData(this.mob.getXRot());

            float yRotTarget = Mth.wrapDegrees((float) Mth.atan2(z, x) * Mth.RAD_TO_DEG); // Yaw
            float yRot = Mth.wrapDegrees(this.mob.getYRot() + 90F);
            yRot = Mth.approachDegrees(yRot, yRotTarget, 0.5F);
            this.mob.setYRot(yRot - 90F);
            this.mob.setYRotData(this.mob.getYRot());
            this.mob.setYBodyRot(yRot);
            this.mob.setYHeadRot(yRot);

            x = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.cos(yRot * Mth.DEG_TO_RAD);
            y = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.sin(xRot * Mth.DEG_TO_RAD);
            z = this.mob.getAttributeValue(Attributes.FLYING_SPEED) * Mth.sin(yRot * Mth.DEG_TO_RAD);

            Vec3 motion = new Vec3(x, y, z);
            this.mob.setDeltaMovement(motion);

            // [CODE COPY] - PathfinderMob#tickLeash()
            Entity entity = this.mob.getLeashHolder();
            if (entity != null && entity.level() == this.mob.level()) {
                this.mob.restrictTo(entity.blockPosition(), 5);
                float f = this.mob.distanceTo(entity);
                if (f > 10.0F) {
                    this.mob.dropLeash(true, true);
                    this.mob.goalSelector.disableControlFlag(Goal.Flag.MOVE);
                } else if (f > 6.0F) {
                    double d0 = (entity.getX() - this.mob.getX()) / (double)f;
                    double d1 = (entity.getY() - this.mob.getY()) / (double)f;
                    double d2 = (entity.getZ() - this.mob.getZ()) / (double)f;
                    this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4, d0), Math.copySign(d1 * d1 * 0.4, d1), Math.copySign(d2 * d2 * 0.4, d2)));
                    this.mob.checkSlowFallDistance();
                } else if (this.mob.shouldStayCloseToLeashHolder()) {
                    this.mob.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                    Vec3 vec3 = (new Vec3(entity.getX() - this.mob.getX(), entity.getY() - this.mob.getY(), entity.getZ() - this.mob.getZ())).normalize().scale(Math.max(f - 2.0F, 0.0F));
                    this.mob.getNavigation().moveTo(this.mob.getX() + vec3.x, this.mob.getY() + vec3.y, this.mob.getZ() + vec3.z, this.mob.followLeashSpeed());
                }
            }
        }

        /**
         * Checks if entity bounding box is not colliding with terrain
         */
        private boolean isColliding(Vec3 pos) {
            AABB axisalignedbb = this.mob.getBoundingBox();

            for (int i = 1; i < 7; ++i) {
                axisalignedbb = axisalignedbb.move(pos);
                if (!this.mob.level().noCollision(this.mob, axisalignedbb)) {
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
        public void tick() { }
    }
}