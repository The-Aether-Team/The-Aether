package com.gildedgames.aether.core.capability.capabilities.arrow;

import com.gildedgames.aether.core.capability.interfaces.IPhoenixArrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;

public class PhoenixArrow implements IPhoenixArrow {
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
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        tag.putInt("FireTime", this.getFireTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("PhoenixArrow")) {
            this.setPhoenixArrow(tag.getBoolean("PhoenixArrow"));
        }
        if (tag.contains("FireTime")) {
            this.setFireTime(tag.getInt("FireTime"));
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
