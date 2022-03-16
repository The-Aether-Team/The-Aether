package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.info.WorldgenRegistryDumpReport;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

public abstract class WorldProvider extends WorldgenRegistryDumpReport {
    public abstract <E> boolean shouldSerialize(ResourceKey<E> resourceKey, E resource);

    // Below: [VanillaCopy] WorldgenRegistryDumpReport - All methods made non-static protected. Finer modifications documented
    protected static final Logger LOGGER = Aether.LOGGER;
    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    protected final DataGenerator generator;

    public WorldProvider(DataGenerator generator) {
        super(generator);
        // ^^^ Addition - Supercall
        this.generator = generator;
    }

    @Override
    public void run(HashCache p_194682_) {
        Path path = this.generator.getOutputFolder();
        RegistryAccess registryaccess = RegistryAccess.BUILTIN.get();
        Registry<LevelStem> registry = DimensionType.defaultDimensions(registryaccess, 0L, false);
        ChunkGenerator chunkgenerator = WorldGenSettings.makeDefaultOverworld(registryaccess, 0L, false);
        Registry<LevelStem> registry1 = WorldGenSettings.withOverworld(registryaccess.ownedRegistryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), registry, chunkgenerator);
        DynamicOps<JsonElement> dynamicops = RegistryOps.create(JsonOps.INSTANCE, registryaccess);
        RegistryAccess.knownRegistries().forEach(p_194713_ -> this.dumpRegistryCap(p_194682_, path, registryaccess, dynamicops, p_194713_));
        this.dumpRegistry(path, p_194682_, dynamicops, Registry.LEVEL_STEM_REGISTRY, registry1, LevelStem.CODEC);
    }

    protected <T> void dumpRegistryCap(HashCache p_194684_, Path p_194685_, RegistryAccess p_194686_, DynamicOps<JsonElement> p_194687_, RegistryAccess.RegistryData<T> p_194688_) {
        this.dumpRegistry(p_194685_, p_194684_, p_194687_, p_194688_.key(), p_194686_.ownedRegistryOrThrow(p_194688_.key()), p_194688_.codec());
    }

    protected <E, T extends Registry<E>> void dumpRegistry(Path p_194698_, HashCache p_194699_, DynamicOps<JsonElement> p_194700_, ResourceKey<? extends T> p_194701_, T p_194702_, Encoder<E> p_194703_) {
        for (Map.Entry<ResourceKey<E>, E> entry : p_194702_.entrySet()) {
            if (!this.shouldSerialize(entry.getKey(), entry.getValue())) continue;
            // ^^^ Addition - Determine if an object is acceptable for serialization
            Path path = this.createPath(p_194698_, p_194701_.location(), entry.getKey().location());
            this.dumpValue(path, p_194699_, p_194700_, p_194703_, entry.getValue());
        }
    }

    protected <E> void dumpValue(Path p_194692_, HashCache p_194693_, DynamicOps<JsonElement> p_194694_, Encoder<E> p_194695_, E p_194696_) {
        try {
            Optional<JsonElement> optional = p_194695_.encodeStart(p_194694_, p_194696_).resultOrPartial((p_206405_) -> {
                LOGGER.error("Couldn't serialize element {}: {}", p_194692_, p_206405_);
            });
            if (optional.isPresent()) {
                DataProvider.save(GSON, p_194693_, optional.get(), p_194692_);
            }
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save element {}", p_194692_, ioexception);
        }

    }

    protected Path createPath(Path p_194705_, ResourceLocation p_194706_, ResourceLocation p_194707_) {
        return this.resolveTopPath(p_194705_).resolve(p_194707_.getNamespace()).resolve(p_194706_.getPath()).resolve(p_194707_.getPath() + ".json");
    }

    protected Path resolveTopPath(Path p_194690_) {
        return p_194690_.resolve("reports").resolve("worldgen");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
        return "Worldgen";
    }
}
