package com.aetherteam.aether.world.foliageplacer;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class AetherFoliagePlacerTypes {
    public static final LazyRegistrar<FoliagePlacerType<?>> FOLIAGE_PLACERS = LazyRegistrar.create(Registries.FOLIAGE_PLACER_TYPE, Aether.MODID);

    public static final RegistryObject<FoliagePlacerType<CrystalFoliagePlacer>> CRYSTAL_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("crystal_foliage_placer", () -> new FoliagePlacerType<>(CrystalFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<HolidayFoliagePlacer>> HOLIDAY_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("holiday_foliage_placer", () -> new FoliagePlacerType<>(HolidayFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<GoldenOakFoliagePlacer>> GOLDEN_OAK_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("golden_oak_foliage_placer", () -> new FoliagePlacerType<>(GoldenOakFoliagePlacer.CODEC));
}
