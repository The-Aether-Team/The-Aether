package com.gildedgames.aether.capability.cape;

import com.gildedgames.aether.capability.AetherCapabilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface CapeEntity extends INBTSerializable<CompoundTag> {
    LivingEntity getEntity();

    static LazyOptional<CapeEntity> get(LivingEntity entity) {
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
