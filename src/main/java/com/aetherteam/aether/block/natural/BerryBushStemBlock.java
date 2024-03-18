package com.aetherteam.aether.block.natural;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BerryBushStemBlock extends AetherBushBlock implements BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

	public BerryBushStemBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}

	/**
	 * [CODE COPY] - {@link net.minecraft.world.level.block.SweetBerryBushBlock#entityInside(BlockState, Level, BlockPos, Entity)}.<br><br>
	 * Modified to remove damage behavior.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (AetherConfig.SERVER.berry_bush_consistency.get()) {
			if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
				entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
			}
		}
	}

	/**
	 * Turns a Berry Bush Stem into a Berry Bush with a 1/60 chance during a random tick, as long as the light level is 9 or above.<br><br>
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 * @param state The {@link BlockState} of the block.
	 * @param level The {@link Level} the block is in.
	 * @param pos The {@link BlockPos} of the block.
	 * @param random The {@link RandomSource} from the level.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getRawBrightness(pos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(level, pos, state,random.nextInt(60) == 0)) {
			level.setBlockAndUpdate(pos, AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)));
			ForgeHooks.onCropsGrowPost(level, pos, state);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState blockState) {
		return true;
	}

	/**
	 * Turns a Berry Bush Stem into a Berry Bush when Bone Meal is used on it.
	 * @param level The {@link Level} the block is in.
	 * @param random The {@link RandomSource} from the level.
	 * @param pos The {@link BlockPos} of the block.
	 * @param state The {@link BlockState} of the block.
	 */
	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, AetherBlocks.BERRY_BUSH.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)));
	}

	/**
	 * Returns a Bone Meal success rate of ~9/20.
	 * @param level The {@link Level} the block is in.
	 * @param random The {@link RandomSource} from the level.
	 * @param pos The {@link BlockPos} of the block.
	 * @param state The {@link BlockState} of the block.
	 * @return A {@link Boolean} of whether using Bone Meal was successful.
	 */
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return random.nextFloat() <= 0.45;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	/**
	 * Warning for "deprecation" is suppressed because the method is fine to override.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
