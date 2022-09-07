package com.gildedgames.aether.entity.projectile.crystal;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class AbstractCrystal extends Projectile {
    protected int ticksInAir = 0;

    protected AbstractCrystal(EntityType<? extends AbstractCrystal> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > this.getLifeSpan()) {
            this.spawnExplosionParticles();
            this.discard();
        }
        HitResult result = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) result).getBlockPos();
            BlockState blockState = this.level.getBlockState(blockPos);
            if (blockState.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockPos);
                flag = true;
            } else if (blockState.is(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.level.getBlockEntity(blockPos);
                if (blockEntity instanceof TheEndGatewayBlockEntity endGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockPos, blockState, this, endGatewayBlockEntity);
                }
                flag = true;
            }
        }
        if (result.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, result)) {
            this.onHit(result);
        }
        this.checkInsideBlocks();
        this.tickMovement();
    }

    /**
     * Handles the crystal's movement. Override this if you need different movement code.
     */
    protected void tickMovement() {
        Vec3 vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x;
        double d0 = this.getY() + vector3d.y;
        double d1 = this.getZ() + vector3d.z;
        this.updateRotation();
        this.setPos(d2, d0, d1);
    }

    public void spawnExplosionParticles() {
        if (this.level instanceof ServerLevel level) {
            for (int i = 0; i < 20; i++) {
                double x = (this.random.nextFloat() - 0.5F) * 0.5;
                double y = (this.random.nextFloat() - 0.5F) * 0.5;
                double z = (this.random.nextFloat() - 0.5F) * 0.5;
                level.sendParticles(this.getExplosionParticle(), this.getX(), this.getY(), this.getZ(), 1, x, y, z, 0.0F);
            }
        }
    }

    protected abstract ParticleOptions getExplosionParticle();

    public int getLifeSpan() {
        return 300;
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TicksInAir", this.ticksInAir);
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("TicksInAir")) {
            this.ticksInAir = tag.getInt("TicksInAir");
        }
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
