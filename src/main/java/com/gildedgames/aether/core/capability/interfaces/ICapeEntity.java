package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface ICapeEntity extends INBTSerializable<CompoundTag>
{
    LivingEntity getEntity();

    static LazyOptional<ICapeEntity> get(LivingEntity entity) {
        return entity.getCapability(AetherCapabilities.CAPE_ENTITY_CAPABILITY);
    }

    void onUpdate();

    void moveCloak();

    double getxCloakO();

    double getyCloakO();

    double getzCloakO();

    double getxCloak();

    double getyCloak();

    double getzCloak();

    float getBob();

    float getoBob();
}
