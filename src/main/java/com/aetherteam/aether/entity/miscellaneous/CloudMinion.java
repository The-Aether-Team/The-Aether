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

import javax.annotation.Nonnull;

public class CloudMinion extends FlyingMob {
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_RIGHT_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_LIFESPAN_ID = SynchedEntityData.defineId(CloudMinion.class, EntityDataSerializers.INT);

    public boolean shouldShoot;
    public double targetX, targetY, targetZ;

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
        this.setXRot(this.getOwner().getXRot());
        this.setYRot(this.getOwner().getYRot());
    }

    @Nonnull
    public static AttributeSupplier.Builder createMobAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0)
                .add(Attributes.MOVEMENT_SPEED, 10.0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNER_ID, 0);
        this.entityData.define(DATA_IS_RIGHT_ID, true);
        this.entityData.define(DATA_LIFESPAN_ID, 0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setLifeSpan(this.getLifeSpan() - 1);
        if (this.getLifeSpan() <= 0) {
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
                        if (this.shouldShoot()) {
                            float offset = this.getSide() == HumanoidArm.RIGHT ? 2.0F : -2.0F;
                            float rotation = Mth.wrapDegrees(this.getYRot() + offset);
                            CloudCrystal crystal = new CloudCrystal(this.level);
                            crystal.setPos(this.getX(), this.getY(), this.getZ());
                            crystal.shootFromRotation(this, this.getXRot(), rotation, 0.0F, 1.0F, 1.0F);
                            crystal.setOwner(this.getOwner());
                            if (!this.level.isClientSide) {
                                this.level.addFreshEntity(crystal);
                            }
                            this.playSound(AetherSoundEvents.ENTITY_CLOUD_MINION_SHOOT.get(), 0.75F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

                            this.setShouldShoot(false);
                        }
                    } else {
                        this.approachOwner();
                    }
                } else {
                    this.spawnExplosionParticles();
                    this.remove(RemovalReason.KILLED);
                }
            } else {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void setPositionFromOwner() {
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
            yaw /= -(180.0 / Math.PI);
            this.targetX = this.getOwner().getX() + Math.sin(yaw) * 1.05;
            this.targetY = this.getOwner().getY() + 1.0;
            this.targetZ = this.getOwner().getZ() + Math.cos(yaw) * 1.05;
        }
    }

    public void setRotationFromOwner() {
        this.setYRot(this.getOwner().getYRot() + (this.getSide() == HumanoidArm.RIGHT ? 1.0F : -1.0F));
        this.setXRot(this.getOwner().getXRot());
        this.setYHeadRot(this.getOwner().getYHeadRot());
    }

    public boolean atShoulder() {
        double x = this.getX() - this.targetX;
        double y = this.getY() - this.targetY;
        double z = this.getZ() - this.targetZ;
        return Math.sqrt(x * x + y * y + z * z) < 0.4;
    }

    public void approachOwner() {
        double x = this.targetX - this.getX();
        double y = this.targetY - this.getY();
        double z = this.targetZ - this.getZ();
        double sqrt = Math.sqrt(x * x + y * y + z * z) * 3.25;
        Vec3 motion = this.getDeltaMovement();
        double motionX = (motion.x() + x / sqrt) / 2.0;
        double motionY = (motion.y() + y / sqrt) / 2.0;
        double motionZ = (motion.z() + z / sqrt) / 2.0;
        this.setDeltaMovement(motionX, motionY, motionZ);
    }

    private void spawnExplosionParticles() {
        if (this.level.isClientSide) {
            EntityUtil.spawnSummoningExplosionParticles(this);
        } else {
            this.level.broadcastEntityEvent(this, (byte) 20);
        }
    }

    @Override
    protected void pushEntities() { }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public boolean hurt(@Nonnull DamageSource source, float damage) {
        return false;
    }

    public Player getOwner() {
        return (Player) this.level.getEntity(this.entityData.get(DATA_OWNER_ID));
    }

    public void setOwner(Player entity) {
        this.entityData.set(DATA_OWNER_ID, entity.getId());
    }

    public HumanoidArm getSide() {
        return this.entityData.get(DATA_IS_RIGHT_ID) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
    }

    public void setSide(HumanoidArm armSide) {
        this.entityData.set(DATA_IS_RIGHT_ID, armSide == HumanoidArm.RIGHT);
    }

    public int getLifeSpan() {
        return this.entityData.get(DATA_LIFESPAN_ID);
    }

    public void setLifeSpan(int lifespan) {
        this.entityData.set(DATA_LIFESPAN_ID, lifespan);
    }

    public boolean shouldShoot() {
        return this.shouldShoot;
    }

    public void setShouldShoot(boolean shouldShoot) {
        this.shouldShoot = shouldShoot;
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
