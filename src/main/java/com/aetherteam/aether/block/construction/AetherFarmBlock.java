package com.aetherteam.aether.block.construction;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.BushBlockAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import net.neoforged.neoforge.common.util.TriState;

public class AetherFarmBlock extends FarmBlock {
    public AetherFarmBlock(Properties properties) {
        super(properties);
    }

    /**
     * [CODE COPY] - {@link FarmBlock#getStateForPlacement(BlockPlaceContext)}.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState() : this.defaultBlockState();
    }

    /**
     * [CODE COPY] - {@link FarmBlock#tick(BlockState, ServerLevel, BlockPos, RandomSource)}.
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToDirt(state, level, pos);
        }
    }

    /**
     * [CODE COPY] - {@link FarmBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (i > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, i - 1), 2);
            } else if (!shouldMaintainFarmland(level, pos)) {
                turnToDirt(state, level, pos);
            }
        } else if (i < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    /**
     * [CODE COPY] - {@link FarmBlock#fallOn(Level, BlockState, BlockPos, Entity, float)}.
     */
    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide() && CommonHooks.onFarmlandTrample(level, pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), fallDistance, entity)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(state, level, pos);
        }
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }

    /**
     * [CODE COPY] - {@link FarmBlock#turnToDirt(Entity, BlockState, Level, BlockPos)}.
     */
    public static void turnToDirt(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), level, pos));
    }

    /**
     * [CODE COPY] - {@link FarmBlock#shouldMaintainFarmland(BlockGetter, BlockPos)}.
     */
    private static boolean shouldMaintainFarmland(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }

    /**
     * [CODE COPY] - {@link FarmBlock#isNearWater(LevelReader, BlockPos)}.
     */
    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }

    /**
     * Chosen checks based on {@link net.minecraft.world.level.block.Block#canSustainPlant(BlockState, BlockGetter, BlockPos, Direction, IPlantable)}.
     */
    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        Block plantBlock = plant.getBlock();
        if ((plantBlock instanceof BushBlock bushBlock && ((BushBlockAccessor) bushBlock).callMayPlaceOn(Blocks.FARMLAND.defaultBlockState(), level, soilPosition))) {
            return TriState.TRUE;
        } else {
            return super.canSustainPlant(state, level, soilPosition, facing, plant);
        }
    }

    /**
     * [CODE COPY] - {@link net.neoforged.neoforge.common.extensions.IBlockExtension#isFertile(BlockState, BlockGetter, BlockPos)}.
     */
    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(FarmBlock.MOISTURE) > 0;
    }
}
