package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.core.api.registers.ParachuteType;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AetherParachuteTypes
{
    public static Map<ResourceLocation, ParachuteType> PARACHUTES = new HashMap<>();

    public static final ParachuteType COLD_PARACHUTE = register("cold_parachute", AetherEntityTypes.COLD_PARACHUTE_TYPE);
    public static final ParachuteType GOLDEN_PARACHUTE = register("golden_parachute", AetherEntityTypes.GOLDEN_PARACHUTE_TYPE);

    public static ParachuteType register(String registryName, EntityType<?> entityType) {
        ResourceLocation registryLocation = new ResourceLocation(Aether.MODID, registryName);
        ParachuteType parachuteType = new ParachuteType(registryLocation, entityType);
        PARACHUTES.put(registryLocation, parachuteType);
        return parachuteType;
    }
}
