package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.*;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class SmartRegistryWriteOps<Format> extends MultiRegistryWriteOps<Format>
{
    protected HashCache directoryCache;

    public SmartRegistryWriteOps(String modId, DataGenerator generator, DynamicOps<Format> ops, Function<Format, String> fileWriter, RegistryAccess dynamicRegistries) {
        super(modId, generator, ops, fileWriter, dynamicRegistries);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    public final void run(HashCache cache) {
        this.directoryCache = cache;

        super.run(cache);
    }

    @Override
    protected <Resource> DataResult<Format> encode(Resource resource, Format dynamic, ResourceKey<? extends Registry<Resource>> registryKey, Codec<Resource> codec) {
        Optional<ResourceLocation> instanceKey = this.scanForRegistryAttachment(resource, registryKey);

        // Four registries checked... Let's see if we won a prize
        if (instanceKey.isPresent()) {
            if (this.modId.equals(instanceKey.get().getNamespace())) // This avoids generating anything that does not belong to the mod
                this.serialize(registryKey, instanceKey.get(), resource, codec);

            return ResourceLocation.CODEC.encode(instanceKey.get(), this.delegate, dynamic);
        }

        // Empty-handed. Inline the object instead.
        return codec.encode(resource, this, dynamic);
    }

    private final HashSet<Object> objectsSerializationCache = new HashSet<>();

    public <Resource> void serialize(ResourceKey<? extends Registry<Resource>> resourceType, ResourceLocation resourceLocation, Resource resource, Encoder<Resource> encoder) {
        if (!this.objectsSerializationCache.add(resource)) {
            return;
        }

        Optional<Format> output = this
                .withEncoder(encoder)
                .apply(resource)
                .setLifecycle(Lifecycle.experimental())
                .resultOrPartial(error -> Aether.LOGGER.error("Object [" + resourceType.getRegistryName() + "] " + resourceLocation + " not serialized within recursive serialization: " + error));

        if (output.isPresent()) {
            try {
                this.save(this.directoryCache, output.get(), makePath(this.generator.getOutputFolder(), resourceType, resourceLocation, this.delegate.toString()));
            } catch (IOException e) {
                Aether.LOGGER.error("Could not save resource `" + resourceLocation + "` (Resource Type `" + resourceType.location() + "`)", e);
            }
        }
    }

    /** VanillaCopy: IDataProvider.save, with tweaks to work generally with DynamicOps */
    private void save(HashCache cache, Format dynamic, Path filePath) throws IOException {
        String fileContents = this.fileWriter.apply(dynamic).replaceAll("(?<!\\r)\\n", "\n");
        String fileHash = SHA1.hashUnencodedChars(fileContents).toString();

        if (!Objects.equals(cache.getHash(filePath), fileHash) || !Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath);

            try {
                bufferedwriter.write(fileContents);
            } catch (Throwable throwable1) {
                if (bufferedwriter != null) {
                    try {
                        bufferedwriter.close();
                    } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                    }
                }

                throw throwable1;
            }

            if (bufferedwriter != null) {
                bufferedwriter.close();
            }
        }

        cache.putNew(filePath, fileHash);
    }

    private static Path makePath(Path path, ResourceKey<?> key, ResourceLocation resc, String fileExt) {
        return path.resolve("data").resolve(resc.getNamespace()).resolve(key.location().getPath()).resolve(resc.getPath() + "." + fileExt.toLowerCase(Locale.ROOT));
    }
}
