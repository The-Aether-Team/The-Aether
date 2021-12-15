package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;

public class TNTPresentEntity extends Entity
{
    private static final DataParameter<Integer> FUSE = EntityDataManager.defineId(TNTPresentEntity.class, DataSerializers.INT);
    private int fuse = 80;

    public TNTPresentEntity(EntityType<? extends TNTPresentEntity> type, World worldIn) {
        super(type, worldIn);
        this.blocksBuilding = true;
    }

    public TNTPresentEntity(World worldIn, double x, double y, double z) {
        this(AetherEntityTypes.TNT_PRESENT.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(10);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FUSE, 80);
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
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected Explosion explode() {
        DamagelessExplosion explosion = new DamagelessExplosion(this.level, this, null, null, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false, Explosion.Mode.BREAK);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(true);
        return explosion;
    }

    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    public void setFuse(int fuseIn) {
        this.entityData.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    public int getFuseDataManager() {
        return this.entityData.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private static class DamagelessExplosion extends Explosion
    {
        private final Explosion.Mode blockInteraction;
        private final World level;
        private final double x;
        private final double y;
        private final double z;
        @Nullable
        private final Entity source;
        private final float radius;
        private final ExplosionContext damageCalculator;
        private final List<BlockPos> toBlow = Lists.newArrayList();
        private final Map<PlayerEntity, Vector3d> hitPlayers = Maps.newHashMap();

        public DamagelessExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionContext context, double x, double y, double z, float radius, boolean fire, Explosion.Mode mode) {
            super(world, entity, damageSource, context, x, y, z, radius, fire, mode);
            this.level = world;
            this.source = entity;
            this.radius = radius;
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockInteraction = mode;
            this.damageCalculator = new ExplosionContext();
        }

        @Override
        public void explode() {
            Set<BlockPos> set = Sets.newHashSet();
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    for (int l = 0; l < 16; ++l) {
                        if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                            double d0 = (float) j / 15.0F * 2.0F - 1.0F;
                            double d1 = (float) k / 15.0F * 2.0F - 1.0F;
                            double d2 = (float) l / 15.0F * 2.0F - 1.0F;
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 = d0 / d3;
                            d1 = d1 / d3;
                            d2 = d2 / d3;
                            float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                            double d4 = this.x;
                            double d6 = this.y;
                            double d8 = this.z;

                            for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                                BlockPos blockpos = new BlockPos(d4, d6, d8);
                                BlockState blockstate = this.level.getBlockState(blockpos);
                                FluidState fluidstate = this.level.getFluidState(blockpos);
                                Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockpos, blockstate, fluidstate);
                                if (optional.isPresent()) {
                                    f -= (optional.get() + f1) * f1;
                                }

                                if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                                    set.add(blockpos);
                                }

                                d4 += d0 * (double) f1;
                                d6 += d1 * (double) f1;
                                d8 += d2 * (double) f1;
                            }
                        }
                    }
                }
            }

            this.toBlow.addAll(set);
            float f2 = this.radius * 2.0F;
            int k1 = MathHelper.floor(this.x - (double) f2 - 1.0D);
            int l1 = MathHelper.floor(this.x + (double) f2 + 1.0D);
            int i2 = MathHelper.floor(this.y - (double) f2 - 1.0D);
            int i1 = MathHelper.floor(this.y + (double) f2 + 1.0D);
            int j2 = MathHelper.floor(this.z - (double) f2 - 1.0D);
            int j1 = MathHelper.floor(this.z + (double) f2 + 1.0D);
            List<Entity> list = this.level.getEntities(this.source, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
            net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
            Vector3d vector3d = new Vector3d(this.x, this.y, this.z);

            for (int k2 = 0; k2 < list.size(); ++k2) {
                Entity entity = list.get(k2);
                if (!entity.ignoreExplosion()) {
                    double d12 = MathHelper.sqrt(entity.distanceToSqr(vector3d)) / f2;
                    if (d12 <= 1.0D) {
                        double d5 = entity.getX() - this.x;
                        double d7 = (entity instanceof TNTEntity ? entity.getY() : entity.getEyeY()) - this.y;
                        double d9 = entity.getZ() - this.z;
                        double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                        if (d13 != 0.0D) {
                            d5 = d5 / d13;
                            d7 = d7 / d13;
                            d9 = d9 / d13;
                            double d14 = getSeenPercent(vector3d, entity);
                            double d10 = (1.0D - d12) * d14;
                            entity.hurt(this.getDamageSource(), 0.0001F);
                            double d11 = d10;
                            if (entity instanceof LivingEntity) {
                                d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity)entity, d10);
                            }

                            entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                            if (entity instanceof PlayerEntity) {
                                PlayerEntity playerentity = (PlayerEntity) entity;
                                if (!playerentity.isSpectator() && (!playerentity.isCreative() || !playerentity.abilities.flying)) {
                                    this.hitPlayers.put(playerentity, new Vector3d(d5 * d10, d7 * d10, d9 * d10));
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void finalizeExplosion(boolean p_77279_1_) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
            }

            boolean flag = this.blockInteraction != Explosion.Mode.NONE;
            if (p_77279_1_) {
                if (!(this.radius < 2.0F) && flag) {
                    this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                } else {
                    this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
                }
            }

            if (flag) {
                ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
                Collections.shuffle(this.toBlow, this.level.random);

                for (BlockPos blockpos : this.toBlow) {
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    if (!blockstate.isAir(this.level, blockpos)) {
                        BlockPos blockpos1 = blockpos.immutable();
                        this.level.getProfiler().push("explosion_blocks");
                        if (blockstate.canDropFromExplosion(this.level, blockpos, this) && this.level instanceof ServerWorld) {
                            TileEntity tileentity = blockstate.hasTileEntity() ? this.level.getBlockEntity(blockpos) : null;
                            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.level)).withRandom(this.level.random).withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(blockpos)).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withOptionalParameter(LootParameters.BLOCK_ENTITY, tileentity).withOptionalParameter(LootParameters.THIS_ENTITY, this.source);
                            if (this.blockInteraction == Explosion.Mode.DESTROY) {
                                lootcontext$builder.withParameter(LootParameters.EXPLOSION_RADIUS, this.radius);
                            }

                            blockstate.getDrops(lootcontext$builder).forEach((p_229977_2_) -> {
                                addBlockDrops(objectarraylist, p_229977_2_, blockpos1);
                            });
                        }

                        blockstate.onBlockExploded(this.level, blockpos, this);
                        this.level.getProfiler().pop();
                    }
                }

                for (Pair<ItemStack, BlockPos> pair : objectarraylist) {
                    Block.popResource(this.level, pair.getSecond(), pair.getFirst());
                }
            }
        }

        private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> p_229976_0_, ItemStack p_229976_1_, BlockPos p_229976_2_) {
            int i = p_229976_0_.size();
            for (int j = 0; j < i; ++j) {
                Pair<ItemStack, BlockPos> pair = p_229976_0_.get(j);
                ItemStack itemstack = pair.getFirst();
                if (ItemEntity.areMergable(itemstack, p_229976_1_)) {
                    ItemStack itemstack1 = ItemEntity.merge(itemstack, p_229976_1_, 16);
                    p_229976_0_.set(j, Pair.of(itemstack1, pair.getSecond()));
                    if (p_229976_1_.isEmpty()) {
                        return;
                    }
                }
            }
            p_229976_0_.add(Pair.of(p_229976_1_, p_229976_2_));
        }
    }
}
