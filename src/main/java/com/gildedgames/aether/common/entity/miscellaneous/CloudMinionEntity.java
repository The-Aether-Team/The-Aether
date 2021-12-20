package com.gildedgames.aether.common.entity.miscellaneous;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.crystal.CloudCrystalEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class CloudMinionEntity extends FlyingMob
{
    private static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CloudMinionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_RIGHT_ID = SynchedEntityData.defineId(CloudMinionEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_LIFESPAN_ID = SynchedEntityData.defineId(CloudMinionEntity.class, EntityDataSerializers.INT);

    public boolean shouldShoot;
    public double targetX, targetY, targetZ;

    public CloudMinionEntity(EntityType<? extends FlyingMob> p_i48578_1_, Level p_i48578_2_) {
        super(p_i48578_1_, p_i48578_2_);
    }

    public CloudMinionEntity(Level p_i48578_2_, Player entity, HumanoidArm side) {
        super(AetherEntityTypes.CLOUD_MINION.get(), p_i48578_2_);
        this.setOwner(entity);
        this.setSide(side);
        this.setLifeSpan(3600);
        this.noPhysics = true;
        this.setPositionFromOwner();
        this.setPos(this.targetX, this.targetY, this.targetZ);
        this.setXRot(this.getOwner().getXRot());
        this.setYRot(this.getOwner().getYRot());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNER_ID, 0);
        this.entityData.define(DATA_IS_RIGHT_ID, true);
        this.entityData.define(DATA_LIFESPAN_ID, 0);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return FlyingMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 10.0F);
    }

    public void setPositionFromOwner() {
        if (this.distanceTo(this.getOwner()) > 2.0F) {
            this.targetX = this.getOwner().getX();
            this.targetY = this.getOwner().getY() + 1.0D;
            this.targetZ = this.getOwner().getZ();
        } else {
            double yaw = this.getOwner().getYRot();
            if (this.getSide() == HumanoidArm.RIGHT) {
                yaw -= 90.0D;
            } else {
                yaw += 90.0D;
            }
            yaw /= -(180.0D / Math.PI);
            this.targetX = this.getOwner().getX() + Math.sin(yaw) * 1.05D;
            this.targetY = this.getOwner().getY() + 1.0D;
            this.targetZ = this.getOwner().getZ() + Math.cos(yaw) * 1.05D;
        }
    }

    public boolean atShoulder() {
        double x = this.getX() - this.targetX;
        double y = this.getY() - this.targetY;
        double z = this.getZ() - this.targetZ;
        return Math.sqrt(x * x + y * y + z * z) < 0.4D;
    }

    public void approachOwner() {
        double x = this.targetX - this.getX();
        double y = this.targetY - this.getY();
        double z = this.targetZ - this.getZ();
        double sqrt = Math.sqrt(x * x + y * y + z * z) * 3.25D;
        Vec3 motion = this.getDeltaMovement();
        double motionX = (motion.x() + x / sqrt) / 2.0D;
        double motionY = (motion.y() + y / sqrt) / 2.0D;
        double motionZ = (motion.z() + z / sqrt) / 2.0D;
        this.setDeltaMovement(motionX, motionY, motionZ);
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
                    if (this.atShoulder()) {
                        Vec3 motion = this.getDeltaMovement();
                        this.setDeltaMovement(motion.multiply(0.65D, 0.65D, 0.65D));
                        this.setYRot(this.getOwner().getYRot() + (this.getSide() == HumanoidArm.RIGHT ? 1.0F : -1.0F));
                        this.setXRot(this.getOwner().getXRot());
                        this.setYHeadRot(this.getOwner().getYHeadRot());
                        if (this.shouldShoot()) {
                            float offset = this.getSide() == HumanoidArm.RIGHT ? 2.0F : -2.0F;
                            float rotation = Mth.wrapDegrees(this.getYRot() + offset);
                            CloudCrystalEntity crystal = new CloudCrystalEntity(this.level);
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

    private void spawnExplosionParticles() {
        if (this.level.isClientSide) {
            for (int i = 0; i < 20; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.POOF, this.getX(0.0D) - d0 * 10.0D, this.getRandomY() - d1 * 10.0D, this.getRandomZ(1.0D) - d2 * 10.0D, d0, d1, d2);
            }
        } else {
            this.level.broadcastEntityEvent(this, (byte) 20);
        }
    }

    @Override
    protected void pushEntities() { }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        return false;
    }

    public void setOwner(Player entity) {
        this.entityData.set(DATA_OWNER_ID, entity.getId());
    }

    public Player getOwner() {
        return (Player) this.level.getEntity(this.entityData.get(DATA_OWNER_ID));
    }

    public void setSide(HumanoidArm side) {
        this.entityData.set(DATA_IS_RIGHT_ID, side == HumanoidArm.RIGHT);
    }

    public HumanoidArm getSide() {
        return this.entityData.get(DATA_IS_RIGHT_ID) ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
    }

    public void setLifeSpan(int lifespan) {
        this.entityData.set(DATA_LIFESPAN_ID, lifespan);
    }

    public int getLifeSpan() {
        return this.entityData.get(DATA_LIFESPAN_ID);
    }

    public void setShouldShoot(boolean shouldShoot) {
        this.shouldShoot = shouldShoot;
    }

    public boolean shouldShoot() {
        return this.shouldShoot;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
