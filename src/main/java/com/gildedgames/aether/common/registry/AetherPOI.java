package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherPOI
{
    public static final DeferredRegister<PointOfInterestType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Aether.MODID);

    public static final RegistryObject<PointOfInterestType> AETHER_PORTAL = POI.register("aether_portal",
            () -> new PointOfInterestType("aether_portal", PointOfInterestType.getBlockStates(AetherBlocks.AETHER_PORTAL.get()), 0, 1));
}
