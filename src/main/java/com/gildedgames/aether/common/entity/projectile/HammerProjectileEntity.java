package com.gildedgames.aether.common.entity.projectile;

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
        this.setShooter(player);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        this.world.addParticle(ParticleTypes.CLOUD, this.getPosX(), this.getPosY() + 0.2, this.getPosZ(), 0.0, 0.0, 0.0);
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
    protected void onImpact(RayTraceResult result) {
        for(int j = 0; j < 8; j++) {
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
        }
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.remove();
        }
    }

    /**
     * When an entity is hit, this method makes sure it is not the shooter that gets hit.
     */
    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity target = result.getEntity();
        target.attackEntityFrom(DamageSource.causeThrownDamage(this, func_234616_v_()), 5);
        target.addVelocity(this.getMotion().x, 0.6D, this.getMotion().z);
    }

    public void shoot(PlayerEntity player, float rotationPitch, float rotationYaw, float v, float velocity, float inaccuracy) {
        float x = -MathHelper.sin(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        float y = -MathHelper.sin((rotationPitch + v) * ((float)Math.PI / 180F));
        float z = MathHelper.cos(rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(x, y, z, velocity, inaccuracy);
        Vector3d playerMotion = player.getMotion();
        this.setMotion(this.getMotion().add(playerMotion.x, player.isOnGround() ? 0.0D : playerMotion.y, playerMotion.z));
    }


    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }
}
