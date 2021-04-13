package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ICapeEntity extends INBTSerializable<CompoundNBT>
{
    LivingEntity getEntity();

    static LazyOptional<ICapeEntity> get(LivingEntity entity) {
        return entity.getCapability(AetherCapabilities.CAPE_ENTITY_CAPABILITY);
    }

    void onUpdate();

    double getxCloakO();

    double getyCloakO();

    double getzCloakO();

    double getxCloak();

    double getyCloak();

    double getzCloak();

    float getBob();

    float getoBob();
}
