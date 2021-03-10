package com.gildedgames.aether.common.entity.block;

import java.util.List;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.block.util.FloatingBlock;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
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
	private BlockState floatTile = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
	public int floatTime;
	public boolean shouldDropItem = true;
	private boolean dontSetBlock;
	private boolean hurtEntities;
	private int floatHurtMax = 40;
	private float floatHurtAmount = 2.0F;
	public CompoundNBT tileEntityData;
	protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.defineId(FloatingBlockEntity.class, DataSerializers.BLOCK_POS);
	
	public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);
	}

	public FloatingBlockEntity(World worldIn) {
		this(AetherEntityTypes.FLOATING_BLOCK.get(), worldIn);
	}

	public FloatingBlockEntity(World worldIn, double x, double y, double z, BlockState floatingBlockState) {
		super(AetherEntityTypes.FLOATING_BLOCK.get(), worldIn);

		this.floatTile = floatingBlockState;
		this.blocksBuilding = true;
		this.setPos(x, y + (1.0F - this.getBbHeight()) / 2.0F, z);
		this.setDeltaMovement(Vector3d.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.setOrigin(new BlockPos(this.blockPosition()));
	}
	
	@Override
	public boolean isAttackable() {
		return false;
	}
	
	public void setOrigin(BlockPos origin) {
		this.entityData.set(ORIGIN, origin);
	}
	
	@OnlyIn(Dist.CLIENT)
	public BlockPos getOrigin() {
		return this.entityData.get(ORIGIN);
	}
	
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	@Override
	protected void defineSynchedData() {
		this.entityData.define(ORIGIN, BlockPos.ZERO);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public boolean isPickable() {
		return !this.removed;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void tick() {
		if (this.floatTile.isAir()) {
			this.remove();
		}
		else {
			this.xo = this.getX();
			this.yo = this.getY();
			this.zo = this.getZ();
			Block block = this.floatTile.getBlock();
			if (this.floatTime++ == 0) {
				BlockPos blockpos = new BlockPos(this.blockPosition());
				if (this.level.getBlockState(blockpos).getBlock() == block) {
					this.level.removeBlock(blockpos, false);
				}
				else if (!this.level.isClientSide) {
					this.remove();
					return;
				}
			}

			if (!this.isNoGravity()) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.04, 0.0));
			}

			this.move(MoverType.SELF, this.getDeltaMovement());
			if (!this.level.isClientSide) {
				BlockPos blockpos1 = new BlockPos(this.blockPosition());
				boolean flag = this.floatTile.getBlock() instanceof ConcretePowderBlock;
				boolean flag1 = flag && this.level.getFluidState(blockpos1).is(FluidTags.WATER);
				double d0 = this.getDeltaMovement().lengthSqr();
				if (flag && d0 > 1.0) {
					BlockRayTraceResult blockraytraceresult = this.level
						.clip(new RayTraceContext(new Vector3d(this.xo, this.yo, this.zo),
							new Vector3d(this.getX(), this.getY(), this.getZ()), RayTraceContext.BlockMode.COLLIDER,
							RayTraceContext.FluidMode.SOURCE_ONLY, this));
					if (blockraytraceresult.getType() != RayTraceResult.Type.MISS
						&& this.level.getFluidState(blockraytraceresult.getBlockPos()).is(FluidTags.WATER)) {
						blockpos1 = blockraytraceresult.getBlockPos();
						flag1 = true;
					}
				}

				if ((!this.verticalCollision || this.onGround) && !flag1) {
					if (!this.level.isClientSide && (this.floatTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.floatTime > 600)) {
						if (this.shouldDropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS) && blockpos1.getY() <= 256) {
							this.spawnAtLocation(block);
						}

						this.remove();
					}
				}
				else {
					BlockState blockstate = this.level.getBlockState(blockpos1);
					this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, 1.5, 0.7));
					if (blockstate.getBlock() != Blocks.MOVING_PISTON) {
						this.remove();
						if (!this.dontSetBlock) {
							boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockpos1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
							boolean flag3 = this.floatTile.canSurvive(this.level, blockpos1);
							if (flag2 && flag3) {
								if (this.floatTile.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockpos1).getType() == Fluids.WATER) {
									this.floatTile = this.floatTile.setValue(BlockStateProperties.WATERLOGGED, true);
								}

								if (this.level.setBlock(blockpos1, this.floatTile, 3)) {
									if (block instanceof FloatingBlock) {
										((FloatingBlock) block).onEndFloating(this.level, blockpos1, this.floatTile, blockstate);
									}

									if (this.tileEntityData != null && this.floatTile.hasTileEntity()) {
										TileEntity tileentity = this.level.getBlockEntity(blockpos1);
										if (tileentity != null) {
											CompoundNBT compoundnbt = tileentity.save(new CompoundNBT());

											for (String s : this.tileEntityData.getAllKeys()) {
												INBT inbt = this.tileEntityData.get(s);
												if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
													compoundnbt.put(s, inbt.copy());
												}
											}

											tileentity.load(blockstate, compoundnbt);
											tileentity.setChanged();
										}
									}
								}
								else if (this.shouldDropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
									this.spawnAtLocation(block);
								}
							}
							else if (this.shouldDropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
								this.spawnAtLocation(block);
							}
						}
						else if (block instanceof FloatingBlock) {
							((FloatingBlock) block).onBroken(this.level, blockpos1);
						}
					}
				}
			}

			this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
		}
	}
	
	@Override
	public boolean causeFallDamage(float distance, float damageMultiplier) {
		if (this.hurtEntities) {
			int i = MathHelper.ceil(distance - 1.0F);
			if (i > 0) {
				List<Entity> list = Lists.newArrayList(this.level.getEntities(this, this.getBoundingBox()));
				boolean flag = this.floatTile.is(BlockTags.ANVIL);
				DamageSource damagesource = flag? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
				
				for(Entity entity : list) {
					entity.hurt(damagesource, Math.min(MathHelper.floor(i * this.floatHurtAmount), this.floatHurtMax));
				}
				
				if (flag && this.random.nextFloat() < 0.05F + i * 0.05F) {
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
	protected void addAdditionalSaveData(CompoundNBT compound) {
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
	protected void readAdditionalSaveData(CompoundNBT compound) {
		this.floatTile = NBTUtil.readBlockState(compound.getCompound("BlockState"));
		this.floatTime = compound.getInt("Time");
		if (compound.contains("HurtEntities", 99)) {
			this.hurtEntities = compound.getBoolean("HurtEntities");
			this.floatHurtAmount = compound.getFloat("FallHurtAmount");
			this.floatHurtMax = compound.getInt("FallHurtMax");
		}
		else if (this.floatTile.is(BlockTags.ANVIL)) {
			this.hurtEntities = true;
		}

		if (compound.contains("DropItem", 99)) {
			this.shouldDropItem = compound.getBoolean("DropItem");
		}

		if (compound.contains("TileEntityData", 10)) {
			this.tileEntityData = compound.getCompound("TileEntityData");
		}

		if (this.floatTile.isAir()) {
			this.floatTile = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
		}
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public World getWorldObj() {
		return this.level;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean displayFireAnimation() {
		return false;
	}

	@Override
	public void fillCrashReportCategory(CrashReportCategory category) {
		super.fillCrashReportCategory(category);
		category.setDetail("Immitating BlockState", this.floatTile.toString());
	}

	public BlockState getBlockState() {
		return this.floatTile;
	}

	public void setHurtEntities(boolean hurtEntitiesIn) {
		this.hurtEntities = hurtEntitiesIn;
	}

	@Override
	public boolean onlyOpCanSetNbt() {
		return true;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeVarInt(Block.getId(this.floatTile));
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.floatTile = Block.stateById(additionalData.readVarInt());
	}

}
