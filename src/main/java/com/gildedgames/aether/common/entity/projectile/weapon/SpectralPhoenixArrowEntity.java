package com.gildedgames.aether.common.entity.projectile.weapon;

import com.gildedgames.aether.common.registry.AetherEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpectralPhoenixArrowEntity extends AbstractArrowEntity {
    private int duration = 200;

    public SpectralPhoenixArrowEntity(EntityType<? extends SpectralPhoenixArrowEntity> type, World world) {
        super(type, world);
    }

    public SpectralPhoenixArrowEntity(World worldIn, double x, double y, double z) {
        super(AetherEntityTypes.SPECTRAL_PHOENIX_ARROW.get(), x, y, z, worldIn);
    }

    public SpectralPhoenixArrowEntity(World worldIn, LivingEntity shooter) {
        super(AetherEntityTypes.SPECTRAL_PHOENIX_ARROW.get(), shooter, worldIn);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.spawnPotionParticles(1);
                }
            }
            else {
                this.spawnPotionParticles(2);
            }
        }
    }

    private void spawnPotionParticles(int particleCount) {
        for (int j = 0; j < particleCount; ++j) {
            this.level.addParticle(ParticleTypes.FLAME, this.getX() + (this.random.nextGaussian() / 5.0), this.getY() + (this.random.nextGaussian() / 5.0), this.getZ() + (this.random.nextGaussian() / 3.0), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if(compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }
    }


    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Duration", this.duration);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        EffectInstance effectinstance = new EffectInstance(Effects.GLOWING, this.duration, 0);
        entity.addEffect(effectinstance);
        if (!(entity instanceof EndermanEntity)) {
            entity.setSecondsOnFire(5);
            if (this.isOnFire()) {
                entity.setSecondsOnFire(10);
            }
        }
    }
}
