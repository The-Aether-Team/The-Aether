package com.gildedgames.aether.common.block.natural;

import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

import java.util.function.Supplier;

public class AetherFlowerBlock extends FlowerBlock
{
    private final Supplier<Effect> suspiciousStewEffect;
    private final int effectDuration;

    public AetherFlowerBlock(Supplier<Effect> effect, int effectDuration, Properties properties) {
        super(Effects.POISON, effectDuration, properties);
        this.suspiciousStewEffect = effect;
        this.effectDuration = effectDuration;
    }

    @Override
    public Effect getSuspiciousStewEffect() {
        return this.suspiciousStewEffect.get();
    }

    @Override
    public int getEffectDuration() {
        return this.getSuspiciousStewEffect().isInstantenous() ? this.effectDuration : this.effectDuration * 20;
    }
}
