package com.gildedgames.aether.common.effect;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class InebriationEffect extends MobEffect
{
    private int effectDuration;

    private double rotationDirection, motionDirection;

    public InebriationEffect() {
        super(MobEffectCategory.HARMFUL, 5319035);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (this.effectDuration % 50 == 0) {
            entityLivingBaseIn.hurt(new DamageSource("inebriation").bypassArmor(), 1.0F);
        }
        this.distractEntity(entityLivingBaseIn);
    }

    private void distractEntity(LivingEntity entityLivingBaseIn) {
        double gaussian = entityLivingBaseIn.level.random.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motionDirection = 0.2D * newMotD + (0.8D) * this.motionDirection;

        entityLivingBaseIn.setDeltaMovement(entityLivingBaseIn.getDeltaMovement().add(this.motionDirection, 0, this.motionDirection));
        this.rotationDirection = 0.125D * newRotD + (1.0D - 0.125D) * this.rotationDirection;

        entityLivingBaseIn.yRot = (float)((double)entityLivingBaseIn.yRot + rotationDirection);
        entityLivingBaseIn.xRot = (float)((double)entityLivingBaseIn.xRot + rotationDirection);

        if (entityLivingBaseIn.level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) entityLivingBaseIn.level;
            world.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.RED_DYE.getDefaultInstance()),
                    entityLivingBaseIn.getX(),
                    entityLivingBaseIn.getY() + entityLivingBaseIn.getBbHeight() * 0.8,
                    entityLivingBaseIn.getZ(),
                    1, 0.0, 0.0, 0.0, 0.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        this.effectDuration = duration;
        return true;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> curatives = new ArrayList<>();
        curatives.add(new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()));
        curatives.add(new ItemStack(AetherItems.WHITE_APPLE.get()));
        return curatives;
    }
}
