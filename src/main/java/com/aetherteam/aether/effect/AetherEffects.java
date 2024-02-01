package com.aetherteam.aether.effect;

import com.aetherteam.aether.Aether;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class AetherEffects {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Aether.MODID);

	public static final RegistryObject<MobEffect> INEBRIATION = EFFECTS.register("inebriation", InebriationEffect::new);
	public static final RegistryObject<MobEffect> REMEDY = EFFECTS.register("remedy", RemedyEffect::new);
}
