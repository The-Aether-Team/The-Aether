package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.util.LazyOptional;

public interface PhoenixArrow extends INBTSynchable<CompoundTag> {
    AbstractArrow getArrow();

    static LazyOptional<PhoenixArrow> get(AbstractArrow arrow) {
        return arrow.getCapability(AetherCapabilities.PHOENIX_ARROW_CAPABILITY);
    }

    void setPhoenixArrow(boolean isPhoenixArrow);
    boolean isPhoenixArrow();

    void setFireTime(int time);
    int getFireTime();
}
