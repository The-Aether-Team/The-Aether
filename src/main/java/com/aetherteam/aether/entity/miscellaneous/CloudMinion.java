package com.aetherteam.aether.entity.miscellaneous;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.EntityUtil;
import com.aetherteam.aether.entity.projectile.crystal.CloudCrystal;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CloudMinion extends FlyingMob {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_RIGHT_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_LIFESPAN_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.INT);

    private boolean shouldShoot;
    private double targetX, targetY, targetZ;

    public CloudMinion(EntityType<? extends FlyingMob> type, Level level) {
        super(type, level);
    }

    public CloudMinion(Level level, Player player, HumanoidArm armSide) {
        super(AetherEntityTypes.CLOUD_MINION.get(), level);
        this.setOwner(player);
        this.setSide(armSide);
        this.setLifeSpan(3600);
        this.noPhysics = true;
        this.setPositionFromOwner();
        this.setPos(this.targetX, this.targetY, this.targetZ);
        this.setXRot(player.getXRot());
        this.setYRot(player.getYRot());
    }
   
    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 10.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_OWNER_ID, 0);
        this.getEntityData().define(DATA_IS_RIGHT_ID, true);
        this.getEntityData().define(DATA_LIFESPAN_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setLifeSpan(this.getLifeSpan() - 1);
        if (this.getLifeSpan() <= 0) { // Removes if lifespan is up.
            this.spawnExplosionParticles();
            this.remove(RemovalReason.DISCARDED);
        } else {
            if (this.getOwner() != null) {
                if (this.getOwner().isAlive()) {
                    this.setPositionFromOwner();
                    this.setRotationFromOwner();
                    if (this.atShoulder()) {
                        Vec3 motion = this.getDeltaMovement();
                        this.setDeltaMovement(motion.multiply(0.65, 0.65, 0.65));
                        if (this.shouldShoot()) { // Checks if able to shoot a Cloud Crystal
                            float offset = this.getSide() == HumanoidArm.RIGHT ? 2.0F : -2.0F;
                            float rotation = Mth.wrapDegrees(this.getYRot() + offset);
                            CloudCrystal crystal = new CloudCrystal(this.getLevel()); // Sets up Cloud Crystal.
                            crystal.setPos(this.getX(), this.getY(), this.getZ());
                            crystal.shootFromRotation(this, this.getXRot(), rotation, 0.0F, 1.0F, 1.0F);
                            crystal.setOwner(this.getOwner());
                            if (!this.getLevel().isClientSide()) {
                                this.getLevel().addFreshEntity(crystal);
                            }
                            this.playSound(AetherSoundEvents.ENTITY_CLOUD_MINION_SHOOT.get(), 0.75F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
                            this.setShouldShoot(false); // Finish shoot.
                        }
                    } else { // Approaches owner if not at shoulder.
                        this.approachOwner();
                    }
                } else { // Removes if owner is dead.
                    this.spawnExplosionParticles();
                    this.remove(RemovalReason.KILLED);
                }
            } else { // Removes if owner doesn't exist.
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    /**
     * Sets the position that the Cloud Minion should hover at on the side of the player.
     */
    public void setPositionFromOwner() {
        if (this.getOwner() != null) {
            if (this.distanceTo(this.getOwner()) > 2.0F) {
                this.targetX = this.getOwner().getX();
                this.targetY = this.getOwner().getY() + 1.0;
                this.targetZ = this.getOwner().getZ();
            } else {
                double yaw = this.getOwner().getYRot();
                if (this.getSide() == HumanoidArm.RIGHT) {
                    yaw -= 90.0;
                } else {
                    yaw += 90.0;
                }
                yaw /= -Mth.RAD_TO_DEG;
                this.targetX = this.getOwner().getX() + Math.sin(yaw) * 1.05;
                this.targetY = this.getOwner().getY() + 1.0;
                this.targetZ = this.getOwner().getZ() + Math.cos(yaw) * 1.05;
            }
        }
    }

    /**
     * Rotates the Cloud Minion according to the player's rotation.
     */
    public void setRotationFromOwner() {
        if (this.getOwner() != null) {
            this.setYRot(this.getOwner().getYRot() + (this.getSide() == HumanoidArm.RIGHT ? 1.0F : -1.0F));
            this.setXRot(this.getOwner().getXRot());
            this.setYHeadRot(this.getOwner().getYHeadRot());
        }
    }

    /**
     * @return A {@link Boolean} for whether the Cloud Minion is with the player at their shoulder.
     */
    public boolean atShoulder() {
        double x = this.getX() - this.targetX;
        double y = this.getY() - this.targetY;
        double z = this.getZ() - this.targetZ;
        return Math.sqrt(x * x + y * y + z * z) < 0.4;
    }

    /**
     * Moves the Cloud Minion towards the owner based on the target position.
     * Called if the Cloud Minion is not at the player's shoulder, according to {@link CloudMinion#atShoulder()}.
     */
    public void approachOwner() {
        double x = this.targetX - this.getX();
        double y = this.targetY - this.getY();
        double z = this.targetZ - this.getZ();
        double sqrt = Math.sqrt(x * x + y * y + z * z);
        Vec3 motion = this.getDeltaMovement();
        double motionX = ((motion.x() + x / (sqrt * 3.25)) * sqrt) / 2.0;
        double motionY = ((motion.y() + y / (sqrt * 3.25)) * sqrt) / 2.0;
        double motionZ = ((motion.z() + z / (sqrt * 3.25)) * sqrt) / 2.0;
        this.setDeltaMovement(motionX, motionY, motionZ);
    }

    /**
     * Spawn explosion particles on client or in {@link CloudMinion#handleEntityEvent(byte)}.
     */
    public void spawnExplosionParticles() {
        if (this.getLevel().isClientSide()) {
            EntityUtil.spawnSummoningExplosionParticles(this);
        } else {
            this.getLevel().broadcastEntityEvent(this, (byte) 70);
        }
    }

    /**
     * Cloud Minions have no collision.
     */
    @Override
    protected void pushEntities() { }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    /**
     * Cloud Minions cannot be damaged.
     */
    @Override
    public boolean hurt(DamageSource source, float damage) {
        return false;
    }

    /**
     * @return The owner {@link Player} of the Cloud Minion.
     */
    @Nullable
    public Player getOwner() {
        return (Player) this.getLevel().getEntity(this.getEntityData().get(DATA_OWNER_ID));
    }

    /**
     * Sets the owner {@link Player} of the Cloud Minion.
     * @param entity The owner {@link Player}.
     */
    public void setOwner(Player entity) {
        this.getEntityData().set(DATA_OWNER_ID, entity.getId());
    }

    /**
     * @return The {@link HumanoidArm} side that the Cloud Minion should hover at.
     */
    public HumanoidArm getSide() {
        return this.getEntityData().get(DATA_IS_RIGHT_ID) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
    }

    /**
     * Sets the {@link HumanoidArm} side that the Cloud Minion should hover at
     * @param armSide The {@link HumanoidArm} side.
     */
    public void setSide(HumanoidArm armSide) {
        this.getEntityData().set(DATA_IS_RIGHT_ID, armSide == HumanoidArm.RIGHT);
    }

    /**
     * @return The lifespan of the Cloud Minion, as an {@link Integer}.
     */
    public int getLifeSpan() {
        return this.getEntityData().get(DATA_LIFESPAN_ID);
    }

    /**
     * Sets the lifespan of the Cloud Minion.
     * @param lifespan The lifespan, as an {@link Integer}.
     */
    public void setLifeSpan(int lifespan) {
        this.getEntityData().set(DATA_LIFESPAN_ID, lifespan);
    }

    /**
     * @return Whether the Cloud Minion can shoot a Cloud Crystal, as a {@link Boolean}.
     */
    public boolean shouldShoot() {
        return this.shouldShoot;
    }

    /**
     * Sets whether the Cloud Minion can shoot a Cloud Crystal.
     * @param shouldShoot Whether it should shoot, as a {@link Boolean}.
     */
    public void setShouldShoot(boolean shouldShoot) {
        this.shouldShoot = shouldShoot;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 70) {
            EntityUtil.spawnSummoningExplosionParticles(this);
        } else {
            super.handleEntityEvent(id);
        }
    }
   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
