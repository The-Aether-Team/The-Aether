package com.gildedgames.aether.common.entity.equipment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AbstractParachuteEntity extends Entity
{
    public AbstractParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.horizontalCollision = false;
        this.verticalCollision = false;
    }

    @Override
    protected void defineSynchedData() { }

    //TODO: Do i have to let the passenger control the entity to make it move properly?

    @Override
    public void tick() {
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null) {
            Entity entity = this.getPassengers().get(0);
//            Vector3d motion = entity.getDeltaMovement();
//
//            entity.fallDistance = 0.0F;
//            if (motion.y() < 0.0F) {
//                this.setDeltaMovement(motion.multiply(2.0, 0.6, 2.0));
//            }
//           // this.setDeltaMovement(entity.getDeltaMovement());
//            this.spawnExplosionParticle();
//
//           // this.setDeltaMovement(new Vector3d(entity.xo, 0.0, entity.zo));
//
//            this.move(MoverType.SELF, entity.getDeltaMovement());

            //System.out.println(entity.getDeltaMovement());

            if (this.isOnGround() || this.isInWater() || this.isInLava()) {
                this.die();
            }
        } else {
            this.die();
        }
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 1; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            double d4 = this.getX() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d0 * d3;
            double d5 = this.getY() + ((double) this.random.nextFloat() * this.getBbHeight()) - d1 * d3;
            double d6 = this.getZ() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d2 * d3;
            this.level.addParticle(ParticleTypes.POOF, d4, d5 - 2.25, d6, d0, d1, d2);
        }
    }

    private void die() {
        this.ejectPassengers();
        this.spawnExplosionParticle();
        this.kill();
    }
//
//    @Override
//    public Vector3d getDismountLocationForPassenger(LivingEntity p_230268_1_) {
//        return new Vector3d(this.getX(), this.getY(), this.getZ());
//    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
