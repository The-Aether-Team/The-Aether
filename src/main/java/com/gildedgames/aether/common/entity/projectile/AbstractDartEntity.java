package com.gildedgames.aether.common.entity.projectile;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractDartEntity extends AbstractArrowEntity {
    private int ticksInAir;
    protected AbstractDartEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
        this.ticksInAir = 0;
    }

    public AbstractDartEntity(EntityType<? extends AbstractArrowEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
        this.ticksInAir = 0;
    }

    public AbstractDartEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.ticksInAir = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.ticksInAir == 500)
        {
            this.remove();
        }

        if (!this.onGround)
        {
            ++this.ticksInAir;
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);
        this.setNoGravity(false);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return AetherSoundEvents.ENTITY_PROJECTILE_SHOOT.get();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
