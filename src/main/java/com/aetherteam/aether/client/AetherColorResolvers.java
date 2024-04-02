package com.aetherteam.aether.client;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.MoaEggItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.BlockColorsAccessor;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.HashMap;
import java.util.Map;

public class AetherColorResolvers {
    private static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    private static final int ENCHANTED_GRASS_COLOR = 0xFCEA64;

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        Map<Block, BlockColor> map = new HashMap<>();
        Map<Block, BlockColor> blockColors = ((BlockColorsAccessor) event.getBlockColors()).aether$getBlockColors();
        map.put(Blocks.SHORT_GRASS, blockColors.get(Blocks.SHORT_GRASS));
        map.put(Blocks.FERN, blockColors.get(Blocks.FERN));
        map.put(Blocks.TALL_GRASS, blockColors.get(Blocks.TALL_GRASS));
        map.put(Blocks.LARGE_FERN, blockColors.get(Blocks.LARGE_FERN));

        for (Map.Entry<Block, BlockColor> entry : map.entrySet()) { // Recolors tintable plants when placed on Aether Grass and Enchanted Grass.
            event.register(((state, level, pos, tintIndex) -> {
                if (level != null && pos != null) {
                    BlockPos newPos = state.hasProperty(DoublePlantBlock.HALF) ? (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos) : pos;
                    BlockPos baseBlock = newPos.below();
                    if (level.getBlockState(baseBlock).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                        return ENCHANTED_GRASS_COLOR;
                    } else if (level.getBlockState(baseBlock).is(AetherBlocks.AETHER_GRASS_BLOCK.get())) {
                        return AETHER_GRASS_COLOR;
                    }
                }
                return entry.getValue().getColor(state, level, pos, tintIndex);
            }), entry.getKey());
        }
    }

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        event.register((color, itemProvider) -> itemProvider > 0 ? -1 : ((DyeableLeatherItem) color.getItem()).getColor(color), AetherItems.LEATHER_GLOVES.get());
        for (MoaEggItem moaEggItem : MoaEggItem.moaEggs()) {
            event.register((color, itemProvider) -> moaEggItem.getColor(), moaEggItem);
        }
    }
}
