package com.aetherteam.aether.entity.ai.attribute;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AetherAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Aether.MODID);

    public static final DeferredHolder<Attribute, Attribute> MOA_MAX_JUMPS =  ATTRIBUTES.register("moa_max_jumps", () -> new RangedAttribute("aether.attribute.name.moa_max_jumps", -1.0, -1.0, 1024.0).setSyncable(true));
}
