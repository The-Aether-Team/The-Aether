package com.aether.block;

import java.util.Random;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aether.Aether;
import com.aether.particles.AetherParticleTypes;
import com.aether.util.AetherSoundEvents;
import com.aether.world.AetherDimensions;
import com.aether.world.AetherTeleporter;
import com.google.common.cache.LoadingCache;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlaySoundEventPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Aether.MODID)
public class AetherPortalBlock extends NetherPortalBlock {

	public AetherPortalBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		// do nothing
	}	
	
	@Override
	public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
		AetherPortalBlock.Size aetherportalblock$size = this.isPortal(worldIn, pos);
		if (aetherportalblock$size != null
			&& !net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(worldIn, pos, aetherportalblock$size)) {
			aetherportalblock$size.placePortalBlocks();
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	@Nullable
	public AetherPortalBlock.Size isPortal(IWorld world, BlockPos pos) {
		AetherPortalBlock.Size aetherportalblock$size = new AetherPortalBlock.Size(world, pos, Direction.Axis.X);
		if (aetherportalblock$size.isValid() && aetherportalblock$size.portalBlockCount == 0) {
			return aetherportalblock$size;
		}
		else {
			AetherPortalBlock.Size aetherportalblock$size1 = new AetherPortalBlock.Size(world, pos, Direction.Axis.Z);
			return (aetherportalblock$size1.isValid() && aetherportalblock$size1.portalBlockCount == 0)? aetherportalblock$size1 : null;
		}
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
		BlockPos currentPos, BlockPos facingPos) {
		Direction.Axis direction$axis = facing.getAxis();
		Direction.Axis direction$axis1 = stateIn.get(AXIS);
		boolean flag = direction$axis1 != direction$axis && direction$axis.isHorizontal();
		return !flag && facingState.getBlock() != this
			&& !(new AetherPortalBlock.Size(worldIn, currentPos, direction$axis1)).func_208508_f()
				? Blocks.AIR.getDefaultState()
				: stateIn;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {
			setPortal(entityIn, pos);
		}
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				AetherSoundEvents.BLOCK_AETHER_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d0 = pos.getX() + rand.nextFloat();
			double d1 = pos.getY() + rand.nextFloat();
			double d2 = pos.getZ() + rand.nextFloat();
			double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;
			if (worldIn.getBlockState(pos.west()).getBlock() != this
				&& worldIn.getBlockState(pos.east()).getBlock() != this) {
				d0 = pos.getX() + 0.5D + 0.25D * j;
				d3 = rand.nextFloat() * 2.0F * j;
			}
			else {
				d2 = pos.getZ() + 0.5D + 0.25D * j;
				d5 = rand.nextFloat() * 2.0F * j;
			}

			worldIn.addParticle(AetherParticleTypes.AETHER_PORTAL, d0, d1, d2, d3, d4, d5);
		}

	}
	
	private void setPortal(Entity entity, BlockPos pos) {
		if (entity.timeUntilPortal > 0) {
			entity.timeUntilPortal = entity.getPortalCooldown();
		}
		else {
			if (!entity.world.isRemote && !pos.equals(entity.lastPortalPos)) {
				entity.lastPortalPos = new BlockPos(pos);
				BlockPattern.PatternHelper blockpattern$patternhelper = AetherBlocks.AETHER_PORTAL.createPatternHelper(entity.world, entity.lastPortalPos);
				double d0 = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? blockpattern$patternhelper.getFrontTopLeft().getZ() : blockpattern$patternhelper.getFrontTopLeft().getX();
	            double d1 = Math.abs(MathHelper.pct((blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? entity.posZ : entity.posX) - (blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 1 : 0), d0, d0 - blockpattern$patternhelper.getWidth()));
	            double d2 = MathHelper.pct(entity.posY - 1.0, blockpattern$patternhelper.getFrontTopLeft().getY(), blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight());
	            entity.lastPortalVec = new Vec3d(d1, d2, 0.0);
	            entity.teleportDirection = blockpattern$patternhelper.getForwards();
			}
			
			entity.inPortal = true;
		}
	}
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@SubscribeEvent
	public static void onEntityChangeDimension(EntityTravelToDimensionEvent event) {
		Entity entity = event.getEntity();
//		if ((event.getDimension() != DimensionType.THE_NETHER || entity.dimension != DimensionType.OVERWORLD) 
//			&& (event.getDimension() != DimensionType.OVERWORLD || entity.dimension != AetherDimensions.THE_AETHER)) {
//			return; // do nothing
//		}
		if (event.getDimension() != DimensionType.THE_NETHER || entity.dimension != DimensionType.OVERWORLD && entity.dimension != AetherDimensions.THE_AETHER) {
			return; // do nothing
		}
		
		BlockPos pos = entity.getPosition();
		Block block = entity.world.getBlockState(pos).getBlock();
//		boolean inPortal = false;
//		
		LOGGER.debug("Entity change dimension: block @ " + pos + " = " + block.getRegistryName());
//		if (block == AetherBlocks.AETHER_PORTAL) {
//			inPortal = true;
//		}
//		else {
//			for (Direction direction : Direction.Plane.HORIZONTAL) {
//				BlockPos pos2 = pos.offset(direction);
//				block = entity.world.getBlockState(pos2).getBlock();
//				LOGGER.debug("Entity change dimension: block @ " + pos2 + " = " + block.getRegistryName());
//				if (block == AetherBlocks.AETHER_PORTAL) {
//					inPortal = true;
//					break;
//				}
//			}
//		}
		if (!containsAetherPortal(entity.getBoundingBox(), entity.world)) {
			return; // not in an Aether Portal, do nothing	
		}
		
		// Entity is in Aether Portal, cancel event and start new one with Aether as the destination
		event.setCanceled(true);
		changeAetherDimension(entity, entity.dimension == DimensionType.OVERWORLD);
	}
	
	protected static boolean containsAetherPortal(AxisAlignedBB bb, IWorld world) {
		int i = MathHelper.floor(bb.minX);
		int j = MathHelper.ceil(bb.maxX);
		int k = MathHelper.floor(bb.minY);
		int l = MathHelper.ceil(bb.maxY);
		int i1 = MathHelper.floor(bb.minZ);
		int j1 = MathHelper.ceil(bb.maxZ);

		try (BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.retain()) {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						BlockState blockstate = world.getBlockState(pos.setPos(k1, l1, i2));
						if (blockstate.getBlock() == AetherBlocks.AETHER_PORTAL) {
							LOGGER.debug("FOUND AETHER PORTAL, INTERSECTS WITH ENTITY BB = {}",
								blockstate.getShape(world, pos).getBoundingBox().intersects(bb));
							return true;
						}
					}
				}
			}

			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	protected static Entity changeAetherDimension(Entity self, boolean goToAether) {
		DimensionType destination = goToAether? AetherDimensions.THE_AETHER : DimensionType.OVERWORLD;
		if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(self, destination)) {
			return null;
		}
		if (self instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)self;
			player.invulnerableDimensionChange = true;
			DimensionType dimensiontype = player.dimension;
			ServerWorld serverworld = player.server.getWorld(dimensiontype);
			player.dimension = destination;
			ServerWorld serverworld1 = player.server.getWorld(destination);
			WorldInfo worldinfo = player.world.getWorldInfo();
			net.minecraftforge.fml.network.NetworkHooks.sendDimensionDataPacket(player.connection.netManager, player);
			player.connection.sendPacket(new SRespawnPacket(destination, worldinfo.getGenerator(), player.interactionManager.getGameType()));
			player.connection.sendPacket(new SServerDifficultyPacket(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
			PlayerList playerlist = player.server.getPlayerList();
			playerlist.updatePermissionLevel(player);
			serverworld.removeEntity(player, true);
			player.revive();
			double d0 = player.posX;
			double d1 = player.posY;
			double d2 = player.posZ;
			float f = player.rotationPitch;
			float f1 = player.rotationYaw;
			float f2 = f1;
			serverworld.getProfiler().startSection("moving");
			double moveFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
			d0 *= moveFactor;
			d2 *= moveFactor;
//				if (dimensiontype == DimensionType.OVERWORLD && destination == DimensionType.THE_NETHER) {
//					player.enteredNetherPosition = new Vec3d(player.posX, player.posY, player.posZ);
//				}
//				else if (dimensiontype == DimensionType.OVERWORLD && destination == DimensionType.THE_END) {
//					BlockPos blockpos = serverworld1.getSpawnCoordinate();
//					d0 = blockpos.getX();
//					d1 = blockpos.getY();
//					d2 = blockpos.getZ();
//					f1 = 90.0F;
//					f = 0.0F;
//				}

			player.setLocationAndAngles(d0, d1, d2, f1, f);
			serverworld.getProfiler().endSection();
			serverworld.getProfiler().startSection("placing");
			double d7 = Math.min(-2.9999872E7, serverworld1.getWorldBorder().minX() + 16.0);
			double d4 = Math.min(-2.9999872E7, serverworld1.getWorldBorder().minZ() + 16.0);
			double d5 = Math.min(2.9999872E7, serverworld1.getWorldBorder().maxX() - 16.0);
			double d6 = Math.min(2.9999872E7, serverworld1.getWorldBorder().maxZ() - 16.0);
			d0 = MathHelper.clamp(d0, d7, d5);
			d2 = MathHelper.clamp(d2, d4, d6);
			player.setLocationAndAngles(d0, d1, d2, f1, f);
			Teleporter teleporter = getAetherTeleporter(serverworld1);
			if (!teleporter.func_222268_a(player, f2)) {
				teleporter.makePortal(player);
				teleporter.func_222268_a(player, f2);
			}

			serverworld.getProfiler().endSection();
			player.setWorld(serverworld1);
			serverworld1.func_217447_b(player);
			player.func_213846_b(serverworld);
			player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, f1, f);
			player.interactionManager.setWorld(serverworld1);
			player.connection.sendPacket(new SPlayerAbilitiesPacket(player.abilities));
			playerlist.sendWorldInfo(player, serverworld1);
			playerlist.sendInventory(player);

			for (EffectInstance effectinstance : player.getActivePotionEffects()) {
				player.connection.sendPacket(new SPlayEntityEffectPacket(player.getEntityId(), effectinstance));
			}

			player.connection.sendPacket(new SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false));
			player.lastExperience = -1;
			player.lastHealth = -1.0f;
			player.lastFoodLevel = -1;
			net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerChangedDimensionEvent(player, dimensiontype, destination);
			return player;
		} else if (!self.world.isRemote && !self.removed) {
			if (self instanceof EnderPearlEntity) {
				((EnderPearlEntity)self).owner = null;
			} else if (self instanceof ContainerMinecartEntity) {
				((ContainerMinecartEntity)self).dropContentsWhenDead(false);
			} else if (self instanceof AbstractVillagerEntity) {
				((AbstractVillagerEntity)self).setCustomer((PlayerEntity)null);
			}
			self.world.getProfiler().startSection("changeDimension");
			MinecraftServer minecraftserver = self.getServer();
			DimensionType dimensiontype = self.dimension;
			ServerWorld serverworld = minecraftserver.getWorld(dimensiontype);
			ServerWorld serverworld1 = minecraftserver.getWorld(destination);
			self.dimension = destination;
			self.detach();
			self.world.getProfiler().startSection("reposition");
			Vec3d vec3d = self.getMotion();
			float f = 0.0F;
			BlockPos blockpos;
			
			double movementFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
			double d0 = self.posX * movementFactor;
			double d1 = self.posZ * movementFactor;

			double d3 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minX() + 16.0D);
			double d4 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minZ() + 16.0D);
			double d5 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxX() - 16.0D);
			double d6 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxZ() - 16.0D);
			d0 = MathHelper.clamp(d0, d3, d5);
			d1 = MathHelper.clamp(d1, d4, d6);
			Vec3d vec3d1 = self.getLastPortalVec();
			blockpos = new BlockPos(d0, self.posY, d1);
			Teleporter teleporter = getAetherTeleporter(serverworld1);
			BlockPattern.PortalInfo blockpattern$portalinfo = teleporter.func_222272_a(blockpos, vec3d, self.getTeleportDirection(), vec3d1.x, vec3d1.y, self instanceof PlayerEntity);
			if (blockpattern$portalinfo == null) {
				return null;
			}

			blockpos = new BlockPos(blockpattern$portalinfo.field_222505_a);
			vec3d = blockpattern$portalinfo.field_222506_b;
			f = blockpattern$portalinfo.field_222507_c;

			self.world.getProfiler().endStartSection("reloading");
			Entity entity = self.getType().create(serverworld1);
			if (entity != null) {
				entity.copyDataFromOld(self);
				entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw + f, entity.rotationPitch);
				entity.setMotion(vec3d);
				serverworld1.func_217460_e(entity);
			}

			self.remove(false);
			self.world.getProfiler().endSection();
			serverworld.resetUpdateEntityTick();
			serverworld1.resetUpdateEntityTick();
			self.world.getProfiler().endSection();
			
			if (!self.world.isRemote && entity instanceof ItemEntity) {
				((ItemEntity)entity).searchForOtherItemsNearby();
			}
			
			return entity;
		}
		else {
			return null;
		}
	}
	
	public static AetherTeleporter getAetherTeleporter(ServerWorld world) {
		if (!world.customTeleporters.isEmpty()) {
			for (Teleporter teleporter : world.customTeleporters) {
				if (teleporter instanceof AetherTeleporter) {
					return (AetherTeleporter)teleporter;
				}
			}
		}
		AetherTeleporter teleporter = new AetherTeleporter(world);
		world.customTeleporters.add(teleporter);
		return teleporter;
	}
	
