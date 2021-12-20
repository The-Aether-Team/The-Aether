package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IPhoenixArrow extends INBTSerializable<CompoundTag>
{
    AbstractArrow getArrow();

    static LazyOptional<IPhoenixArrow> get(AbstractArrow arrow) {
        return arrow.getCapability(AetherCapabilities.PHOENIX_ARROW_CAPABILITY);
    }

    void setPhoenixArrow(boolean isPhoenixArrow);
    boolean isPhoenixArrow();

    void setFireTime(int time);
    int getFireTime();
}
