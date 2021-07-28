package com.gildedgames.aether.common.entity.projectile.weapon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class HammerProjectileEntity extends ThrowableEntity
{
    private static final DataParameter<Boolean> DATA_JEB_ID = EntityDataManager.defineId(HammerProjectileEntity.class, DataSerializers.BOOLEAN);

    private int ticksInAir = 0;

    public HammerProjectileEntity(EntityType<? extends HammerProjectileEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public HammerProjectileEntity(LivingEntity owner, World world) {
        super(AetherEntityTypes.HAMMER_PROJECTILE.get(), owner, world);
    }

    public HammerProjectileEntity(World world) {
        super(AetherEntityTypes.HAMMER_PROJECTILE.get(), world);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_JEB_ID, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.remove();
        }
        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    public void shoot(PlayerEntity player, float rotationPitch, float rotationYaw, float v, float velocity, float inaccuracy) {
        float x = -MathHelper.sin(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        float y = -MathHelper.sin((rotationPitch + v) * ((float)Math.PI / 180F));
        float z = MathHelper.cos(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(x, y, z, velocity, inaccuracy);
        Vector3d playerMotion = player.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(playerMotion.x, player.isOnGround() ? 0.0D : playerMotion.y, playerMotion.z));
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.remove();
        } else {
            for(int j = 0; j < 8; j++) {
                this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
                this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        if (!this.level.isClientSide) {
            Entity target = result.getEntity();
            launchTarget(target);
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        if (!this.level.isClientSide) {
            List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate(3.0D));
            for (Entity target : list) {
                launchTarget(target);
            }
        }
    }

    private void launchTarget(Entity target) {
        if (target != this.getOwner()) {
            if (this.getOwner() == null || target != this.getOwner().getVehicle()) {
                target.hurt(DamageSource.thrown(this, this.getOwner()), 5);
                target.push(this.getDeltaMovement().x, 0.6D, this.getDeltaMovement().z);
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    public void setIsJeb(boolean isJeb) {
        this.entityData.set(DATA_JEB_ID, isJeb);
    }

    public boolean getIsJeb() {
        return this.entityData.get(DATA_JEB_ID);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
