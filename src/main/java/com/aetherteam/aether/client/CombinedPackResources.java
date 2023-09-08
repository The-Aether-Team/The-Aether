package com.aetherteam.aether.client;

import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.resource.DelegatingPackResources;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Used to create built-in resource packs by merging two separate resource folders. Used for the legacy resource packs.
 */
public class CombinedPackResources extends DelegatingPackResources {
    private final Path source;

    public CombinedPackResources(String id, String name, PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
        super(id, name, packInfo, packs);
        this.source = sourcePack;
    }

    public Path getSource() {
        return this.source;
    }

    protected Path resolve(String... paths) {
        Path path = getSource();
        for (String name : paths)
            path = path.resolve(name);
        return path;
    }

    @Override
    public InputStream getRootResource(String pFileName) throws IOException {
        if (!pFileName.contains("/") && !pFileName.contains("\\")) {
            return this.getResource(pFileName);
        } else {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
    }

    @Override
    protected boolean hasResource(@Nonnull String name) {
        final Path path = resolve(name);
        return Files.exists(path);
    }

    @Nonnull
    @Override
    protected InputStream getResource(@Nonnull String name) throws IOException {
        final Path path = resolve(name);
        if(!Files.exists(path))
            throw new FileNotFoundException("Can't find resource " + name + " at " + getSource());
        return Files.newInputStream(path, StandardOpenOption.READ);
    }

    @Nonnull
    @Override
    public String toString() {
        return String.format("%s: %s", getClass().getName(), getSource());
    }
}
