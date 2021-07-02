package com.gildedgames.aether.common.entity.projectile.weapon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class HammerProjectileEntity extends ThrowableEntity
{
    private int ticksInAir = 0;

    public HammerProjectileEntity(EntityType<? extends HammerProjectileEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public HammerProjectileEntity(World world, LivingEntity player) {
        super(AetherEntityTypes.HAMMER_PROJECTILE.get(), player, world);
        this.setOwner(player);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.remove();
        }
        //TODO: Code this to ignore the shooter and its passengers
    }

    @Override
    protected void onHit(RayTraceResult result) {
        for(int j = 0; j < 8; j++) {
            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            this.level.addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.remove();
        }
    }

    /**
     * When an entity is hit, this method makes sure it is not the shooter that gets hit.
     */
    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity target = result.getEntity();
        target.hurt(DamageSource.thrown(this, getOwner()), 5);
        target.push(this.getDeltaMovement().x, 0.6D, this.getDeltaMovement().z);
    }

    public void shoot(PlayerEntity player, float rotationPitch, float rotationYaw, float v, float velocity, float inaccuracy) {
        float x = -MathHelper.sin(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        float y = -MathHelper.sin((rotationPitch + v) * ((float)Math.PI / 180F));
        float z = MathHelper.cos(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(x, y, z, velocity, inaccuracy);
        Vector3d playerMotion = player.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(playerMotion.x, player.isOnGround() ? 0.0D : playerMotion.y, playerMotion.z));
    }


    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravity() {
        return 0.0F;
    }
}
