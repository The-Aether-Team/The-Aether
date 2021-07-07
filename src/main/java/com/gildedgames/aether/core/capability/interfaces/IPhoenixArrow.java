package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IPhoenixArrow extends INBTSerializable<CompoundNBT>
{
    AbstractArrowEntity getArrow();

    static LazyOptional<IPhoenixArrow> get(AbstractArrowEntity arrow) {
        return arrow.getCapability(AetherCapabilities.PHOENIX_ARROW_CAPABILITY);
    }

    void setPhoenixArrow(boolean isPhoenixArrow);
    boolean isPhoenixArrow();

    void setFireTime(int time);
    int getFireTime();
}
