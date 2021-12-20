package com.gildedgames.aether.common.item.accessories.abilities;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.gildedgames.aether.common.event.hooks.AetherEventHooks;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
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

    default void handleLiquidFreezing(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Level world = livingEntity.level;
                    BlockPos pos = livingEntity.blockPosition();
                    BlockPos newPos = pos.offset(x, y, z);
                    BlockState state = world.getBlockState(newPos);
                    Block block = state.getBlock();
                    if (block instanceof LiquidBlock) {
                        FluidState fluidState = state.getFluidState();
                        if (FREEZABLES.containsKey(fluidState.getType())) {
                            BlockState frozenState = FREEZABLES.get(fluidState.getType()).defaultBlockState();
                            FreezeEvent.FreezeFromItem event = AetherEventHooks.onItemFreezeFluid(world, newPos, fluidState, frozenState, stack);
                            if (!event.isCanceled()) {
                                frozenState = event.getFrozenBlock();
                                world.setBlockAndUpdate(newPos, frozenState);
                                if (fluidState.is(FluidTags.LAVA)) {
                                    world.playSound(null, newPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                                }
                                if ((x + y + z) % 3 == 0) {
                                    stack.hurtAndBreak(1, livingEntity, (entity) -> CuriosApi.getCuriosHelper().onBrokenCurio(identifier, index, entity));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
