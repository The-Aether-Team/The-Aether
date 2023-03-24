package com.gildedgames.aether.block.construction;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.mixin.mixins.common.accessor.BushBlockAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class AetherFarmBlock extends FarmBlock {
    public AetherFarmBlock(Properties properties) {
        super(properties);
    }

    /**
     * Based on {@link FarmBlock#getStateForPlacement(BlockPlaceContext)}.
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState() : this.defaultBlockState();
    }

    /**
     * Copy of {@link FarmBlock#tick(BlockState, ServerLevel, BlockPos, RandomSource)}.
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnToDirt(state, level, pos);
        }
    }

    /**
     * Copy of {@link FarmBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}.
     */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (i > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, i - 1), 2);
            } else if (!isUnderCrops(level, pos)) {
                turnToDirt(state, level, pos);
            }
        } else if (i < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    /**
     * Based on {@link FarmBlock#fallOn(Level, BlockState, BlockPos, Entity, float)}.
     */
    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide() && ForgeHooks.onFarmlandTrample(level, pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), fallDistance, entity)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(state, level, pos);
        }
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }

    /**
     * Based on {@link FarmBlock#turnToDirt(Entity, BlockState, Level, BlockPos)}.
     */
    public static void turnToDirt(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), level, pos));
    }

    /**
     * Copy of {@link FarmBlock#isUnderCrops(BlockGetter, BlockPos)}.
     */
    private boolean isUnderCrops(BlockGetter level, BlockPos pos) {
        BlockState plant = level.getBlockState(pos.above());
        BlockState state = level.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable plantable && state.canSustainPlant(level, pos, Direction.UP, plantable);
    }

    /**
     * Copy of {@link FarmBlock#isNearWater(LevelReader, BlockPos)}.
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
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction direction, IPlantable plantable) {
        PlantType type = plantable.getPlantType(level, pos.relative(direction));
        return (plantable instanceof BushBlock bushBlock && ((BushBlockAccessor) bushBlock).callMayPlaceOn(state, level, pos)) || PlantType.CROP.equals(type) || PlantType.PLAINS.equals(type);
    }
}
