package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.effect.InebriationEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AetherPotionEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Aether.MODID);

    public static final RegistryObject<Effect> INEBRIATION = EFFECTS.register("inebriation", () -> new InebriationEffect(EffectType.HARMFUL, 0x51297B));
}
