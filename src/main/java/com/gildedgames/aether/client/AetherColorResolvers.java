package com.gildedgames.aether.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.mixin.mixins.accessor.BlockColorsAccessor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Aether.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AetherColorResolvers {
    private static final ColorResolver AETHER_GRASS = (biome, x, z) -> 0xB1FFCB;
    private static final ColorResolver ENCHANTED_GRASS = (biome, x, z) -> 0xFCEA64;

    @SubscribeEvent
    static void registerColorResolver(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(AETHER_GRASS);
        event.register(ENCHANTED_GRASS);
    }

    @SubscribeEvent
    static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        Map<Block, BlockColor> map = new HashMap<>();
        map.put(Blocks.GRASS, ((BlockColorsAccessor) event.getBlockColors()).getBlockColors().get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.GRASS)));
        map.put(Blocks.FERN, ((BlockColorsAccessor) event.getBlockColors()).getBlockColors().get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.FERN)));
        map.put(Blocks.TALL_GRASS, ((BlockColorsAccessor) event.getBlockColors()).getBlockColors().get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.TALL_GRASS)));
        map.put(Blocks.LARGE_FERN, ((BlockColorsAccessor) event.getBlockColors()).getBlockColors().get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.LARGE_FERN)));

        for (Map.Entry<Block, BlockColor> entry : map.entrySet()) {
            event.register(((state, level, pos, tintIndex) -> {
                if (level != null && pos != null) {
                    BlockPos newPos = state.hasProperty(DoublePlantBlock.HALF) ? (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos) : pos;
                    if (level.getBlockState(newPos.below()).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                        return level.getBlockTint(newPos, ENCHANTED_GRASS);
                    } else if (level.getBlockState(newPos.below()).is(AetherBlocks.AETHER_GRASS_BLOCK.get())) {
                        return level.getBlockTint(newPos, AETHER_GRASS);
                    }
                }
                return entry.getValue().getColor(state, level, pos, tintIndex);
            }), entry.getKey());
        }
    }
}
