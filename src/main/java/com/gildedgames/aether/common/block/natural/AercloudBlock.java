package com.gildedgames.aether.common.block.natural;

import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.block.util.IAetherDoubleDropBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AercloudBlock extends BreakableBlock implements IAetherDoubleDropBlock
{
	private static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;
	protected static VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 0.01, 16.0);
	
	public AercloudBlock(AbstractBlock.Properties properties) {
		super(properties.setOpaque((state, reader, pos) -> false).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false));
		this.setDefaultState(this.getDefaultState().with(DOUBLE_DROPS, false));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(DOUBLE_DROPS);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.fallDistance = 0.0F;

		if (entity.getMotion().y < 0.0) {
			entity.setMotion(entity.getMotion().mul(1.0, 0.005, 1.0));
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
}
