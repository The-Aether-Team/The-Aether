package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.common.block.util.Floatable;
import com.gildedgames.aether.common.block.util.FloatingBlock;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FloatingBlockEntity extends Entity
{
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    private BlockState blockState;
    public int time;
    public boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    private long removeAtMillis;
    private int floatDistance;
    @Nullable
    public CompoundTag blockData;

    public FloatingBlockEntity(EntityType<FloatingBlockEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FloatingBlockEntity(Level level, double x, double y, double z, BlockState state) {
        this(AetherEntityTypes.FLOATING_BLOCK.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y + (double) ((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else if (this.level.isClientSide && this.removeAtMillis > 0L) {
            if (System.currentTimeMillis() >= this.removeAtMillis) {
                super.setRemoved(RemovalReason.DISCARDED);
            }
        } else {
            Block block = this.blockState.getBlock();
            if (this.time++ == 0) {
                BlockPos blockPos = this.blockPosition();
                if (this.level.getBlockState(blockPos).is(block)) {
                    this.level.removeBlock(blockPos, false);
                } else if (!this.level.isClientSide) {
                    this.discard();
                    return;
                }
            }

            if (!this.isNoGravity()) {
                this.floatDistance = this.blockPosition().getY() - this.getStartPos().getY();
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.04D, 0.0D));
                this.causeFallDamage();
                if (this.level.isClientSide) {
                    this.spawnFloatingBlockParticles();
                }
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockPos1 = this.blockPosition();
                boolean isConcrete = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean canConvert = isConcrete && this.level.getFluidState(blockPos1).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (isConcrete && d0 > 1.0D) {
                    BlockHitResult blockHitResult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && this.level.getFluidState(blockHitResult.getBlockPos()).is(FluidTags.WATER)) {
                        blockPos1 = blockHitResult.getBlockPos();
                        canConvert = true;
                    }
                }

                if ((!this.verticalCollision || this.onGround) && !canConvert) {
                    if (!this.level.isClientSide && (this.time > 100 && (blockPos1.getY() <= this.level.getMinBuildHeight() || blockPos1.getY() > this.level.getMaxBuildHeight()) || this.time > 600)) {
                        if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.dropBlock();
                        }
                        this.discard();
                    }
                } else {
                    BlockState blockState = this.level.getBlockState(blockPos1);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockState.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean canBeReplaced = blockState.canBeReplaced(new DirectionalPlaceContext(this.level, blockPos1, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
                            boolean isAboveFree = FloatingBlock.isFree(this.level.getBlockState(blockPos1.above())) && (!isConcrete || !canConvert);
                            boolean canBlockSurvive = this.blockState.canSurvive(this.level, blockPos1) && !isAboveFree;
                            if (canBeReplaced && canBlockSurvive) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockPos1).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                                }

                                if (this.level.setBlock(blockPos1, this.blockState, 3)) {
                                    ((ServerLevel) this.level).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockPos1, this.level.getBlockState(blockPos1)));
                                    this.discard();
                                    if (block instanceof Floatable floatable) {
                                        floatable.onCollide(this.level, blockPos1, this.blockState, blockState, this);
                                    } else if (block instanceof ConcretePowderBlock concretePowderBlock) {
                                        if (ConcretePowderBlock.shouldSolidify(this.level, blockPos1, blockState)) {
                                            this.level.setBlock(blockPos1, concretePowderBlock.concrete, 3);
                                        }
                                    } else if (block instanceof AnvilBlock) {
                                        if (!this.isSilent()) {
                                            this.level.levelEvent(1029, blockPos1, 0);
                                        }
                                    }

                                    if (this.blockData != null && this.blockState.hasBlockEntity()) {
                                        BlockEntity blockEntity = this.level.getBlockEntity(blockPos1);
                                        if (blockEntity != null) {
                                            CompoundTag tag = blockEntity.saveWithoutMetadata();
                                            for (String string : this.blockData.getAllKeys()) {
                                                tag.put(string, this.blockData.get(string).copy());
                                            }

                                            try {
                                                blockEntity.load(tag);
                                            } catch (Exception exception) {
                                                LOGGER.error("Failed to load block entity from floating block", exception);
                                            }
                                            blockEntity.setChanged();
                                        }
                                    }
                                } else if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockPos1);
                                    this.dropBlock();
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockPos1);
                                    this.dropBlock();
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockPos1);
                        }
                    }
                }
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }

    @Override
    public void setRemoved(@Nonnull Entity.RemovalReason removalReason) {
        if (this.level.shouldDelayFallingBlockEntityRemoval(removalReason)) {
            this.removeAtMillis = System.currentTimeMillis() + 50L;
        } else {
            super.setRemoved(removalReason);
        }
    }

    public void setHurtsEntities(float fallDamagePerDistance, int fallDamageMax) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = fallDamagePerDistance;
        this.fallDamageMax = fallDamageMax;
    }

    private void dropBlock() {
        if (this.level instanceof ServerLevel) {
            for (ItemStack stack : Block.getDrops(this.blockState, (ServerLevel) this.level, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos pos) {
        if (block instanceof Floatable floatable) {
            floatable.onBrokenAfterCollide(this.level, pos, this);
        }
    }

    private void causeFallDamage() {
        if (this.hurtEntities) {
            Predicate<Entity> predicate;
            DamageSource damageSource;
            if (this.blockState.getBlock() instanceof Floatable floatable) {
                predicate = floatable.getHurtsEntitySelector();
                damageSource = floatable.getFallDamageSource();
            } else {
                predicate = EntitySelector.NO_SPECTATORS;
                damageSource = DamageSource.FALLING_BLOCK;
            }

            float f = (float) Math.min(Mth.floor((float) this.floatDistance * this.fallDamagePerDistance), this.fallDamageMax);
            this.level.getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> p_149649_.hurt(damageSource, f));
            boolean flag = this.blockState.is(BlockTags.ANVIL);
            if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float) this.floatDistance * 0.05F) {
                BlockState blockstate = AnvilBlock.damage(this.blockState);
                if (blockstate == null) {
                    this.cancelDrop = true;
                } else {
                    this.blockState = blockstate;
                }
            }
        }
    }

    private void spawnFloatingBlockParticles() {
        if (this.random.nextInt(8) == 0) {
            double d0 = (this.getX() - 0.5) + this.random.nextDouble();
            double d1 = this.getY() - 0.05D;
            double d2 = (this.getZ() - 0.5) + this.random.nextDouble();
            this.level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, this.getBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public void setStartPos(BlockPos pOrigin) {
        this.entityData.set(DATA_START_POS, pOrigin);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Nonnull
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        tag.putInt("Time", this.time);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        tag.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            tag.put("TileEntityData", this.blockData);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.blockState = NbtUtils.readBlockState(tag.getCompound("BlockState"));
        this.time = tag.getInt("Time");
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallDamagePerDistance = tag.getFloat("FallHurtAmount");
            this.fallDamageMax = tag.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }
        if (tag.contains("TileEntityData", 10)) {
            this.blockData = tag.getCompound("TileEntityData");
        }
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(@Nonnull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1 + (double)((1.0F - this.getBbHeight()) / 2.0F), d2);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void fillCrashReportCategory(@Nonnull CrashReportCategory category) {
        super.fillCrashReportCategory(category);
        category.setDetail("Immitating BlockState", this.blockState.toString());
    }
}
