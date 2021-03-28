package com.gildedgames.aether.common.item.accessories.abilities;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Map;
import java.util.function.Supplier;

public interface IIceAccessory
{
    Map<Fluid, Block> FREEZABLES = Maps.newHashMap();

    static void registerDefaultFreezables() {
        registerFreezableFluid(() -> Fluids.WATER, () -> Blocks.ICE);
        registerFreezableFluid(() -> Fluids.FLOWING_WATER, () -> Blocks.ICE);
        registerFreezableFluid(() -> Fluids.LAVA, () -> Blocks.OBSIDIAN);
        registerFreezableFluid(() -> Fluids.FLOWING_LAVA, () -> Blocks.OBSIDIAN);
    }

    static void registerFreezableFluid(Supplier<Fluid> fluid, Supplier<Block> block) {
        if (!FREEZABLES.containsKey(fluid.get())) {
            FREEZABLES.put(fluid.get(), block.get());
        }
    }

    static void removeFreezableFluid(Supplier<Fluid> fluid, Supplier<Block> block) {
        if (FREEZABLES.containsKey(fluid.get())) {
            FREEZABLES.remove(fluid.get(), block.get());
        }
    }

    static void handleLiquidFreezing(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    World world = livingEntity.level;
                    BlockPos pos = livingEntity.blockPosition();
                    BlockPos newPos = pos.offset(x, y, z);
                    BlockState state2 = world.getBlockState(newPos);
                    Block block = state2.getBlock();
                    if (block instanceof FlowingFluidBlock) {
                        FluidState fluidState = state2.getFluidState();
                        if (FREEZABLES.containsKey(fluidState.getType())) {
                            world.setBlockAndUpdate(newPos, FREEZABLES.get(fluidState.getType()).defaultBlockState());
                            if (fluidState.is(FluidTags.LAVA)) {
                                world.playSound(null, newPos, SoundEvents.LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            }
                            stack.hurtAndBreak(1, livingEntity, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, entity));
                        }
                    }
                }
            }
        }
    }
}
