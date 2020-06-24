package com.aether.world.gen;

import com.aether.block.AetherBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationSettings;

public class AetherGenSettings extends GenerationSettings {

    public AetherGenSettings() {
        setDefaultBlock(AetherBlocks.HOLYSTONE.getDefaultState());
        setDefaultFluid(Blocks.WATER.getDefaultState());
    }
}
