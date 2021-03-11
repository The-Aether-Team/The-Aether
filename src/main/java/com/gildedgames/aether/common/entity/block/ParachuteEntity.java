package com.gildedgames.aether.common.entity.block;

import com.gildedgames.aether.core.api.registers.ParachuteType;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.registry.AetherParachuteTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ParachuteEntity extends Entity
{
    private static final DataParameter<Integer> DATA_PLAYER_ID = EntityDataManager.defineId(PlayerEntity.class, DataSerializers.INT);
    private static final DataParameter<String> DATA_PARACHUTE_TYPE = EntityDataManager.defineId(PlayerEntity.class, DataSerializers.STRING);

    public ParachuteEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_PLAYER_ID, 0);
        this.entityData.define(DATA_PARACHUTE_TYPE, "");
    }

    @Override
    public void tick() {
        if (this.getPlayer() != null) {
            Entity player = this.getPlayer();
            Vector3d motion = player.getDeltaMovement();

            player.fallDistance = 0.0F;
            if (motion.y < 0.0) {
                player.setDeltaMovement(motion.multiply(1.0, 0.6, 1.0));
            }

            this.moveToEntityUsing();
            this.spawnExplosionParticle();
        } else {
            this.kill();
        }
    }

    public void spawnExplosionParticle() {
        for (int i = 0; i < 1; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            double d4 = this.getX() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d0 * d3;
            double d5 = this.getY() + ((double) this.random.nextFloat() * this.getBbHeight()) - d1 * d3;
            double d6 = this.getZ() + ((double) this.random.nextFloat() * this.getBbWidth() * 2.0D) - this.getBbWidth() - d2 * d3;
            this.level.addParticle(ParticleTypes.POOF, d4, d5, d6, d0, d1, d2);
        }
    }

    private void moveToEntityUsing() {
        this.setPos(this.getPlayer().getX(), this.getPlayer().getY() - 1.0, this.getPlayer().getZ());

        if (isCollided()) {
            this.die();
        }
    }

    private boolean isCollided() {
        return this.getPlayer().isOnGround() || this.getPlayer().isInWater();
    }

    public void die() {
        this.spawnExplosionParticle();
        this.kill();
        IAetherPlayer.get(this.getPlayer()).ifPresent((player) -> player.setParachute(null));
    }

    public void setPlayer(int id) {
        this.entityData.set(DATA_PLAYER_ID, id);
    }

    public PlayerEntity getPlayer() {
        return (PlayerEntity) this.level.getEntity(this.entityData.get(DATA_PLAYER_ID));
    }

    public void setParachuteType(String type) {
        this.entityData.set(DATA_PARACHUTE_TYPE, type);
    }

    public ParachuteType getParachuteType() {
        return AetherParachuteTypes.PARACHUTES.get(new ResourceLocation(this.entityData.get(DATA_PARACHUTE_TYPE)));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) { }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
