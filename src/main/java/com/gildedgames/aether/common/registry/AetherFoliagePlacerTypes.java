package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.foliageplacer.CrystalFoliagePlacer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class AetherFoliagePlacerTypes {
    public static final FoliagePlacerType<?> CRYSTAL_FOLIAGE_PLACER = register("crystal_foliage_placer", CrystalFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.FOLIAGE_PLACER_TYPES, new ResourceLocation(Aether.MODID, name), new FoliagePlacerType<>(codec));
    }

    public static void init() {}
}
