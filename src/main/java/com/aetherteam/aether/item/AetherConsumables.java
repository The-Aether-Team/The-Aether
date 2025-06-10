package com.aetherteam.aether.item;

import com.aetherteam.aether.effect.AetherEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class AetherConsumables {
    public static final Consumable HEALING_STONE = Consumables.defaultDrink().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 610, 0))).build();
    public static final Consumable POISON_BUCKET = Consumables.defaultDrink().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(AetherEffects.INEBRIATION, 500, 0, false, false, true))).build();
    public static final Consumable REMEDY_BUCKET = Consumables.defaultDrink().onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(AetherEffects.REMEDY, 200, 0, false, false, true))).build();
    public static final Consumable FAST_FOOD = Consumables.defaultFood().consumeSeconds(0.8F).build();
}
