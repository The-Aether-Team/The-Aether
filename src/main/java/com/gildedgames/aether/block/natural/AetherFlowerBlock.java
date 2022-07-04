package com.gildedgames.aether.block.natural;

import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AetherFlowerBlock extends FlowerBlock
{
    private final Supplier<MobEffect> suspiciousStewEffect;
    private final int effectDuration;

    public AetherFlowerBlock(Supplier<MobEffect> effect, int effectDuration, Properties properties) {
        super(MobEffects.POISON, effectDuration, properties);
        this.suspiciousStewEffect = effect;
        this.effectDuration = effectDuration;
    }

    @Override
    public MobEffect getSuspiciousStewEffect() {
        return this.suspiciousStewEffect.get();
    }

    @Override
    public int getEffectDuration() {
        return this.getSuspiciousStewEffect().isInstantenous() ? this.effectDuration : this.effectDuration * 20;
    }
}
