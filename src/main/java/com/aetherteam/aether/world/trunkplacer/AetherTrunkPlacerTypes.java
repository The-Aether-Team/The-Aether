package com.aetherteam.aether.world.trunkplacer;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Aether.MODID);

    public static final RegistryObject<TrunkPlacerType<GoldenOakTrunkPlacer>> GOLDEN_OAK_TRUNK_PLACER = TRUNK_PLACERS.register("golden_oak_trunk_placer", () -> new TrunkPlacerType<>(GoldenOakTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<CrystalTreeTrunkPlacer>> CRYSTAL_TREE_TRUNK_PLACER = TRUNK_PLACERS.register("crystal_tree_trunk_placer", () -> new TrunkPlacerType<>(CrystalTreeTrunkPlacer.CODEC));
}
