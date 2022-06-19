package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;

import java.nio.file.Path;

// Register this class to the data generator to generate world generation pieces
public class AetherWorldData extends AetherWorldProvider {
    public AetherWorldData(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    protected void dumpRegistries(RegistryAccess registryAccess, CachedOutput cache, Path path, DynamicOps<JsonElement> dynamicOps) {
//        this.registerDimensionType(cache, path, dynamicOps);
//        this.registerLevelStem(registryAccess, cache, path, dynamicOps);
    }

    @Override
    public <E> boolean shouldSerialize(ResourceKey<E> resourceKey, E resource) {
        return Aether.MODID.equals(resourceKey.location().getNamespace());
    }

    @Override
    protected Path resolveTopPath(Path path) {
        return path.resolve("data");
    }
}