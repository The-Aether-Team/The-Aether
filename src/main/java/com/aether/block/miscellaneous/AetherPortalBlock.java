package com.aether.block.miscellaneous;

import com.aether.Aether;
import com.aether.registry.AetherParticleTypes;
import com.aether.registry.AetherBlocks;
import com.aether.registry.AetherSoundEvents;
import com.aether.world.AetherTeleporter;
import com.aether.registry.AetherDimensions;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.AbstractBlock;

@EventBusSubscriber(modid = Aether.MODID)
public class AetherPortalBlock extends Block {
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

	public AetherPortalBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Axis.X));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.get(AXIS)) {
			case Z:
				return Z_AABB;
			case X:
				return X_AABB;
			default:
				throw new AssertionError("Invalid value found for 'axis'");
		}
	}

	public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
		AetherPortalBlock.Size aetherPortalSize = this.isPortal(worldIn, pos);
		if (aetherPortalSize != null) {
			aetherPortalSize.placePortalBlocks();
			return true;
		}
		else {
			return false;
		}
	}
	
	@Nullable
	public AetherPortalBlock.Size isPortal(IWorld world, BlockPos pos) {
		AetherPortalBlock.Size aetherPortalSizeX = new AetherPortalBlock.Size(world, pos, Axis.X);
		if (aetherPortalSizeX.isValid() && aetherPortalSizeX.portalBlockCount == 0) {
			return aetherPortalSizeX;
		}
		else {
			AetherPortalBlock.Size aetherPortalSizeZ = new AetherPortalBlock.Size(world, pos, Axis.Z);
			return aetherPortalSizeZ.isValid() && aetherPortalSizeZ.portalBlockCount == 0? aetherPortalSizeZ : null;
		}
	}
	
	@Override
	@Deprecated
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Axis directionAxis = facing.getAxis();
		Axis stateAxis = stateIn.get(AXIS);
		boolean flag = stateAxis != directionAxis && directionAxis.isHorizontal();
		return (!flag && facingState.getBlock() != this && !(new AetherPortalBlock.Size(worldIn, currentPos, stateAxis)).canCreatePortal())? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos); 
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		if(!entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss()) {
			if(entity.func_242280_ah()) {
				entity.func_242279_ag();
			}
			else {
				if(!entity.world.isRemote && !pos.equals(entity.field_242271_ac)) {
					entity.field_242271_ac = pos.toImmutable();
				}
				World serverworld = entity.world;
				if(serverworld != null) {
					MinecraftServer minecraftserver = serverworld.getServer();
					RegistryKey<World> where2go = entity.world.getDimensionKey() == AetherDimensions.AETHER_WORLD ? World.OVERWORLD : AetherDimensions.AETHER_WORLD;
					if(minecraftserver != null) {
						ServerWorld destination = minecraftserver.getWorld(where2go);
						if(destination != null && minecraftserver.getAllowNether() && !entity.isPassenger()) {
							entity.world.getProfiler().startSection("aether_portal");
							entity.func_242279_ag();
							entity.changeDimension(destination, new AetherTeleporter(destination));
							entity.world.getProfiler().endSection();
						}
					}
				}
			}
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get(), SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double x = pos.getX() + rand.nextFloat();
			double y = pos.getY() + rand.nextFloat();
			double z = pos.getZ() + rand.nextFloat();
			double sX = (rand.nextFloat() - 0.5) * 0.5;
			double sY = (rand.nextFloat() - 0.5) * 0.5;
			double sZ = (rand.nextFloat() - 0.5) * 0.5;
			int mul = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				x = pos.getX() + 0.5 + 0.25 * mul;
				sX = rand.nextFloat() * 2.0F * mul;
			}
			else {
				z = pos.getZ() + 0.5 + 0.25 * mul;
				sZ = rand.nextFloat() * 2.0F * mul;
			}

			worldIn.addParticle(AetherParticleTypes.AETHER_PORTAL.get(), x, y, z, sX, sY, sZ);
		}
	}
	
	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
	
	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.get(AXIS)) {
					case Z:
						return state.with(AXIS, Axis.X);
					case X:
						return state.with(AXIS, Axis.Z);
					default:
						throw new AssertionError("Invalid value for 'axis'");
				}
			default:
				return state;
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
	
	@SubscribeEvent
	public static void onNeighborNotify(NeighborNotifyEvent event) {
		BlockPos pos = event.getPos();
		IWorld world = event.getWorld();
		
		BlockState blockstate = world.getBlockState(pos);
		FluidState fluidstate = world.getFluidState(pos);
		if (fluidstate.getFluid() != Fluids.WATER || blockstate.isAir(world, pos)) {
			return;
		}

		/* idk what to do with this
		DimensionType dimension = world.getDimension().getType();
		if (dimension != DimensionType.OVERWORLD && dimension != AetherDimensions.THE_AETHER) {
			return;
		}
		*/
		
		boolean tryPortal = false;
		for (Direction direction : Direction.values()) {
			if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.GLOWSTONE) {
				if (AetherBlocks.AETHER_PORTAL.get().isPortal(world, pos) != null) {
					tryPortal = true;
					break;
				}
			}
		}
		
		if (!tryPortal) {
			return;
		}
		
		if (AetherBlocks.AETHER_PORTAL.get().trySpawnPortal(world, pos)) {
			event.setCanceled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static BlockPattern.PatternHelper createPatternHelper(IWorld worldIn, BlockPos pos) {
		Axis axis = Axis.Z;
		AetherPortalBlock.Size size = new AetherPortalBlock.Size(worldIn, pos, Axis.X);
		LoadingCache<BlockPos, CachedBlockInfo> cache = BlockPattern.createLoadingCache(worldIn, true);
		if (!size.isValid()) {
			axis = Axis.X;
			size = new AetherPortalBlock.Size(worldIn, pos, Axis.Z);
		}

		if (!size.isValid()) {
			return new BlockPattern.PatternHelper(pos, Direction.NORTH, Direction.UP, cache, 1, 1, 1);
		}
		else {
			int[] axes = new int[AxisDirection.values().length];
			Direction direction = size.rightDir.rotateYCCW();
			BlockPos blockpos = size.bottomLeft.up(size.getHeight() - 1);

			for (AxisDirection axisDir : AxisDirection.values()) {
				BlockPattern.PatternHelper helper = new BlockPattern.PatternHelper((direction.getAxisDirection() == axisDir)? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), Direction.getFacingFromAxis(axisDir, axis), Direction.UP, cache, size.getWidth(), size.getHeight(), 1);

				for (int i = 0; i < size.getWidth(); ++i) {
					for (int j = 0; j < size.getHeight(); ++j) {
						CachedBlockInfo cachedInfo = helper.translateOffset(i, j, 1);
						if (!cachedInfo.getBlockState().isAir()) {
							++axes[axisDir.ordinal()];
						}
					}
				}
			}

			AxisDirection axisDirPos = AxisDirection.POSITIVE;

			for (AxisDirection axisDir : AxisDirection.values()) {
				if (axes[axisDir.ordinal()] < axes[axisDirPos.ordinal()]) {
					axisDirPos = axisDir;
				}
			}

			return new BlockPattern.PatternHelper((direction.getAxisDirection() == axisDirPos)? blockpos : blockpos.offset(size.rightDir, size.getWidth() - 1), Direction.getFacingFromAxis(axisDirPos, axis), Direction.UP, cache, size.getWidth(), size.getHeight(), 1);
		}
	}

	public static class Size {
		protected final IWorld world;
		public final Direction.Axis axis;
		public final Direction rightDir;
		public final Direction leftDir;
		public int portalBlockCount;
		@Nullable
		public BlockPos bottomLeft;
		public int height;
		public int width;

		public Size(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
			this.world = worldIn;
			this.axis = axisIn;
			if (axisIn == Direction.Axis.X) {
				this.leftDir = Direction.EAST;
				this.rightDir = Direction.WEST;
			}
			else {
				this.leftDir = Direction.NORTH;
				this.rightDir = Direction.SOUTH;
			}

			for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0
				&& this.isEmptyBlock(worldIn.getBlockState(pos.down())); pos = pos.down()) {
				;
			}

			int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
			if (i >= 0) {
				this.bottomLeft = pos.offset(this.leftDir, i);
				this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
				if (this.width < 2 || this.width > 21) {
					this.bottomLeft = null;
					this.width = 0;
				}
			}

			if (this.bottomLeft != null) {
				this.height = this.calculatePortalHeight();
			}

		}

		protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
			int i;
			for (i = 0; i < 22; ++i) {
				BlockPos blockpos = pos.offset(directionIn, i);
				if (!this.isEmptyBlock(this.world.getBlockState(blockpos))
					|| this.world.getBlockState(blockpos.down()).getBlock() != Blocks.GLOWSTONE) {
					break;
				}
			}

			BlockPos framePos = pos.offset(directionIn, i);
			return this.world.getBlockState(framePos).getBlock() == Blocks.GLOWSTONE? i : 0;
		}

		public int getHeight() {
			return this.height;
		}

		public int getWidth() {
			return this.width;
		}

		protected int calculatePortalHeight() {
			outerloop:
			for (this.height = 0; this.height < 21; ++this.height) {
				for (int i = 0; i < this.width; ++i) {
					BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
					BlockState blockstate = this.world.getBlockState(blockpos);
					if (!this.isEmptyBlock(blockstate)) {
						break outerloop;
					}

					Block block = blockstate.getBlock();
					if (block == AetherBlocks.AETHER_PORTAL.get()) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						BlockPos framePos = blockpos.offset(this.leftDir);
						if (this.world.getBlockState(framePos).getBlock() != Blocks.GLOWSTONE) {
							break outerloop;
						}
					}
					else if (i == this.width - 1) {
						BlockPos framePos = blockpos.offset(this.rightDir);
						if (this.world.getBlockState(framePos).getBlock() != Blocks.GLOWSTONE) {
							break outerloop;
						}
					}
				}
			}

			for (int j = 0; j < this.width; ++j) {
				BlockPos framePos = this.bottomLeft.offset(this.rightDir, j).up(this.height);
				if (this.world.getBlockState(framePos).getBlock() != Blocks.GLOWSTONE) {
					this.height = 0;
					break;
				}
			}

			if (this.height <= 21 && this.height >= 3) {
				return this.height;
			}
			else {
				this.bottomLeft = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}

		@SuppressWarnings("deprecation")
		protected boolean isEmptyBlock(BlockState pos) {
			Block block = pos.getBlock();
			return pos.isAir() || block == Blocks.WATER || block == AetherBlocks.AETHER_PORTAL.get();
		}

		public boolean isValid() {
			return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}

		public void placePortalBlocks() {
			for (int i = 0; i < this.width; ++i) {
				BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

				for (int j = 0; j < this.height; ++j) {
					this.world.setBlockState(blockpos.up(j), AetherBlocks.AETHER_PORTAL.get().getDefaultState().with(AetherPortalBlock.AXIS, this.axis), 18);
				}
			}

		}

		private boolean isLargeEnough() {
			return this.portalBlockCount >= this.width * this.height;
		}

		public boolean canCreatePortal() {
			return this.isValid() && this.isLargeEnough();
		}
	}

}
