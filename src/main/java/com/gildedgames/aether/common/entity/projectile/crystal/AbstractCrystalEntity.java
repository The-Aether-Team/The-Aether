package com.gildedgames.aether.common.entity.projectile.crystal;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractCrystalEntity extends ProjectileEntity
{
    private int ticksInAir = 0;

    protected AbstractCrystalEntity(EntityType<? extends AbstractCrystalEntity> entityType, World world) {
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
            this.remove();
        }
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult)raytraceresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                TileEntity tileentity = this.level.getBlockEntity(blockpos);
                if (tileentity instanceof EndGatewayTileEntity && EndGatewayTileEntity.canEntityTeleport(this)) {
                    ((EndGatewayTileEntity)tileentity).teleportEntity(this);
                }
                flag = true;
            }
        }
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }
        this.checkInsideBlocks();
        Vector3d vector3d = this.getDeltaMovement();
        double d2 = this.getX() + vector3d.x;
        double d0 = this.getY() + vector3d.y;
        double d1 = this.getZ() + vector3d.z;
        this.updateRotation();
        this.setDeltaMovement(vector3d.scale(0.99F));
        this.setPos(d2, d0, d1);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.spawnExplosionParticles();
        this.remove();
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
