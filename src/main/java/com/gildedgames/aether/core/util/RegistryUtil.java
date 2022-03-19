package com.gildedgames.aether.core.util;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegistryUtil {
    public static  <T> Holder<T> register(Registry<T> registry, String name, T object) {
        ResourceLocation rl = new ResourceLocation(Aether.MODID, name);
        ResourceKey<T> key = ResourceKey.create(registry.key(), rl);

        Holder.Reference<T> ret = Holder.Reference.createStandAlone(registry, key);
        ret.bind(key, object);
        return ret;
    }

    private RegistryUtil() {
    }
}
