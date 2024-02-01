package com.aetherteam.aether.client;

import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.neoforge.resource.DelegatingPackResources;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Used to create built-in resource packs by merging two separate resource folders. Used for the legacy resource packs.
 */
public class CombinedPackResources extends DelegatingPackResources {
    private final Path source;

    public CombinedPackResources(String id, PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
        super(id, true, packInfo, packs);
        this.source = sourcePack;
    }

    public Path getSource() {
        return this.source;
    }

    /**
     * [CODE COPY] - {@link net.neoforged.neoforge.resource.PathPackResources#resolve(String...)}.
     */
    protected Path resolve(String... paths) {
        Path path = getSource();
        for (String name : paths) {
            path = path.resolve(name);
        }
        return path;
    }

    /**
     * [CODE COPY] - {@link net.neoforged.neoforge.resource.PathPackResources#getRootResource(String...)}.
     */
    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... paths) {
        final Path path = resolve(paths);
        if (!Files.exists(path)) {
            return null;
        }
        return IoSupplier.create(path);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getClass().getName(), getSource());
    }
}
