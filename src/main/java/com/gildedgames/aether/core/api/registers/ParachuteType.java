package com.gildedgames.aether.core.api.registers;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class ParachuteType
{
    private final ResourceLocation registryName;
    private final EntityType<?> parachuteType;

    public ParachuteType(ResourceLocation registryName, EntityType<?> parachuteType) {
        this.registryName = registryName;
        this.parachuteType = parachuteType;
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    public EntityType<?> getParachuteType() {
        return parachuteType;
    }
}
