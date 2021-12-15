package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AetherFarmlandBlock extends FarmlandBlock
{
    public AetherFarmlandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
        if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
            turnToDirt(p_225534_1_, p_225534_2_, p_225534_3_);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
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
    public void fallOn(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), fallDistance, entityIn)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(worldIn.getBlockState(pos), worldIn, pos);
        }
        entityIn.causeFallDamage(fallDistance, 1.0F);
    }

    public static void turnToDirt(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), worldIn, pos));
    }

    private boolean isUnderCrops(IBlockReader p_176529_0_, BlockPos p_176529_1_) {
        BlockState plant = p_176529_0_.getBlockState(p_176529_1_.above());
        BlockState state = p_176529_0_.getBlockState(p_176529_1_);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(p_176529_0_, p_176529_1_, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
    }

    private static boolean isNearWater(IWorldReader p_176530_0_, BlockPos p_176530_1_) {
        for(BlockPos blockpos : BlockPos.betweenClosed(p_176530_1_.offset(-4, 0, -4), p_176530_1_.offset(4, 1, 4))) {
            if (p_176530_0_.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(p_176530_0_, p_176530_1_);
    }
}
