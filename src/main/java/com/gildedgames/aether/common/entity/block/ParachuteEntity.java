package com.gildedgames.aether.common.entity.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ParachuteEntity extends Entity
{
    protected static final DataParameter<Optional<UUID>> DATA_PLAYER_UUID_ID = EntityDataManager.defineId(ParachuteEntity.class, DataSerializers.OPTIONAL_UUID);
    
    public ParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_PLAYER_UUID_ID, Optional.empty());
    }

    @Override
    public void tick() {
        if (this.getPlayer() != null) {
            Entity player = this.getPlayer();
            Vector3d motion = player.getDeltaMovement();

            player.fallDistance = 0.0F;
            if (motion.y < 0.0) {
                player.setDeltaMovement(motion.multiply(1.0, 0.6, 1.0));
            }

            this.moveToEntityUsing();
            this.spawnExplosionParticle();
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
            this.level.addParticle(ParticleTypes.POOF, d4, d5, d6, d0, d1, d2);
        }
    }

    private void moveToEntityUsing() {
        if (this.getPlayer() != null) {
            this.setPos(this.getPlayer().getX(), this.getPlayer().getY() - 1.0, this.getPlayer().getZ());

            if (isCollided()) {
                this.die();
            }
        }
    }

    private boolean isCollided() {
        if (this.getPlayer() != null) {
            return this.getPlayer().isOnGround() || this.getPlayer().isInWater();
        }
        return true;
    }

    public void die() {
        this.spawnExplosionParticle();
        this.ejectPassengers();
        this.kill();
    }

    @Nullable
    public PlayerEntity getPlayer() {
        try {
            UUID uuid = this.getPlayerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Nullable
    public UUID getPlayerUUID() {
        return this.entityData.get(DATA_PLAYER_UUID_ID).orElse(null);
    }

    public void setPlayerUUID(@Nullable UUID p_184754_1_) {
        this.entityData.set(DATA_PLAYER_UUID_ID, Optional.ofNullable(p_184754_1_));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (this.getPlayerUUID() != null) {
            nbt.putUUID("Player", this.getPlayerUUID());
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("Player")) {
            this.setPlayerUUID(nbt.getUUID("Player"));
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
