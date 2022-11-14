package com.gildedgames.aether.block.construction;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.mixin.mixins.accessor.BushBlockAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class AetherFarmlandBlock extends FarmBlock
{
    public AetherFarmlandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState() : this.defaultBlockState();
    }

    @Override
    public void tick(BlockState p_225534_1_, ServerLevel p_225534_2_, BlockPos p_225534_3_, RandomSource p_225534_4_) {
        if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
            turnToDirt(p_225534_1_, p_225534_2_, p_225534_3_);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (!state.canSurvive(worldIn, pos)) {
            turnToDirt(state, worldIn, pos);
        } else {
            int i = state.getValue(MOISTURE);
            if (!isNearWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
                if (i > 0) {
                    worldIn.setBlock(pos, state.setValue(MOISTURE, i - 1), 2);
                } else if (!isUnderCrops(worldIn, pos)) {
                    turnToDirt(state, worldIn, pos);
                }
            } else if (i < 7) {
                worldIn.setBlock(pos, state.setValue(MOISTURE, 7), 2);
            }
        }
    }

    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), fallDistance, entityIn)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(worldIn.getBlockState(pos), worldIn, pos);
        }
        entityIn.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }

    public static void turnToDirt(BlockState state, Level worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), worldIn, pos));
    }

    private boolean isUnderCrops(BlockGetter p_176529_0_, BlockPos p_176529_1_) {
        BlockState plant = p_176529_0_.getBlockState(p_176529_1_.above());
        BlockState state = p_176529_0_.getBlockState(p_176529_1_);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable plantable && state.canSustainPlant(p_176529_0_, p_176529_1_, Direction.UP, plantable);
    }

    private static boolean isNearWater(LevelReader p_176530_0_, BlockPos p_176530_1_) {
        for(BlockPos blockpos : BlockPos.betweenClosed(p_176530_1_.offset(-4, 0, -4), p_176530_1_.offset(4, 1, 4))) {
            if (p_176530_0_.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_176530_0_, p_176530_1_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));
        return (plantable instanceof BushBlock bushBlock && ((BushBlockAccessor) bushBlock).callMayPlaceOn(state, world, pos)) || PlantType.CROP.equals(type) || PlantType.PLAINS.equals(type);
    }
}
