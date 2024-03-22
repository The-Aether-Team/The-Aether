package com.aetherteam.aether.world.treegrower;

import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class AetherTreeGrowers {
    public static final TreeGrower SKYROOT = new TreeGrower(
            "skyroot",
            Optional.empty(),
            Optional.of(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION),
            Optional.empty()
    );

    public static final TreeGrower GOLDEN_OAK = new TreeGrower(
            "golden_oak",
            Optional.empty(),
            Optional.of(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION),
            Optional.empty()
    );
}
