package com.gildedgames.aether.block.natural;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.block.util.IAetherDoubleDropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AercloudBlock extends HalfTransparentBlock implements IAetherDoubleDropBlock
{
	private static final BooleanProperty DOUBLE_DROPS = AetherBlockStateProperties.DOUBLE_DROPS;
	protected static final VoxelShape COLLISION_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 0.01, 16.0);
	protected static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);
	
	public AercloudBlock(BlockBehaviour.Properties properties) {
		super(properties.isRedstoneConductor((state, reader, pos) -> false).isSuffocating((state, reader, pos) -> false).isViewBlocking((state, reader, pos) -> false));
		this.registerDefaultState(this.defaultBlockState().setValue(DOUBLE_DROPS, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DOUBLE_DROPS);
	}

	@Override
	public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		entity.resetFallDistance();
		if (entity.getDeltaMovement().y < 0.0) {
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.005, 1.0));
		}
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) { }

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entityCollisionContext) {
			Entity entity = entityCollisionContext.getEntity();
			if (entity != null) {
				if (entity.fallDistance > 2.5F) {
					return FALLING_COLLISION_SHAPE;
				}
			}
		}
		return getAlternateShape(state, worldIn, pos, context);
	}

	public VoxelShape getAlternateShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return COLLISION_SHAPE;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}
}
