package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;

public class PhoenixArrow implements IPhoenixArrow
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
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        nbt.putInt("FireTime", this.getFireTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("PhoenixArrow")) {
            this.setPhoenixArrow(nbt.getBoolean("PhoenixArrow"));
        }
        if (nbt.contains("FireTime")) {
            this.setFireTime(nbt.getInt("FireTime"));
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
