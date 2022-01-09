package com.gildedgames.aether.common.entity.block;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.aether.common.block.util.FloatingBlock;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.google.common.collect.Lists;

import net.minecraft.CrashReportCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class FloatingBlockEntity extends Entity implements IEntityAdditionalSpawnData
{
    private BlockState blockState = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
    public int time;
    private boolean cancelDrop;
    private boolean hurtEntities;

    private final List<Entity> carriedEntityList = new ArrayList<>();
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.BLOCK_POS);

    public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityType, Level world) {
        super(entityType, world);
    }

    public FloatingBlockEntity(Level world, double x, double y, double z, BlockState blockState) {
        this(AetherEntityTypes.FLOATING_BLOCK.get(), world);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
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
            this.remove(RemovalReason.DISCARDED);
        } else {
            this.time++;
            Block block = this.blockState.getBlock();
            this.handleCarriedEntities();
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.04D, 0.0D));
            }
            this.move(MoverType.SELF, getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos blockPos = this.blockPosition();
                boolean isConcrete = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean canConvert = isConcrete && this.level.getFluidState(blockPos).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (isConcrete && d0 > 1.0D) {
                    BlockHitResult blockraytraceresult = this.level.clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != HitResult.Type.MISS && this.level.getFluidState(blockraytraceresult.getBlockPos()).is(FluidTags.WATER)) {
                        blockPos = blockraytraceresult.getBlockPos();
                        canConvert = true;
                    }
                }
                if ((!this.verticalCollision || this.onGround) && !canConvert) {
                    if (!this.level.isClientSide && (blockPos.getY() < 1 || blockPos.getY() > this.level.getMaxBuildHeight()) || this.time > 600) {
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.dropItem();
                        }
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(blockPos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, 1.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.remove(RemovalReason.KILLED);
                        if (!this.cancelDrop) {
                            boolean canBeReplaced = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level, blockPos, Direction.UP, ItemStack.EMPTY, Direction.DOWN));
                            boolean canSurvive = this.blockState.canSurvive(this.level, blockPos);
                            if (canBeReplaced && canSurvive) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level.getFluidState(blockPos).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE);
                                }
                                if (this.level.setBlock(blockPos, this.blockState, 3)) {
                                    if (block instanceof FloatingBlock) {
                                        ((FloatingBlock) block).onLand(this.level, blockPos, this.blockState, blockstate, this);
                                    }
                                } else if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.dropItem();
                                }
                            } else if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                this.dropItem();
                            }
                        } else if (block instanceof FloatingBlock) {
                            ((FloatingBlock) block).onBroken(this.level, blockPos, this);
                        }
                    }
                }
                this.causeDamage(this.time / 10.0F);
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
            this.floatEntities();
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        this.resetCarriedEntities();
        super.remove(reason);
    }

    private void dropItem() {
        if (this.level instanceof ServerLevel) {
            for (ItemStack stack : Block.getDrops(this.blockState, (ServerLevel) this.level, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
        }
    }

    private void causeDamage(float p_225503_1_) {
        if (this.hurtEntities) {
            int i = Mth.ceil(p_225503_1_ - 1.0F);
            if (i > 0) {
                List<Entity> list = Lists.newArrayList(this.level.getEntities(this, this.getBoundingBox()));
                boolean flag = this.blockState.is(BlockTags.ANVIL);
                DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                for (Entity entity : list) {
                    if (!(entity instanceof ItemEntity) || !((ItemEntity) entity).getItem().is(ItemTags.ANVIL)) {
                        entity.hurt(damagesource, (float) Math.min(Mth.floor((float) i * 2.0F), 40));
                    }
                }
                if (flag && (double) this.random.nextFloat() < (double) 0.05F + (double) i * 0.05D) {
                    BlockState blockstate = AnvilBlock.damage(this.blockState);
                    if (blockstate == null) {
                        this.cancelDrop = true;
                    } else {
                        this.blockState = blockstate;
                    }
                }
            }
        }
    }

    private void floatEntities() {
        for (Entity entity : this.getCarriedEntityList()) {
            entity.setNoGravity(true);
            entity.setOnGround(true);
            entity.fallDistance *= 0.0F;
            entity.setPos(entity.getX(), getY() + 1.0D, entity.getZ());
        }
    }

    private void handleCarriedEntities() {
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().expandTowards(0.0D, 0.1D, 0.0D));
        if (!list.equals(this.getCarriedEntityList())) {
            this.resetCarriedEntities();
        }
        for (Entity entity : list) {
            if (!this.getCarriedEntityList().contains(entity)) {
                this.getCarriedEntityList().add(entity);
            }
        }
    }

    private void resetCarriedEntities() {
        for (Entity entity : this.getCarriedEntityList()) {
            entity.setNoGravity(false);
        }
        this.getCarriedEntityList().clear();
    }

    public List<Entity> getCarriedEntityList() {
        return this.carriedEntityList;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public void setHurtsEntities(boolean p_145806_1_) {
        this.hurtEntities = p_145806_1_;
    }

    public void setStartPos(BlockPos p_184530_1_) {
        this.entityData.set(DATA_START_POS, p_184530_1_);
    }

    @OnlyIn(Dist.CLIENT)
    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    @OnlyIn(Dist.CLIENT)
    public Level getLevel() {
        return this.level;
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
    @OnlyIn(Dist.CLIENT)
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {
        p_213281_1_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_213281_1_.putInt("Time", this.time);
        p_213281_1_.putBoolean("HurtEntities", this.hurtEntities);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {
        this.blockState = NbtUtils.readBlockState(p_70037_1_.getCompound("BlockState"));
        this.time = p_70037_1_.getInt("Time");
        if (p_70037_1_.contains("HurtEntities", 99)) {
            this.hurtEntities = p_70037_1_.getBoolean("HurtEntities");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeVarInt(Block.getId(this.blockState));
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.blockState = Block.stateById(additionalData.readVarInt());
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory p_85029_1_) {
        super.fillCrashReportCategory(p_85029_1_);
        p_85029_1_.setDetail("Immitating BlockState", this.blockState.toString());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
