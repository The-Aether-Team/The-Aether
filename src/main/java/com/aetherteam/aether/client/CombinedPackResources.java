package com.aetherteam.aether.client;

import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraftforge.resource.DelegatingPackResources;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CombinedPackResources extends DelegatingPackResources {
    private final Path source;

    public CombinedPackResources(String id, PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
        super(id, true, packInfo, packs);
        this.source = sourcePack;
    }

    public Path getSource() {
        return this.source;
    }

    protected Path resolve(String... paths) {
        Path path = getSource();
        for (String name : paths) {
            path = path.resolve(name);
        }
        return path;
    }

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
