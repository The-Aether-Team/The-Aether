package com.gildedgames.aether.common.block.construction;

import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AetherFarmlandBlock extends Block {

    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public AetherFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP && !stateIn.canSurvive(worldIn, currentPos)) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.above());
        return !blockstate.getMaterial().isSolid() || blockstate.getBlock() instanceof FenceGateBlock || blockstate.getBlock() instanceof MovingPistonBlock;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? AetherBlocks.AETHER_DIRT.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.canSurvive(worldIn, pos)) {
            turnToDirt(state, worldIn, pos);
        } else {
            int i = state.getValue(MOISTURE);
            if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
                if (i > 0) {
                    worldIn.setBlock(pos, state.setValue(MOISTURE, i - 1), 2);
                } else if (!hasCrops(worldIn, pos)) {
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

        super.fallOn(worldIn, pos, entityIn, fallDistance);
    }

    public static void turnToDirt(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, AetherBlocks.AETHER_DIRT.get().defaultBlockState(), worldIn, pos));
    }

    private boolean hasCrops(IBlockReader worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos.above());
        return state.getBlock() instanceof net.minecraftforge.common.IPlantable && canSustainPlant(state, worldIn, pos, Direction.UP, (net.minecraftforge.common.IPlantable)state.getBlock());
    }

    private static boolean hasWater(IWorldReader worldIn, BlockPos pos) {
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (worldIn.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
