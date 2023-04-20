package com.aetherteam.aether.entity.projectile.dart;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class AbstractDart extends AbstractArrow {
    private int ticksInAir = 0;

    protected AbstractDart(EntityType<? extends AbstractDart> type, Level level) {
        super(type, level);
    }

    public AbstractDart(EntityType<? extends AbstractDart> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {
            ++this.ticksInAir;
        }
        if (this.ticksInAir > 500) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult result) {
        super.onHitBlock(result);
        this.setNoGravity(false);
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult result) {
        super.onHitEntity(result);
        this.setNoGravity(false);
    }

    @Nonnull
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherSoundEvents.ENTITY_DART_HIT.get();
    }

    @Nonnull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
