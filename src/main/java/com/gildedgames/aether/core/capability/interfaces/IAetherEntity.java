package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IAetherEntity extends INBTSerializable<CompoundNBT>
{
    LivingEntity getEntity();

    static LazyOptional<IAetherEntity> get(LivingEntity entity) {
        return entity.getCapability(AetherCapabilities.AETHER_ENTITY_CAPABILITY);
    }

    void sync();

    void onUpdate();
}
