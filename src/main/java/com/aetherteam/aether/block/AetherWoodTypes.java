package com.aetherteam.aether.block;

import com.aetherteam.aether.Aether;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherWoodTypes {
    public static final BlockSetType SKYROOT_BLOCK_SET = new BlockSetTypeBuilder().register(new ResourceLocation(Aether.MODID, "skyroot"));
    public static final WoodType SKYROOT = new WoodTypeBuilder().register(new ResourceLocation(Aether.MODID, "skyroot"), SKYROOT_BLOCK_SET);

    public static void init() {}
}
