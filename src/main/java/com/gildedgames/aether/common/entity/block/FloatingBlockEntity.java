package com.gildedgames.aether.common.entity.block;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.util.FloatingBlock;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.google.common.collect.Lists;

import net.minecraft.block.*;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class FloatingBlockEntity extends Entity implements IEntityAdditionalSpawnData
{
    private BlockState blockState = AetherBlocks.GRAVITITE_ORE.get().defaultBlockState();
    public int time;
    private boolean cancelDrop;
    private boolean hurtEntities;

    private final List<Entity> carriedEntityList = new ArrayList<>();
    protected static final DataParameter<BlockPos> DATA_START_POS = EntityDataManager.defineId(FloatingBlockEntity.class, DataSerializers.BLOCK_POS);

    public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public FloatingBlockEntity(World world, double x, double y, double z, BlockState blockState) {
        this(AetherEntityTypes.FLOATING_BLOCK.get(), world);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vector3d.ZERO);
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
            this.remove();
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
                    BlockRayTraceResult blockraytraceresult = this.level.clip(new RayTraceContext(new Vector3d(this.xo, this.yo, this.zo), this.position(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.level.getFluidState(blockraytraceresult.getBlockPos()).is(FluidTags.WATER)) {
                        blockPos = blockraytraceresult.getBlockPos();
                        canConvert = true;
                    }
                }
                if ((!this.verticalCollision || this.onGround) && !canConvert) {
                    if (!this.level.isClientSide && (blockPos.getY() < 1 || blockPos.getY() > this.level.getMaxBuildHeight()) || this.time > 600) {
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.dropItem();
                        }
                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(blockPos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, 1.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.remove();
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
    public void remove() {
        this.resetCarriedEntities();
        super.remove();
    }

    private void dropItem() {
        if (this.level instanceof ServerWorld) {
            for (ItemStack stack : Block.getDrops(this.blockState, (ServerWorld) this.level, this.blockPosition(), null)) {
                this.spawnAtLocation(stack);
            }
        }
    }

    private void causeDamage(float p_225503_1_) {
        if (this.hurtEntities) {
            int i = MathHelper.ceil(p_225503_1_ - 1.0F);
            if (i > 0) {
                List<Entity> list = Lists.newArrayList(this.level.getEntities(this, this.getBoundingBox()));
                Aether.LOGGER.info(list);
                boolean flag = this.blockState.is(BlockTags.ANVIL);
                DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
                for (Entity entity : list) {
                    if (!(entity instanceof ItemEntity) || !((ItemEntity) entity).getItem().getItem().is(ItemTags.ANVIL)) {
                        entity.hurt(damagesource, (float) Math.min(MathHelper.floor((float) i * 2.0F), 40));
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
            Aether.LOGGER.info(true);
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
    public World getLevel() {
        return this.level;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
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
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        p_213281_1_.put("BlockState", NBTUtil.writeBlockState(this.blockState));
        p_213281_1_.putInt("Time", this.time);
        p_213281_1_.putBoolean("HurtEntities", this.hurtEntities);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        this.blockState = NBTUtil.readBlockState(p_70037_1_.getCompound("BlockState"));
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
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeVarInt(Block.getId(this.blockState));
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.blockState = Block.stateById(additionalData.readVarInt());
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory p_85029_1_) {
        super.fillCrashReportCategory(p_85029_1_);
        p_85029_1_.setDetail("Immitating BlockState", this.blockState.toString());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
