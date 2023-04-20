package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.capability.AetherCapabilities;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface PhoenixArrow extends INBTSerializable<CompoundTag> {
    AbstractArrow getArrow();

    static LazyOptional<PhoenixArrow> get(AbstractArrow arrow) {
        return arrow.getCapability(AetherCapabilities.PHOENIX_ARROW_CAPABILITY);
    }

    void setPhoenixArrow(boolean isPhoenixArrow);
    boolean isPhoenixArrow();

    void setFireTime(int time);
    int getFireTime();
}
