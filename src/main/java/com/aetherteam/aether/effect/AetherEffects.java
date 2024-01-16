package com.aetherteam.aether.effect;

import com.aetherteam.aether.Aether;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class AetherEffects {
	public static final LazyRegistrar<MobEffect> EFFECTS = LazyRegistrar.create(Registries.MOB_EFFECT, Aether.MODID);

	public static final RegistryObject<MobEffect> INEBRIATION = EFFECTS.register("inebriation", InebriationEffect::new);
	public static final RegistryObject<MobEffect> REMEDY = EFFECTS.register("remedy", RemedyEffect::new);
}
