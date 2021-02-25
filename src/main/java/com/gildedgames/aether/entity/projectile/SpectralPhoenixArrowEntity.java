package com.gildedgames.aether.entity.projectile;

import com.gildedgames.aether.registry.AetherEntityTypes;
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            if (this.inGround) {
                if (this.timeInGround % 5 == 0) {
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
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX() + (this.rand.nextGaussian() / 5D), this.getPosY() + (this.rand.nextGaussian() / 5D), this.getPosZ() + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if(compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }
    }


    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Duration", this.duration);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        EffectInstance effectinstance = new EffectInstance(Effects.GLOWING, this.duration, 0);
        entity.addPotionEffect(effectinstance);
        if (!(entity instanceof EndermanEntity)) {
            entity.setFire(5);
            if (this.isBurning()) {
                entity.setFire(10);
            }
        }
    }
}
