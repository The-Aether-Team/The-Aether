package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class BerryBushStemBlock extends AetherBushBlock implements BonemealableBlock
{
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

	public BerryBushStemBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_149653_1_) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
		if (worldIn.getRawBrightness(pos.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(60) == 0)) {
			worldIn.setBlockAndUpdate(pos, AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)));
			net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		return rand.nextFloat() < 0.45F;
	}

	@Override
	public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
		worldIn.setBlockAndUpdate(pos, AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)));
	}
}
