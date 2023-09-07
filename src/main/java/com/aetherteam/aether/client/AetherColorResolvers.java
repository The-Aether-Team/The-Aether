package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.MoaEggItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.BlockColorsAccessor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeableLeatherItem;
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
        Map<Holder.Reference<Block>, BlockColor> blockColors = ((BlockColorsAccessor) event.getBlockColors()).aether$getBlockColors();
        map.put(Blocks.GRASS, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.GRASS)));
        map.put(Blocks.FERN, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.FERN)));
        map.put(Blocks.TALL_GRASS, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.TALL_GRASS)));
        map.put(Blocks.LARGE_FERN, blockColors.get(ForgeRegistries.BLOCKS.getDelegateOrThrow(Blocks.LARGE_FERN)));

        for (Map.Entry<Block, BlockColor> entry : map.entrySet()) { // Recolors tintable plants when placed on Aether Grass and Enchanted Grass.
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

    @SubscribeEvent
    static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());
        for (MoaEggItem moaEggItem : MoaEggItem.moaEggs()) {
            event.register((color, itemProvider) -> moaEggItem.getColor(), moaEggItem);
        }
    }
}
