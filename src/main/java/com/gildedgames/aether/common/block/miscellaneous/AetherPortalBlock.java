package com.gildedgames.aether.common.block.miscellaneous;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherParticleTypes;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.common.world.AetherTeleporter;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;

@EventBusSubscriber(modid = Aether.MODID)
public class AetherPortalBlock extends Block
{
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	protected static final VoxelShape X_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final VoxelShape Z_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

	public AetherPortalBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Axis.X));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		switch (state.getValue(AXIS)) {
			case Z:
				return Z_AABB;
			case X:
			default:
				return X_AABB;
		}
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		Axis directionAxis = facing.getAxis();
		Axis stateAxis = stateIn.getValue(AXIS);
		boolean flag = stateAxis != directionAxis && directionAxis.isHorizontal();
		return (!flag && facingState.getBlock() != this && !(new AetherPortalBlock.Size(worldIn, currentPos, stateAxis)).canCreatePortal()) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entity) {
		if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {
			if (entity.isOnPortalCooldown()) {
				entity.setPortalCooldown();
			} else {
				if (!entity.level.isClientSide && !pos.equals(entity.portalEntrancePos)) {
					entity.portalEntrancePos = pos.immutable();
				}
				LazyOptional<IAetherPlayer> aetherPlayer = entity.getCapability(AetherCapabilities.AETHER_PLAYER_CAPABILITY);
				if (!aetherPlayer.isPresent()) {
					handleTeleportation(entity);
				} else {
					aetherPlayer.ifPresent(handler -> {
						handler.setInPortal(true);
						int waitTime = handler.getPortalTimer();
						if (waitTime >= entity.getPortalWaitTime()) {
							handleTeleportation(entity);
							handler.setPortalTimer(0);
						}
					});
				}
			}
		}
	}

	public boolean trySpawnPortal(LevelAccessor worldIn, BlockPos pos) {
		AetherPortalBlock.Size aetherPortalSize = this.isPortal(worldIn, pos);
		if (aetherPortalSize != null) {
			aetherPortalSize.placePortalBlocks();
			return true;
		} else {
			return false;
		}
	}

	private void handleTeleportation(Entity entity) {
		Level serverworld = entity.level;
		if (serverworld != null) {
			MinecraftServer minecraftserver = serverworld.getServer();
			ResourceKey<Level> where2go = entity.level.dimension() == AetherDimensions.AETHER_WORLD ? Level.OVERWORLD : AetherDimensions.AETHER_WORLD;
			if (minecraftserver != null) {
				ServerLevel destination = minecraftserver.getLevel(where2go);
				if (destination != null && minecraftserver.isNetherEnabled() && !entity.isPassenger()) {
					entity.level.getProfiler().push("aether_portal");
					entity.setPortalCooldown();
					entity.changeDimension(destination, new AetherTeleporter(destination, true));
					entity.level.getProfiler().pop();
				}
			}
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT.get(), SoundSource.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + rand.nextDouble();
			double z = pos.getZ() + rand.nextDouble();
			double sX = (rand.nextFloat() - 0.5) * 0.5;
			double sY = (rand.nextFloat() - 0.5) * 0.5;
			double sZ = (rand.nextFloat() - 0.5) * 0.5;
			int mul = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				x = pos.getX() + 0.5 + 0.25 * mul;
				sX = rand.nextFloat() * 2.0 * mul;
			}
			else {
				z = pos.getZ() + 0.5 + 0.25 * mul;
				sZ = rand.nextFloat() * 2.0 * mul;
			}

			worldIn.addParticle(AetherParticleTypes.AETHER_PORTAL.get(), x, y, z, sX, sY, sZ);
		}
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
	
	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(AXIS)) {
					case Z:
						return state.setValue(AXIS, Axis.X);
					case X:
						return state.setValue(AXIS, Axis.Z);
					default:
						return state;
				}
			default:
				return state;
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@SubscribeEvent
	public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
        BlockHitResult hitVec = event.getHitVec();
        BlockPos pos = hitVec.getBlockPos().relative(hitVec.getDirection());
		if (event.getItemStack().is(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS)) {
			if (!AetherConfig.COMMON.disable_aether_portal.get()) {
				if (fillPortalBlocks(event.getWorld(), pos, event.getPlayer(), event.getHand(), event.getItemStack())) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
		BlockPos pos = event.getPos();
		Level world = (Level) event.getWorld();
		BlockState blockstate = world.getBlockState(pos);
		FluidState fluidstate = world.getFluidState(pos);
		if (fluidstate.getType() == Fluids.WATER && !blockstate.isAir()) {
			if (world.dimension() == Level.OVERWORLD || world.dimension() == AetherDimensions.AETHER_WORLD) {
				boolean tryPortal = false;
				for (Direction direction : Direction.values()) {
					if (world.getBlockState(pos.relative(direction)).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS)) {
						if (AetherBlocks.AETHER_PORTAL.get().isPortal(world, pos) != null) {
							tryPortal = true;
							break;
						}
					}
				}
				if (tryPortal) {
					if (AetherBlocks.AETHER_PORTAL.get().trySpawnPortal(world, pos)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}

	private static boolean fillPortalBlocks(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
		if (world.dimension() == Level.OVERWORLD || world.dimension() == AetherDimensions.AETHER_WORLD) {
			boolean tryPortal = false;
			for (Direction direction : Direction.values()) {
				if (world.getBlockState(pos.relative(direction)).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS)) {
					if (AetherBlocks.AETHER_PORTAL.get().isPortal(world, pos) != null) {
						tryPortal = true;
						break;
					}
				}
			}
			if (tryPortal) {
				if (AetherBlocks.AETHER_PORTAL.get().trySpawnPortal(world, pos)) {
					player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
					player.swing(hand);
					if (!player.isCreative()) {
						if (stack.getCount() > 1) {
							stack.shrink(1);
							player.addItem(stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY);
						} else if (stack.isDamageableItem()) {
							stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
						} else {
							player.setItemInHand(hand, stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY);
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	public static boolean fillPortalBlocksWithoutContext(Level world, BlockPos pos, ItemStack stack) {
		if (world.dimension() == Level.OVERWORLD || world.dimension() == AetherDimensions.AETHER_WORLD) {
			boolean tryPortal = false;
			for (Direction direction : Direction.values()) {
				if (world.getBlockState(pos.relative(direction)).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS)) {
					if (AetherBlocks.AETHER_PORTAL.get().isPortal(world, pos) != null) {
						tryPortal = true;
						break;
					}
				}
			}
			if (tryPortal) {
				if (AetherBlocks.AETHER_PORTAL.get().trySpawnPortal(world, pos)) {
					if (stack.isDamageableItem()) {
						int damage = stack.getDamageValue();
						stack.setDamageValue(damage + 1);
						if (stack.getDamageValue() >= stack.getMaxDamage()) {
							stack.shrink(1);
						}
					} else {
						stack.shrink(1);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Nullable
	public AetherPortalBlock.Size isPortal(LevelAccessor world, BlockPos pos) {
		AetherPortalBlock.Size aetherPortalSizeX = new AetherPortalBlock.Size(world, pos, Axis.X);
		if (aetherPortalSizeX.isValid() && aetherPortalSizeX.portalBlockCount == 0) {
			return aetherPortalSizeX;
		}
		else {
			AetherPortalBlock.Size aetherPortalSizeZ = new AetherPortalBlock.Size(world, pos, Axis.Z);
			return aetherPortalSizeZ.isValid() && aetherPortalSizeZ.portalBlockCount == 0? aetherPortalSizeZ : null;
		}
	}

	public static class Size {
		protected final LevelAccessor world;
		public final Direction.Axis axis;
		public final Direction rightDir;
		public final Direction leftDir;
		public int portalBlockCount;
		@Nullable
		public BlockPos bottomLeft;
		public int height;
		public int width;

		public Size(LevelAccessor worldIn, BlockPos pos, Direction.Axis axisIn) {
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

			for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > worldIn.getMinBuildHeight()
				&& this.isEmptyBlock(worldIn.getBlockState(pos.below())); pos = pos.below()) {

			}

			int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
			if (i >= 0) {
				this.bottomLeft = pos.relative(this.leftDir, i);
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
				BlockPos blockpos = pos.relative(directionIn, i);
				if (!this.isEmptyBlock(this.world.getBlockState(blockpos))
					|| !(this.world.getBlockState(blockpos.below()).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS))) {
					break;
				}
			}

			BlockPos framePos = pos.relative(directionIn, i);
			return this.world.getBlockState(framePos).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS) ? i : 0;
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
					BlockPos blockpos = this.bottomLeft.relative(this.rightDir, i).above(this.height);
					BlockState blockstate = this.world.getBlockState(blockpos);
					if (!this.isEmptyBlock(blockstate)) {
						break outerloop;
					}

					Block block = blockstate.getBlock();
					if (block == AetherBlocks.AETHER_PORTAL.get()) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						BlockPos framePos = blockpos.relative(this.leftDir);
						if (!(this.world.getBlockState(framePos).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS))) {
							break outerloop;
						}
					}
					else if (i == this.width - 1) {
						BlockPos framePos = blockpos.relative(this.rightDir);
						if (!(this.world.getBlockState(framePos).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS))) {
							break outerloop;
						}
					}
				}
			}

			for (int j = 0; j < this.width; ++j) {
				BlockPos framePos = this.bottomLeft.relative(this.rightDir, j).above(this.height);
				if (!(this.world.getBlockState(framePos).is(AetherTags.Blocks.AETHER_PORTAL_BLOCKS))) {
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
				BlockPos blockpos = this.bottomLeft.relative(this.rightDir, i);

				for (int j = 0; j < this.height; ++j) {
					if (this.world instanceof Level) {
						Level world = (Level) this.world;
						world.setBlockAndUpdate(blockpos.above(j), AetherBlocks.AETHER_PORTAL.get().defaultBlockState().setValue(AetherPortalBlock.AXIS, this.axis));
					}
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
