package com.aether.poi;

import com.aether.Aether;
import com.aether.block.AetherBlocks;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherPOI {

    public static final DeferredRegister<PointOfInterestType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, Aether.MODID);

    public static final RegistryObject<PointOfInterestType> AETHER_PORTAL = POI.register("aether_portal",
            () -> new PointOfInterestType("aether_portal", PointOfInterestType.getAllStates(AetherBlocks.AETHER_PORTAL), 0, 1));
}
