package com.gildedgames.aether.common.item.materials.util;

import com.gildedgames.aether.common.event.events.SwetBallConvertEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Supplier;

public interface ISwetBallConversion
{
    Map<Block, BlockState> DEFAULT_CONVERSIONS = Maps.newHashMap();
    Map<ResourceLocation, Pair<Block, BlockState>> BIOME_CONVERSIONS = Maps.newHashMap();

    static void registerDefaultConversions() {
        registerDefaultConversion(() -> Blocks.DIRT, Blocks.GRASS_BLOCK::defaultBlockState);
        registerDefaultConversion(AetherBlocks.AETHER_DIRT, AetherBlocks.AETHER_GRASS_BLOCK.get()::defaultBlockState);
    }

    static void registerBiomeConversions() {
        registerBiomeConversion(new ResourceLocation("mushroom_fields"), () -> Blocks.DIRT, Blocks.MYCELIUM::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("mushroom_field_shore"), () -> Blocks.DIRT, Blocks.MYCELIUM::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("giant_tree_taiga"), () -> Blocks.GRASS_BLOCK, Blocks.PODZOL::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("giant_tree_taiga_hills"), () -> Blocks.GRASS_BLOCK, Blocks.PODZOL::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("bamboo_jungle"), () -> Blocks.GRASS_BLOCK, Blocks.PODZOL::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("bamboo_jungle_hills"), () -> Blocks.GRASS_BLOCK, Blocks.PODZOL::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("crimson_forest"), () -> Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM::defaultBlockState);
        registerBiomeConversion(new ResourceLocation("warped_forest"), () -> Blocks.NETHERRACK, Blocks.WARPED_NYLIUM::defaultBlockState);
    }

    static void registerDefaultConversion(Supplier<Block> oldBlock, Supplier<BlockState> newBlock) {
        if (!DEFAULT_CONVERSIONS.containsKey(oldBlock.get())) {
            DEFAULT_CONVERSIONS.put(oldBlock.get(), newBlock.get());
        }
    }

    static void removeDefaultConversion(Supplier<Block> oldBlock, Supplier<BlockState> newBlock) {
        if (DEFAULT_CONVERSIONS.containsKey(oldBlock.get())) {
            DEFAULT_CONVERSIONS.remove(oldBlock.get(), newBlock.get());
        }
    }

    static void registerBiomeConversion(ResourceLocation biome, Supplier<Block> oldBlock, Supplier<BlockState> newBlock) {
        if (!BIOME_CONVERSIONS.containsKey(biome)) {
            BIOME_CONVERSIONS.put(biome, Pair.of(oldBlock.get(), newBlock.get()));

        }
    }

    static void removeBiomeConversion(ResourceLocation biome, Supplier<Block> oldBlock, Supplier<BlockState> newBlock) {
        if (BIOME_CONVERSIONS.containsKey(biome)) {
            BIOME_CONVERSIONS.remove(biome, Pair.of(oldBlock.get(), newBlock.get()));
        }
    }

    default ActionResultType convertBlock(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack heldItem = context.getItemInHand();
        BlockState oldBlockState = world.getBlockState(pos);
        BlockState newBlockState = oldBlockState;

        Block oldBlock = oldBlockState.getBlock();
        if (DEFAULT_CONVERSIONS.containsKey(oldBlock)) {
            newBlockState = DEFAULT_CONVERSIONS.get(oldBlock);
        }

        Biome biome = world.getBiome(pos);
        ResourceLocation biomeName = biome.getRegistryName();
        if (BIOME_CONVERSIONS.containsKey(biomeName)) {
            Pair<Block, BlockState> blockPair = BIOME_CONVERSIONS.get(biomeName);
            if (blockPair.getKey() == oldBlockState.getBlock()) {
                newBlockState = blockPair.getValue();
            }
        }

        SwetBallConvertEvent event = AetherEventHooks.onSwetBallConvert(player, world, pos, heldItem, oldBlockState, newBlockState);
        if (!event.isCanceled()) {
            newBlockState = event.getNewBlockState();
            if (newBlockState != oldBlockState && world.getBlockState(pos.above()).isAir()) {
                world.setBlockAndUpdate(pos, newBlockState);
                if (player != null && !player.abilities.instabuild) {
                    heldItem.shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
}
