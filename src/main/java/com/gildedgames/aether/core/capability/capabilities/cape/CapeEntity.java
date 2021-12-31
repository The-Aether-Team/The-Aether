package com.gildedgames.aether.core.capability.capabilities.cape;

import com.gildedgames.aether.core.capability.interfaces.ICapeEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

public class CapeEntity implements ICapeEntity
{
    private final LivingEntity entity;

    public double xCloakO;
    public double yCloakO;
    public double zCloakO;
    public double xCloak;
    public double yCloak;
    public double zCloak;

    public float oBob;
    public float bob;

    public CapeEntity(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public LivingEntity getEntity() {
        return this.entity;
    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) { }

    @Override
    public void onUpdate() {
        this.moveCloak();
        this.tickPassenger(this.entity);
    }

    private void moveCloak() {
        this.xCloakO = this.xCloak;
        this.yCloakO = this.yCloak;
        this.zCloakO = this.zCloak;
        double d0 = this.entity.getX() - this.xCloak;
        double d1 = this.entity.getY() - this.yCloak;
        double d2 = this.entity.getZ() - this.zCloak;
        double d3 = 10.0D;
        if (d0 > d3) {
            this.xCloak = this.entity.getX();
            this.xCloakO = this.xCloak;
        }

        if (d2 > d3) {
            this.zCloak = this.entity.getZ();
            this.zCloakO = this.zCloak;
        }

        if (d1 > d3) {
            this.yCloak = this.entity.getY();
            this.yCloakO = this.yCloak;
        }

        if (d0 < -d3) {
            this.xCloak = this.entity.getX();
            this.xCloakO = this.xCloak;
        }

        if (d2 < -d3) {
            this.zCloak = this.entity.getZ();
            this.zCloakO = this.zCloak;
        }

        if (d1 < -d3) {
            this.yCloak = this.entity.getY();
            this.yCloakO = this.yCloak;
        }

        this.xCloak += d0 * 0.25D;
        this.zCloak += d2 * 0.25D;
        this.yCloak += d1 * 0.25D;
    }

    private void tickPassenger(Entity passenger) {
        if (!passenger.isRemoved()) {
            if ((passenger.level instanceof ClientLevel clientLevel && clientLevel.tickingEntities.contains(passenger)) || (passenger.level instanceof ServerLevel serverLevel && serverLevel.entityTickList.contains(passenger))) {
                this.rideTick();
            }
        }
    }

    public void rideTick() {
        if (!this.entity.level.isClientSide || !this.entity.isShiftKeyDown() || !this.entity.isPassenger()) {
            this.oBob = this.bob;
            this.bob = 0.0F;
        }
    }

    public double getxCloakO() {
        return this.xCloakO;
    }

    public double getyCloakO() {
        return this.yCloakO;
    }

    public double getzCloakO() {
        return this.zCloakO;
    }

    public double getxCloak() {
        return this.xCloak;
    }

    public double getyCloak() {
        return this.yCloak;
    }

    public double getzCloak() {
        return this.zCloak;
    }

    public float getBob() {
        return this.bob;
    }

    public float getoBob() {
        return this.oBob;
    }
}
