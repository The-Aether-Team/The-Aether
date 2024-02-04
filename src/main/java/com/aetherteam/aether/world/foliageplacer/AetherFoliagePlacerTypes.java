package com.aetherteam.aether.world.foliageplacer;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Aether.MODID);

    public static final Supplier<FoliagePlacerType<CrystalFoliagePlacer>> CRYSTAL_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("crystal_foliage_placer", () -> new FoliagePlacerType<>(CrystalFoliagePlacer.CODEC));
    public static final Supplier<FoliagePlacerType<HolidayFoliagePlacer>> HOLIDAY_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("holiday_foliage_placer", () -> new FoliagePlacerType<>(HolidayFoliagePlacer.CODEC));
    public static final Supplier<FoliagePlacerType<GoldenOakFoliagePlacer>> GOLDEN_OAK_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("golden_oak_foliage_placer", () -> new FoliagePlacerType<>(GoldenOakFoliagePlacer.CODEC));
}
