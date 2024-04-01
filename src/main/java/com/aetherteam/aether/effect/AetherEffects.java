package com.aetherteam.aether.effect;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Aether.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> INEBRIATION = EFFECTS.register("inebriation", InebriationEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> REMEDY = EFFECTS.register("remedy", RemedyEffect::new);
}
