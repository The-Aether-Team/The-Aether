package com.gildedgames.aether.common.block.util;

import com.gildedgames.aether.common.event.events.FreezeEvent;
import com.google.common.collect.Maps;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Supplier;

public interface FreezingBehavior<T>
{
    Map<Block, BlockState> FREEZABLES = Maps.newHashMap();

    static void registerDefaultFreezables() { // TODO We should make this Data-Driven
        registerFreezableBlock(() -> Blocks.WATER, Blocks.ICE::defaultBlockState);
        registerFreezableBlock(() -> Blocks.LAVA, Blocks.OBSIDIAN::defaultBlockState);
    }

    static void registerFreezableBlock(Supplier<Block> freezableBlock, Supplier<BlockState> frozenBlock) {
        if (!FREEZABLES.containsKey(freezableBlock.get())) {
            FREEZABLES.put(freezableBlock.get(), frozenBlock.get());
        }
    }

    static void removeFreezableFluid(Supplier<Block> freezableBlock, Supplier<BlockState> frozenBlock) {
        if (FREEZABLES.containsKey(freezableBlock.get())) {
            FREEZABLES.remove(freezableBlock.get(), frozenBlock.get());
        }
    }

    default int freezeBlocks(Level worldIn, BlockPos origin, T source, float radius) {
        float radiusSq = radius * radius;

        int blocksFrozen = 0;

        for (int z = 1; z <= radius; z++) {
            for (int x = 0; x <= radius; x++) {
                int xzLengthSq = x * x + z * z;
                if (xzLengthSq > radiusSq) continue;

                blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(x, 0, z));
                blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-x, 0, -z));
                blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-z, 0, x));
                blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(z, 0, -x));

                for (int y = 1; y <= radius; y++) {
                    if (xzLengthSq + y * y > radiusSq) continue;

                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(x, y, z));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-x, y, -z));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-z, y, x));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(z, y, -x));

                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(x, -y, z));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-x, -y, -z));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(-z, -y, x));
                    blocksFrozen += this.freezeBlockAt(worldIn, source, origin.offset(z, -y, -x));
                }
            }
        }

        return blocksFrozen;
    }

    private int freezeBlockAt(Level worldIn, T source, BlockPos pos) {
        BlockState priorState = worldIn.getBlockState(pos);

        if (FREEZABLES.containsKey(priorState.getBlock())) {
            BlockState frozenState = FREEZABLES.get(priorState.getBlock());
            FreezeEvent event = this.onFreeze(worldIn, pos, priorState, frozenState, source);
            if (!event.isCanceled()) {
                frozenState = event.getFrozenBlock();
                worldIn.setBlockAndUpdate(pos, frozenState);
                if (priorState.getFluidState().is(FluidTags.LAVA)) {
                    worldIn.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                return 1;
            }
        }

        return 0;
    }

    FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, T source);
}
