package com.gildedgames.aether.block;

import com.gildedgames.aether.event.events.FreezeEvent;
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

    int FLAG_SHELL = 1 | 2;
    int FLAG_VOLUME = 2 | 16;
    default int freezeBlocks(Level worldIn, BlockPos origin, T source, float radius) {
        float radiusSq = radius * radius;

        int blocksFrozen = 0;

        // This loop may be configured weird but this guarantees fully unique
        // placement positions while only updating the shell of its volume
        for (int x = (int) radius; x >= 0; x--) {
            boolean firstXZ = true;

            for (int z = (int) radius; z > 0; z--) {
                int xzLengthSq = x * x + z * z;

                if (xzLengthSq > radiusSq) continue;

                blocksFrozen += this.quarters(worldIn, source, origin, x, 0, z, firstXZ ? FLAG_SHELL : FLAG_VOLUME);
                firstXZ = false;

                boolean firstY = true;
                for (int y = (int) radius; y > 0; y--) {

                    if (xzLengthSq + y * y > radiusSq) continue;

                    int placementFlag = firstY ? FLAG_SHELL : FLAG_VOLUME;
                    blocksFrozen += this.quarters(worldIn, source, origin, x, y, z, placementFlag);
                    blocksFrozen += this.quarters(worldIn, source, origin, x, -y, z, placementFlag);
                    firstY = false;
                }
            }
        }

        // Update the center too
        return this.freezeBlockAt(worldIn, source, origin, FLAG_SHELL) + blocksFrozen;
    }

    private int quarters(Level worldIn, T source, BlockPos origin, int dX, int dY, int dZ, int flag) {
        return this.freezeBlockAt(worldIn, source, origin.offset(dX, dY, dZ), flag)
                + this.freezeBlockAt(worldIn, source, origin.offset(-dZ, dY, dX), flag)
                + this.freezeBlockAt(worldIn, source, origin.offset(-dX, dY, -dZ), flag)
                + this.freezeBlockAt(worldIn, source, origin.offset(dZ, dY, -dX), flag);
    }

    private int freezeBlockAt(Level worldIn, T source, BlockPos pos, int flag) {
        BlockState priorState = worldIn.getBlockState(pos);

        if (FREEZABLES.containsKey(priorState.getBlock())) {
            BlockState frozenState = FREEZABLES.get(priorState.getBlock());
            FreezeEvent event = this.onFreeze(worldIn, pos, priorState, frozenState, source);
            if (!event.isCanceled()) {
                frozenState = event.getFrozenBlock();
                worldIn.setBlock(pos, frozenState, flag);
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
