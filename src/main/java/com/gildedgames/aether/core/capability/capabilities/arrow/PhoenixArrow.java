package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;

public class PhoenixArrow implements IPhoenixArrow
{
    private final AbstractArrowEntity arrow;

    private boolean isPhoenixArrow;
    private int fireTime;

    public PhoenixArrow(AbstractArrowEntity arrow) {
        this.arrow = arrow;
    }

    @Override
    public AbstractArrowEntity getArrow() {
        return this.arrow;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        nbt.putInt("FireTime", this.getFireTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
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
