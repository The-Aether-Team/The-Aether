package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AetherDataGenerator<T> {
    public DataProvider create(DataGenerator generator, ExistingFileHelper helper, DeferredRegister<T> registry, ResourceKey<Registry<T>> registryKey) {
        RegistryAccess registryAccess = RegistryAccess.builtinCopy();
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);
        Map<ResourceLocation, T> map = new HashMap<>();
        for (RegistryObject<T> object : registry.getEntries()) {
            map.put(object.getId(), object.get());
        }
        return JsonCodecProvider.forDatapackRegistry(generator, helper, Aether.MODID, registryOps, registryKey, map);
    }
}
