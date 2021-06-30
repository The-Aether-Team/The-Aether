package com.gildedgames.aether.core.registry;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.api.registers.ParachuteType;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AetherParachuteTypes
{
    public static Map<String, ParachuteType> PARACHUTES = new HashMap<>();

    public static final ParachuteType COLD_PARACHUTE = register("cold_parachute", AetherBlocks.COLD_AERCLOUD);
    public static final ParachuteType GOLDEN_PARACHUTE = register("golden_parachute", AetherBlocks.GOLDEN_AERCLOUD);

    public static ParachuteType register(String registryName, Supplier<Block> block) {
        ParachuteType parachuteType = new ParachuteType(registryName, block);
        PARACHUTES.put(registryName, parachuteType);
        return parachuteType;
    }
}