//	@SubscribeEvent
//	public static void onWorldLoad(WorldEvent.Load event) {
//		DimensionType dimension = event.getWorld().getDimension().getType();
//		if (dimension != DimensionType.OVERWORLD && dimension != AetherDimensions.THE_AETHER) {
//			return;
//		}
//		
//		ServerWorld world = (ServerWorld) event.getWorld();
//		world.customTeleporters.add(new AetherTeleporter(world));
//	}
	
	@SubscribeEvent
	public static void onNeighborNotifyEvent(NeighborNotifyEvent event) {
		BlockPos pos = event.getPos();
		IWorld world = event.getWorld();
		
		BlockState blockstate = world.getBlockState(pos);
		IFluidState fluidstate = world.getFluidState(pos);
		if (fluidstate.getFluid() != Fluids.WATER || blockstate.isAir()) {
			return;
		}
//		LOGGER.debug("NOTIFY FROM " + fluidstate + " PLACED AT " + event.getPos());
		DimensionType dimensiontype = world.getDimension().getType();
		if (dimensiontype != DimensionType.OVERWORLD && dimensiontype != AetherDimensions.THE_AETHER) {
//			LOGGER.debug("INVALID DIMENSION {}", dimensiontype.getRegistryName());
			return;
		}
		
		boolean tryPortal = false;
		for (Direction direction : Direction.values()) {
			if (world.getBlockState(pos.offset(direction)).getBlock() == Blocks.GLOWSTONE) {
//				LOGGER.debug("Found glowstone at {}", pos.offset(direction));
				if (AetherBlocks.AETHER_PORTAL.isPortal(world, pos) != null) {
					tryPortal = true;
					break;
				} else {
//					LOGGER.debug("No portal found");
				}
			}
		}
		
//		LOGGER.debug("Try portal = {}", tryPortal);
		
		if (!tryPortal) {
			return;
		}
		
		if (AetherBlocks.AETHER_PORTAL.trySpawnPortal(world, pos)) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onBlockPlaced(EntityPlaceEvent event) {
		
	}
	
	@Override
	public BlockPattern.PatternHelper createPatternHelper(IWorld worldIn, BlockPos pos) {
		Direction.Axis direction$axis = Direction.Axis.Z;
		AetherPortalBlock.Size aetherportalblock$size = new AetherPortalBlock.Size(worldIn, pos, Direction.Axis.X);
		LoadingCache<BlockPos, CachedBlockInfo> loadingcache = BlockPattern.createLoadingCache(worldIn, true);
		if (!aetherportalblock$size.isValid()) {
			direction$axis = Direction.Axis.X;
			aetherportalblock$size = new AetherPortalBlock.Size(worldIn, pos, Direction.Axis.Z);
		}

		if (!aetherportalblock$size.isValid()) {
			return new BlockPattern.PatternHelper(pos, Direction.NORTH, Direction.UP, loadingcache, 1, 1, 1);
		}
		else {
			int[] aint = new int[Direction.AxisDirection.values().length];
			Direction direction = aetherportalblock$size.rightDir.rotateYCCW();
			BlockPos blockpos = aetherportalblock$size.bottomLeft.up(aetherportalblock$size.height - 1);

			for (Direction.AxisDirection direction$axisdirection : Direction.AxisDirection.values()) {
				BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(
					// pos
					(direction.getAxisDirection() == direction$axisdirection)
						? blockpos
						: blockpos.offset(aetherportalblock$size.rightDir, aetherportalblock$size.width - 1),
					// finger
					Direction.getFacingFromAxis(direction$axisdirection, direction$axis), 
					// thumb
					Direction.UP, 
					// lcache
					loadingcache,
					// width, height, depth
					aetherportalblock$size.width, aetherportalblock$size.height, 1);

				for (int i = 0; i < aetherportalblock$size.width; ++i) {
					for (int j = 0; j < aetherportalblock$size.height; ++j) {
						CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.translateOffset(i, j, 1);
						if (!cachedblockinfo.getBlockState().isAir()) {
							++aint[direction$axisdirection.ordinal()];
						}
					}
				}
			}

			Direction.AxisDirection direction$axisdirection1 = Direction.AxisDirection.POSITIVE;

			for (Direction.AxisDirection direction$axisdirection2 : Direction.AxisDirection.values()) {
				if (aint[direction$axisdirection2.ordinal()] < aint[direction$axisdirection1.ordinal()]) {
					direction$axisdirection1 = direction$axisdirection2;
				}
			}

			return new BlockPattern.PatternHelper(
				// pos
				(direction.getAxisDirection() == direction$axisdirection1)
					? blockpos
					: blockpos.offset(aetherportalblock$size.rightDir, aetherportalblock$size.width - 1),
				// finger
				Direction.getFacingFromAxis(direction$axisdirection1, direction$axis), 
				// thumb
				Direction.UP, 
				// lcache
				loadingcache,
				// width, height, depth
				aetherportalblock$size.width, aetherportalblock$size.height, 1);
		}
	}
	
	public static class Size extends NetherPortalBlock.Size {
		
		private final boolean initialized;

		public Size(IWorld world, BlockPos posIn, Axis axis) {
			super(world, posIn, axis);
			this.bottomLeft = null;
			this.height = this.width = 0;
			this.initialized = true;
			
			for(BlockPos blockpos = posIn; posIn.getY() > blockpos.getY() - 21 && posIn.getY() > 0 && this.func_196900_a(posIn.down()); posIn = posIn.down()) {
	            ;
	         }
			
//			LOGGER.debug("bottom pos = {}", posIn);
			
			int i = this.getDistanceUntilEdge(posIn, this.leftDir) - 1;
			if (i >= 0) {
				this.bottomLeft = posIn.offset(this.leftDir, i);
				this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
				if (this.width < 2 || this.width > 21) {
					this.bottomLeft = null;
					this.width = 0;
				}
				else {
					this.height = this.calculatePortalHeight();
				}
			}
		}
		
		@Override
		protected int getDistanceUntilEdge(BlockPos p_180120_1_, Direction p_180120_2_) {
			if (!initialized) {
				return 0;
			}
			
			int i;
			for (i = 0; i < 22; ++i) {
				BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);
				if (!this.func_196900_a(blockpos) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.GLOWSTONE) {
					break;
				}
			}

			Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
			if (block == Blocks.GLOWSTONE) {
				return i;
			} else {
				LogManager.getLogger().debug("FAILED ON BLOCK {}", block.getRegistryName());
			}
			return block == Blocks.GLOWSTONE? i : 0;
		}
		
		@Override
		protected int calculatePortalHeight() {
			if (!initialized) {
				return 0;
			}
			
			outerloop:
			for (this.height = 0; this.height < 21; ++this.height) {
				for (int i = 0; i < this.width; ++i) {
					BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
					BlockState blockstate = this.world.getBlockState(blockpos);
					if (!this.func_196900_a(blockpos)) {
						break outerloop;
					}

					Block block = blockstate.getBlock();
					if (block == AetherBlocks.AETHER_PORTAL) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();
						if (block != Blocks.GLOWSTONE) {
							break outerloop;
						}
					}
					else if (i == this.width - 1) {
						block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();
						if (block != Blocks.GLOWSTONE) {
							break outerloop;
						}
					}
				}
			}

			for (int j = 0; j < this.width; ++j) {
				if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.GLOWSTONE) {
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
		
		@Override
		public void placePortalBlocks() {
			BlockState portalState = AetherBlocks.AETHER_PORTAL.getDefaultState().with(AetherPortalBlock.AXIS, this.axis);
			for (int i = 0; i < this.width; ++i) {
				BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

				for (int j = 0; j < this.height; ++j) {
					this.world.setBlockState(blockpos.up(j), portalState, 18);
				}
			}
		}
				
		@Override
		@Deprecated
		protected boolean func_196900_a(BlockState state) {
			Block block = state.getBlock();
			return state.isAir() || block == Blocks.WATER || block == AetherBlocks.AETHER_PORTAL;
		}
		
		protected boolean func_196900_a(BlockPos pos) {
			if (this.world.isAirBlock(pos)) {
				return true;
			}
			
			Block block = this.world.getBlockState(pos).getBlock();
			return block == Blocks.WATER || block == AetherBlocks.AETHER_PORTAL;
		}
		
	}

}
