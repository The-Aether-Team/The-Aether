package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BlockColors.class)
public interface BlockColorsAccessor {
    @Accessor("blockColors")
    Map<Block, BlockColor> aether$getBlockColors();
}