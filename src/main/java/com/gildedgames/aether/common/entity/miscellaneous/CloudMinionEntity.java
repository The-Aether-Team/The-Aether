package com.gildedgames.aether.common.entity.miscellaneous;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.entity.projectile.crystal.CloudCrystalEntity;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CloudMinionEntity extends FlyingEntity
{
    private static final DataParameter<Integer> DATA_OWNER_ID = EntityDataManager.defineId(CloudMinionEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> DATA_IS_RIGHT_ID = EntityDataManager.defineId(CloudMinionEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DATA_LIFESPAN_ID = EntityDataManager.defineId(CloudMinionEntity.class, DataSerializers.INT);

    public boolean shouldShoot;
    public double targetX, targetY, targetZ;

    public CloudMinionEntity(EntityType<? extends FlyingEntity> p_i48578_1_, World p_i48578_2_) {
        super(p_i48578_1_, p_i48578_2_);
    }

    public CloudMinionEntity(World p_i48578_2_, PlayerEntity entity, HandSide side) {
        super(AetherEntityTypes.CLOUD_MINION.get(), p_i48578_2_);
        this.setOwner(entity);
        this.setSide(side);
        this.setLifeSpan(3600);
        this.noPhysics = true;
        this.setPositionFromOwner();
        this.setPos(this.targetX, this.targetY, this.targetZ);
        this.xRot = this.getOwner().xRot;
        this.yRot = this.getOwner().yRot;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNER_ID, 0);
        this.entityData.define(DATA_IS_RIGHT_ID, true);
        this.entityData.define(DATA_LIFESPAN_ID, 0);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 10.0F);
    }

    public void setPositionFromOwner() {
        if (this.distanceTo(this.getOwner()) > 2.0F) {
            this.targetX = this.getOwner().getX();
            this.targetY = this.getOwner().getY() + 1.0D;
            this.targetZ = this.getOwner().getZ();
        } else {
            double yaw = this.getOwner().yRot;
            if (this.getSide() == HandSide.RIGHT) {
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
        Vector3d motion = this.getDeltaMovement();
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
            this.remove();
        } else {
            if (this.getOwner() != null) {
                if (this.getOwner().isAlive()) {
                    this.setPositionFromOwner();
                    if (this.atShoulder()) {
                        Vector3d motion = this.getDeltaMovement();
                        this.setDeltaMovement(motion.multiply(0.65D, 0.65D, 0.65D));
                        this.yRot = this.getOwner().yRot + (this.getSide() == HandSide.RIGHT ? 1.0F : -1.0F);
                        this.xRot = this.getOwner().xRot;
                        this.setYHeadRot(this.getOwner().getYHeadRot());
                        if (this.shouldShoot()) {
                            float offset = this.getSide() == HandSide.RIGHT ? 2.0F : -2.0F;
                            float rotation = MathHelper.wrapDegrees(this.yRot + offset);
                            CloudCrystalEntity crystal = new CloudCrystalEntity(this.level);
                            crystal.setPos(this.getX(), this.getY(), this.getZ());
                            crystal.shootFromRotation(this, this.xRot, rotation, 0.0F, 1.0F, 1.0F);
                            crystal.setOwner(this.getOwner());
                            if (!this.level.isClientSide) {
                                this.level.addFreshEntity(crystal);
                            }
                            //TODO: Change to special sound event.
                            this.playSound(AetherSoundEvents.ENTITY_ZEPHYR_SHOOT.get(), 0.75F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

                            this.setShouldShoot(false);
                        }
                    } else {
                        this.approachOwner();
                    }
                } else {
                    this.spawnExplosionParticles();
                    this.remove();
                }
            } else {
                this.remove();
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

    public void setOwner(PlayerEntity entity) {
        this.entityData.set(DATA_OWNER_ID, entity.getId());
    }

    public PlayerEntity getOwner() {
        return (PlayerEntity) this.level.getEntity(this.entityData.get(DATA_OWNER_ID));
    }

    public void setSide(HandSide side) {
        this.entityData.set(DATA_IS_RIGHT_ID, side == HandSide.RIGHT);
    }

    public HandSide getSide() {
        return this.entityData.get(DATA_IS_RIGHT_ID) ? HandSide.RIGHT : HandSide.LEFT;
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
