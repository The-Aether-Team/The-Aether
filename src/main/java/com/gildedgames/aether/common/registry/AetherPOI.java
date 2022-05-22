package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherPOI
{
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Aether.MODID);

    public static final RegistryObject<PoiType> AETHER_PORTAL = POI.register("aether_portal",
            () -> new PoiType("aether_portal", PoiType.getBlockStates(AetherBlocks.AETHER_PORTAL.get()), 0, 1));
}
