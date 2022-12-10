package com.gildedgames.aether.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraftforge.resource.DelegatingPackResources;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class CombinedResourcePack extends DelegatingPackResources {
    private final Path source;

    public CombinedResourcePack(String id, PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
        super(id, false, packInfo, packs);
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

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        return this.getRootResource(getPathFromLocation(location.getPath().startsWith("lang/") ? PackType.CLIENT_RESOURCES : type, location));
    }

    private static String getPathFromLocation(PackType pType, ResourceLocation pLocation) {
        return String.format(Locale.ROOT, "%s/%s/%s", pType.getDirectory(), pLocation.getNamespace(), pLocation.getPath());
    }


//    @Nonnull
//    @Override
//    protected InputStream getResource(@Nonnull String name) throws IOException {
//        final Path path = resolve(name);
//        if(!Files.exists(path))
//            throw new FileNotFoundException("Can't find resource " + name + " at " + getSource());
//        return Files.newInputStream(path, StandardOpenOption.READ);
//    }

    @Override
    public String toString() {
        return String.format("%s: %s", getClass().getName(), getSource());
    }
}
