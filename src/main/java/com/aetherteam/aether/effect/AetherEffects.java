package com.aetherteam.aether.effect;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Aether.MODID);

    public static final Supplier<MobEffect> INEBRIATION = EFFECTS.register("inebriation", InebriationEffect::new);
    public static final Supplier<MobEffect> REMEDY = EFFECTS.register("remedy", RemedyEffect::new);
}
