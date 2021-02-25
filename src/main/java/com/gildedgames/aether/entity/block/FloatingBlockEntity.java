package com.gildedgames.aether.entity.block;

import java.util.List;

import com.gildedgames.aether.registry.AetherBlocks;
import com.gildedgames.aether.block.util.FloatingBlock;
import com.gildedgames.aether.registry.AetherEntityTypes;
import com.google.common.collect.Lists;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class FloatingBlockEntity extends Entity implements IEntityAdditionalSpawnData {
	private BlockState floatTile = AetherBlocks.GRAVITITE_ORE.get().getDefaultState();
	public int floatTime;
	public boolean shouldDropItem = true;
	private boolean dontSetBlock;
	private boolean hurtEntities;
	private int floatHurtMax = 40;
	private float floatHurtAmount = 2.0F;
	public CompoundNBT tileEntityData;
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(FloatingBlockEntity.class, DataSerializers.BLOCK_POS);
	
	public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public FloatingBlockEntity(World worldIn) {
		this(AetherEntityTypes.FLOATING_BLOCK.get(), worldIn);
	}

	public FloatingBlockEntity(World worldIn, double x, double y, double z, BlockState floatingBlockState) {
		super(AetherEntityTypes.FLOATING_BLOCK.get(), worldIn);

		this.floatTile = floatingBlockState;
		this.preventEntitySpawning = true;
		this.setPosition(x, y + (1.0F - this.getHeight()) / 2.0F, z);
		this.setMotion(Vector3d.ZERO);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.setOrigin(new BlockPos(this.getPosition()));
	}
	
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}
	
	public void setOrigin(BlockPos origin) {
		this.dataManager.set(ORIGIN, origin);
	}
	
	@OnlyIn(Dist.CLIENT)
	public BlockPos getOrigin() {
		return this.dataManager.get(ORIGIN);
	}
	
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}
	
	@Override
	protected void registerData() {
		this.dataManager.register(ORIGIN, BlockPos.ZERO);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean canBeCollidedWith() {
		return !this.removed;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void tick() {
		if (this.floatTile.isAir()) {
			this.remove();
		}
		else {
			this.prevPosX = this.getPosX();
			this.prevPosY = this.getPosY();
			this.prevPosZ = this.getPosZ();
			Block block = this.floatTile.getBlock();
			if (this.floatTime++ == 0) {
				BlockPos blockpos = new BlockPos(this.getPosition());
				if (this.world.getBlockState(blockpos).getBlock() == block) {
					this.world.removeBlock(blockpos, false);
				}
				else if (!this.world.isRemote) {
					this.remove();
					return;
				}
			}

			if (!this.hasNoGravity()) {
				this.setMotion(this.getMotion().add(0.0, 0.04, 0.0));
			}

			this.move(MoverType.SELF, this.getMotion());
			if (!this.world.isRemote) {
				BlockPos blockpos1 = new BlockPos(this.getPosition());
				boolean flag = this.floatTile.getBlock() instanceof ConcretePowderBlock;
				boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
				double d0 = this.getMotion().lengthSquared();
				if (flag && d0 > 1.0) {
					BlockRayTraceResult blockraytraceresult = this.world
						.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ),
							new Vector3d(this.getPosX(), this.getPosY(), this.getPosZ()), RayTraceContext.BlockMode.COLLIDER,
							RayTraceContext.FluidMode.SOURCE_ONLY, this));
					if (blockraytraceresult.getType() != RayTraceResult.Type.MISS
						&& this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
						blockpos1 = blockraytraceresult.getPos();
						flag1 = true;
					}
				}

				if ((!this.collidedVertically || this.onGround) && !flag1) {
					if (!this.world.isRemote && (this.floatTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.floatTime > 600)) {
						if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS) && blockpos1.getY() <= 256) {
							this.entityDropItem(block);
						}

						this.remove();
					}
				}
				else {
					BlockState blockstate = this.world.getBlockState(blockpos1);
					this.setMotion(this.getMotion().mul(0.7, 1.5, 0.7));
					if (blockstate.getBlock() != Blocks.MOVING_PISTON) {
						this.remove();
						if (!this.dontSetBlock) {
							boolean flag2 = blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
							boolean flag3 = this.floatTile.isValidPosition(this.world, blockpos1);
							if (flag2 && flag3) {
								if (this.floatTile.hasProperty(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
									this.floatTile = this.floatTile.with(BlockStateProperties.WATERLOGGED, true);
								}

								if (this.world.setBlockState(blockpos1, this.floatTile, 3)) {
									if (block instanceof FloatingBlock) {
										((FloatingBlock) block).onEndFloating(this.world, blockpos1, this.floatTile, blockstate);
									}

									if (this.tileEntityData != null && this.floatTile.hasTileEntity()) {
										TileEntity tileentity = this.world.getTileEntity(blockpos1);
										if (tileentity != null) {
											CompoundNBT compoundnbt = tileentity.write(new CompoundNBT());

											for (String s : this.tileEntityData.keySet()) {
												INBT inbt = this.tileEntityData.get(s);
												if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
													compoundnbt.put(s, inbt.copy());
												}
											}

											tileentity.read(blockstate, compoundnbt);
											tileentity.markDirty();
										}
									}
								}
								else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
									this.entityDropItem(block);
								}
							}
							else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
								this.entityDropItem(block);
							}
						}
						else if (block instanceof FloatingBlock) {
							((FloatingBlock) block).onBroken(this.world, blockpos1);
						}
					}
				}
			}

			this.setMotion(this.getMotion().scale(0.98));
		}
	}
	
	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		if (this.hurtEntities) {
			int i = MathHelper.ceil(distance - 1.0F);
			if (i > 0) {
				List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
				boolean flag = this.floatTile.isIn(BlockTags.ANVIL);
				DamageSource damagesource = flag? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
				
				for(Entity entity : list) {
					entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor(i * this.floatHurtAmount), this.floatHurtMax));
				}
				
				if (flag && this.rand.nextFloat() < 0.05F + i * 0.05F) {
					BlockState blockstate = AnvilBlock.damage(this.floatTile);
					if (blockstate == null) {
						this.dontSetBlock = true;
					}
					else {
						this.floatTile = blockstate;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("BlockState", NBTUtil.writeBlockState(this.floatTile));
		compound.putInt("Time", this.floatTime);
		compound.putBoolean("DropItem", this.shouldDropItem);
		compound.putBoolean("HurtEntities", this.hurtEntities);
		compound.putFloat("FallHurtAmount", this.floatHurtAmount);
		compound.putInt("FallHurtMax", this.floatHurtMax);
		if (this.tileEntityData != null) {
			compound.put("TileEntityData", this.tileEntityData);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.floatTile = NBTUtil.readBlockState(compound.getCompound("BlockState"));
		this.floatTime = compound.getInt("Time");
		if (compound.contains("HurtEntities", 99)) {
			this.hurtEntities = compound.getBoolean("HurtEntities");
			this.floatHurtAmount = compound.getFloat("FallHurtAmount");
			this.floatHurtMax = compound.getInt("FallHurtMax");
		}
		else if (this.floatTile.isIn(BlockTags.ANVIL)) {
			this.hurtEntities = true;
		}

		if (compound.contains("DropItem", 99)) {
			this.shouldDropItem = compound.getBoolean("DropItem");
		}

		if (compound.contains("TileEntityData", 10)) {
			this.tileEntityData = compound.getCompound("TileEntityData");
		}

		if (this.floatTile.isAir()) {
			this.floatTile = AetherBlocks.GRAVITITE_ORE.get().getDefaultState();
		}
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public World getWorldObj() {
		return this.world;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public void fillCrashReport(CrashReportCategory category) {
		super.fillCrashReport(category);
		category.addDetail("Immitating BlockState", this.floatTile.toString());
	}

	public BlockState getBlockState() {
		return this.floatTile;
	}

	public void setHurtEntities(boolean hurtEntitiesIn) {
		this.hurtEntities = hurtEntitiesIn;
	}

	@Override
	public boolean ignoreItemEntityData() {
		return true;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeVarInt(Block.getStateId(this.floatTile));
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.floatTile = Block.getStateById(additionalData.readVarInt());
	}

}
