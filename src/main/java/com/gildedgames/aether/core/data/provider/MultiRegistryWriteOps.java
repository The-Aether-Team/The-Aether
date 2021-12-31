package com.gildedgames.aether.core.data.provider;

import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public abstract class MultiRegistryWriteOps<Format> extends RegistryWriteOps<Format> implements DataProvider
{
    protected final String modId;
    protected final DataGenerator generator;
    protected final Function<Format, String> fileWriter;
    private final RegistryAccess dynamicRegistries;

    public abstract void generate(RegistryAccess dynamicRegistry);

    @Override
    public void run(HashCache cache) {
        this.generate(this.dynamicRegistries);
    }

    public MultiRegistryWriteOps(String modId, DataGenerator generator, DynamicOps<Format> ops, Function<Format, String> fileWriter, RegistryAccess dynamicRegistries) {
        super(ops, dynamicRegistries);

        this.modId = modId;
        this.dynamicRegistries = dynamicRegistries;
        this.generator = generator;
        this.fileWriter = fileWriter;
    }

    protected <Resource> Optional<ResourceLocation> scanForRegistryAttachment(Resource resource, ResourceKey<? extends Registry<Resource>> registryKey) {
        Optional<ResourceLocation> instanceKey = Optional.empty();

        // Ask the object itself if it has a key first
        if (resource instanceof IForgeRegistryEntry) {
            instanceKey = Optional.ofNullable(((IForgeRegistryEntry<?>) resource).getRegistryName());
        }

        // Check "Local" Registry
        if (instanceKey.isEmpty()) {
            try {
                Registry<Resource> dynRegistry = this.dynamicRegistries.registryOrThrow(registryKey);

                //noinspection ConstantConditions
                instanceKey = dynRegistry != null ? dynRegistry.getResourceKey(resource).map(ResourceKey::location) : Optional.empty();
            } catch (Throwable t) {
                // Registry not supported, skip
            }
        }

        // Check Vanilla Worldgen Registries
        if (instanceKey.isEmpty()) {
            Registry<Resource> registry = getFromVanillaRegistryIllegally(BuiltinRegistries.REGISTRY, registryKey);

            if (registry != null) {
                instanceKey = registry.getResourceKey(resource).map(ResourceKey::location);
            }
        }

        // Check Global Vanilla Registries
        if (instanceKey.isEmpty()) {
            Registry<Resource> registry = getFromVanillaRegistryIllegally(Registry.REGISTRY, registryKey);

            if (registry != null) {
                instanceKey = registry.getResourceKey(resource).map(ResourceKey::location);
            }
        }

        // Check Forge Registries
        if (instanceKey.isEmpty()) {
            instanceKey = getFromForgeRegistryIllegally(registryKey, resource);
        }

        return instanceKey;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "SameParameterValue"})
    @Nullable
    protected static <T> T getFromVanillaRegistryIllegally(Registry registry, ResourceKey<T> key) {
        return (T) registry.get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected static <Resource> Optional<ResourceLocation> getFromForgeRegistryIllegally(ResourceKey<? extends Registry<Resource>> registryKey, Resource resource) {
        if (resource instanceof IForgeRegistryEntry<?> entry) {
            ResourceLocation location = entry.getRegistryName();

            if (location != null) {
                return Optional.of(location);
            }

            // This is safe because we've tested IForgeRegistry, but the type-checker is incapable of recognizing it as such
            IForgeRegistry forgeRegistry = RegistryManager.ACTIVE.getRegistry(registryKey.location());
            return Optional.ofNullable(forgeRegistry.getKey(entry));
        }

        return Optional.empty();
    }
}
