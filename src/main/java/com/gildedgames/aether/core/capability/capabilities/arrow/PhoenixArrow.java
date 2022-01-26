package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.PhoenixArrowSerializable;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;

public class PhoenixArrow implements PhoenixArrowSerializable
{
    private final AbstractArrow arrow;

    private boolean isPhoenixArrow;
    private int fireTime;

    public PhoenixArrow(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public AbstractArrow getArrow() {
        return this.arrow;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        compound.putInt("FireTime", this.getFireTime());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        if (compound.contains("PhoenixArrow")) {
            this.setPhoenixArrow(compound.getBoolean("PhoenixArrow"));
        }
        if (compound.contains("FireTime")) {
            this.setFireTime(compound.getInt("FireTime"));
        }
    }

    @Override
    public void setPhoenixArrow(boolean isPhoenixArrow) {
        this.isPhoenixArrow = isPhoenixArrow;
    }

    @Override
    public boolean isPhoenixArrow() {
        return this.isPhoenixArrow;
    }

    @Override
    public void setFireTime(int time) {
        this.fireTime = time;
    }

    @Override
    public int getFireTime() {
        return this.fireTime;
    }
}
