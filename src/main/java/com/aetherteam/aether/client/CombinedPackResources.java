package com.aetherteam.aether.client;

import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.neoforge.resource.DelegatingPackResources;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    public static class CombinedResourcesSupplier implements Pack.ResourcesSupplier {
        private final PackMetadataSection packInfo;
        private final List<? extends PackResources> packs;
        private final Path sourcePack;

        public CombinedResourcesSupplier(PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
            this.packInfo = packInfo;
            this.packs = packs;
            this.sourcePack = sourcePack;
        }

        @Override
        public PackResources openPrimary(String pId) {
            return new CombinedPackResources(pId, this.packInfo, this.packs, this.sourcePack);
        }

        @Override
        public PackResources openFull(String id, Pack.Info info) {
            PackResources packresources = this.openPrimary(id);
            List<String> list = info.overlays();
            if (list.isEmpty()) {
                return packresources;
            } else {
                List<PackResources> list1 = new ArrayList<>(list.size());

                for (String s : list) {
                    Path path = this.sourcePack.resolve(s);
                    list1.add(new CombinedPackResources(id,this.packInfo, this.packs, path));
                }

                return new CompositePackResources(packresources, list1);
            }
        }
    }
}
