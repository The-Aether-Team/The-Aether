package com.aetherteam.aether.effect;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class AetherEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Aether.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> INEBRIATION = EFFECTS.register("inebriation", InebriationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> REMEDY = EFFECTS.register("remedy", RemedyEffect::new);
}
