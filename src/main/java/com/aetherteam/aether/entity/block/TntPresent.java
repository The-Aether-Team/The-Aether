package com.aetherteam.aether.entity.block;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.Nullable;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.item.PrimedTnt}.
 */
public class TntPresent extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(TntPresent.class, EntityDataSerializers.INT);
    @Nullable
    private LivingEntity owner;

    public TntPresent(EntityType<? extends TntPresent> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public TntPresent(Level level, double x, double y, double z, @Nullable LivingEntity owner) {
        this(AetherEntityTypes.TNT_PRESENT.get(), level);
        this.setPos(x, y, z);
        double d0 = level.getRandom().nextDouble() * Mth.TWO_PI;
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2, -Math.cos(d0) * 0.02);
        this.setFuse(10); // Short fuse.
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FUSE_ID, 80);
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide()) {
                this.level().explode(this, null, null, this.getX(), this.getY(0.0625), this.getZ(), 1.0F, false, Level.ExplosionInteraction.TNT);
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide()) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    public int getFuse() {
        return this.getEntityData().get(DATA_FUSE_ID);
    }

    public void setFuse(int fuse) {
        this.getEntityData().set(DATA_FUSE_ID, fuse);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }
   
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.15F;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("Fuse", (short) this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("Fuse")) {
            this.setFuse(tag.getShort("Fuse"));
        }
    }
   
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
