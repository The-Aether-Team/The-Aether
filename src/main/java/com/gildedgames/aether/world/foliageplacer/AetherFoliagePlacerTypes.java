package com.gildedgames.aether.world.foliageplacer;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.gildedgames.aether.world.foliageplacer.HolidayFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, Aether.MODID);

    public static final RegistryObject<FoliagePlacerType<CrystalFoliagePlacer>> CRYSTAL_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("crystal_foliage_placer", () -> new FoliagePlacerType<>(CrystalFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<HolidayFoliagePlacer>> HOLIDAY_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("holiday_foliage_placer", () -> new FoliagePlacerType<>(HolidayFoliagePlacer.CODEC));
}
