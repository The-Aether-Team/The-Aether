package com.aetherteam.aether.world.trunkplacer;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Aether.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<GoldenOakTrunkPlacer>> GOLDEN_OAK_TRUNK_PLACER = TRUNK_PLACERS.register("golden_oak_trunk_placer", () -> new TrunkPlacerType<>(GoldenOakTrunkPlacer.CODEC));
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<CrystalTreeTrunkPlacer>> CRYSTAL_TREE_TRUNK_PLACER = TRUNK_PLACERS.register("crystal_tree_trunk_placer", () -> new TrunkPlacerType<>(CrystalTreeTrunkPlacer.CODEC));
}
