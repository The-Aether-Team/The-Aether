package com.aether.world.gen;

import com.aether.block.AetherBlocks;

import net.minecraft.world.gen.GenerationSettings;

public class AetherGenerationSettings extends GenerationSettings {

    {
        setDefaultBlock(AetherBlocks.HOLYSTONE.getDoubleDropsState());
    }
    
}
