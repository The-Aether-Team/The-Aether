package com.gildedgames.aether.common.effect;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class InebriationEffect extends Effect
{
    private int effectDuration;

    private double rotationDirection, motionDirection;

    public InebriationEffect() {
        super(EffectType.HARMFUL, 5319035);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        // Check for if the entitytype is immune to inebriation or not.
            if (!Aether.INEBRIATIONIMMUNE.contains(entityLivingBaseIn.getType())) {
                if (this.effectDuration % 50 == 0) {
                    entityLivingBaseIn.hurt(new DamageSource("inebriation").bypassArmor(), 1.0F);
                }
            this.distractEntity(entityLivingBaseIn);
        }
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

        if (entityLivingBaseIn.level instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) entityLivingBaseIn.level;
            world.sendParticles(new ItemParticleData(ParticleTypes.ITEM, Items.RED_DYE.getDefaultInstance()),
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
