package com.aetherteam.aether.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.FileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Used to create built-in resource packs by merging two separate resource folders. Used for the legacy resource packs.
 */
public class CombinedPackResources extends AbstractPackResources {

    private final PackMetadataSection packInfo;
    private final List<PackResources> packs;
    private final Map<String, List<PackResources>> assets;
    private final Map<String, List<PackResources>> data;
    private final Path source;

    public CombinedPackResources(String id, PackMetadataSection packInfo, List<? extends PackResources> packs, Path sourcePack) {
        super(id, true);
        this.packInfo = packInfo;
        this.packs = ImmutableList.copyOf(packs);
        this.assets = this.buildNamespaceMap(PackType.CLIENT_RESOURCES, packs);
        this.data = this.buildNamespaceMap(PackType.SERVER_DATA, packs);
        this.source = sourcePack;
    }

    private Map<String, List<PackResources>> buildNamespaceMap(PackType type, List<? extends PackResources> packList) {
        Map<String, List<PackResources>> map = new HashMap<>();
        for (PackResources pack : packList) {
            for (String namespace : pack.getNamespaces(type)) {
                map.computeIfAbsent(namespace, k -> new ArrayList<>()).add(pack);
            }
        }
        map.replaceAll((k, list) -> ImmutableList.copyOf(list));
        return ImmutableMap.copyOf(map);
    }

    public Path getSource() {
        return this.source;
    }

    /**
     * [CODE COPY] - {@link net.minecraft.server.packs.PathPackResources#getRootResource(String...)}.
     */
    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... paths) {
        FileUtil.validatePath(paths);
        Path path = FileUtil.resolvePath(this.getSource(), List.of(paths));
        return Files.exists(path) ? IoSupplier.create(path) : null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) {
        return deserializer.getMetadataSectionName().equals("pack") ? (T) this.packInfo : null;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        for (PackResources pack : this.getCandidatePacks(type, location)) {
            IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
            if (ioSupplier != null) {
                return ioSupplier;
            }
        }

        return null;
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput output) {
        for (PackResources delegate : this.packs) {
            delegate.listResources(type, namespace, path, output);
        }
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.CLIENT_RESOURCES ? this.assets.keySet() : this.data.keySet();
    }

    @Override
    public void close() {
        for (PackResources pack : this.packs) {
            pack.close();
        }
    }

    @Nullable
    public Collection<PackResources> getChildren() {
        return this.packs;
    }

    private List<PackResources> getCandidatePacks(PackType type, ResourceLocation location) {
        Map<String, List<PackResources>> map = type == PackType.CLIENT_RESOURCES ? this.assets : this.data;
        List<PackResources> packsWithNamespace = map.get(location.getNamespace());
        return packsWithNamespace == null ? Collections.emptyList() : packsWithNamespace;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.getClass().getName(), this.getSource());
    }

    public record CombinedResourcesSupplier(PackMetadataSection packInfo, List<? extends PackResources> packs,
                                            Path sourcePack) implements Pack.ResourcesSupplier {
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
                    list1.add(new CombinedPackResources(id, this.packInfo, this.packs, path));
                }

                return new CompositePackResources(packresources, list1);
            }
        }
    }
}
