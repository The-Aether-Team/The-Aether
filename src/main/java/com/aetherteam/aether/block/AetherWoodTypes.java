package com.aetherteam.aether.block;

import com.aetherteam.aether.Aether;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherWoodTypes {
    public static final BlockSetType SKYROOT_BLOCK_SET = new BlockSetType(Aether.MODID + ":skyroot");
    public static final WoodType SKYROOT = new WoodType(Aether.MODID + ":skyroot", SKYROOT_BLOCK_SET);
}
