package com.aetherteam.aether.entity.ai.attribute;


import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AetherAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Aether.MODID);

    public static final RegistryObject<Attribute> MOA_MAX_JUMPS =  ATTRIBUTES.register("moa_max_jumps", () -> new RangedAttribute("aether.attribute.name.moa_max_jumps", -1.0, -1.0, 1024.0).setSyncable(true));
}
