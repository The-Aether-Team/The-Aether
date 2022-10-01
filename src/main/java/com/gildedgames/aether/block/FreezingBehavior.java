package com.gildedgames.aether.block;

import com.gildedgames.aether.event.events.FreezeEvent;
import com.gildedgames.aether.util.ConstantsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface FreezingBehavior<T> {
    int FLAG_SHELL = ConstantsUtil.FLAG_BLOCK_UPDATE | ConstantsUtil.FLAG_CLIENT_CHANGE;
    int FLAG_VOLUME = ConstantsUtil.FLAG_CLIENT_CHANGE | ConstantsUtil.FLAG_PREVENT_NEIGHBOR_UPDATE;

    default int freezeBlocks(Level worldIn, BlockPos origin, T source, float radius) {
        float radiusSq = radius * radius;

        int blocksFrozen = 0;

        // This loop may be configured weird but this guarantees fully unique
        // placement positions while only updating the shell of its volume
        for (int x = (int) radius; x >= 0; x--) {
            boolean firstXZ = true;

            for (int z = (int) radius; z >= 0; z--) {
                int xzLengthSq = x * x + z * z;

                if (xzLengthSq > radiusSq) continue;

                blocksFrozen += this.quarters(worldIn, source, origin, x, 0, z, firstXZ ? FLAG_SHELL : FLAG_VOLUME);
                firstXZ = false;

                boolean firstY = true;
                for (int y = (int) radius; y >= 0; y--) {

                    if (xzLengthSq + y * y > radiusSq) continue;

                    int placementFlag = firstY ? FLAG_SHELL : FLAG_VOLUME;
                    blocksFrozen += this.quarters(worldIn, source, origin, x, y, z, placementFlag);
                    blocksFrozen += this.quarters(worldIn, source, origin, x, -y, z, placementFlag);
                    firstY = false;
                }
            }
        }

        // Update the center too
        return this.freezeFromRecipe(worldIn, source, origin, FLAG_SHELL) + blocksFrozen;
    }

    private int quarters(Level worldIn, T source, BlockPos origin, int dX, int dY, int dZ, int flag) {
        return this.freezeFromRecipe(worldIn, source, origin.offset(dX, dY, dZ), flag)
                + this.freezeFromRecipe(worldIn, source, origin.offset(-dZ, dY, dX), flag)
                + this.freezeFromRecipe(worldIn, source, origin.offset(-dX, dY, -dZ), flag)
                + this.freezeFromRecipe(worldIn, source, origin.offset(dZ, dY, -dX), flag);
    }

    default int freezeBlockAt(Level level, T source, BlockState oldBlockState, BlockState newBlockState, BlockPos pos, int flag) {
        FreezeEvent event = this.onFreeze(level, pos, oldBlockState, newBlockState, source);
        if (!event.isCanceled()) {
            level.setBlock(pos, newBlockState, flag);
            if (newBlockState.isRandomlyTicking()) {
                level.scheduleTick(pos, newBlockState.getBlock(), Mth.nextInt(level.getRandom(), 60, 120));
            }
            if (oldBlockState.getFluidState().is(FluidTags.LAVA)) {
                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return 1;
        }
        return 0;
    }

    int freezeFromRecipe(Level level, T source, BlockPos pos, int flag);

    FreezeEvent onFreeze(LevelAccessor world, BlockPos pos, BlockState fluidState, BlockState blockState, T source);
}
