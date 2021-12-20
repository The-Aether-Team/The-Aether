package com.gildedgames.aether.common.entity.projectile.crystal;

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

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkHooks;

public abstract class AbstractCrystalEntity extends Projectile
{
    private int ticksInAir = 0;

    protected AbstractCrystalEntity(EntityType<? extends AbstractCrystalEntity> entityType, Level world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > this.getLifeSpan()) {
            this.spawnExplosionParticles();
            this.remove(RemovalReason.DISCARDED);
        }
        HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (raytraceresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)raytraceresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }
                flag = true;
            }
        }
        if (raytraceresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }
        this.checkInsideBlocks();
        Vec3 vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x;
        double d0 = this.getY() + vector3d.y;
        double d1 = this.getZ() + vector3d.z;
        this.updateRotation();
        this.setDeltaMovement(vector3d.scale(0.99F));
        this.setPos(d2, d0, d1);
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.spawnExplosionParticles();
        this.discard();
    }

    public void spawnExplosionParticles() { }

    public SoundEvent getImpactExplosionSoundEvent() {
        return SoundEvents.GENERIC_EXPLODE;
    }

    public int getLifeSpan() {
        return 300;
    }

    @Override
    protected void defineSynchedData() { }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
