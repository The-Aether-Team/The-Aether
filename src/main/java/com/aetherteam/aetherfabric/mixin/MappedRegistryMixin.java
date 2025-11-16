package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.FullDataMapAccess;
import com.aetherteam.aetherfabric.pond.RegistryLookupExtension;
import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import net.minecraft.core.MappedRegistry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> implements FullDataMapAccess<T>{
    @Unique
    private final Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps = new IdentityHashMap<>();

    @Override
    public void setDataMaps(Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> dataMaps) {
        this.dataMaps.clear();
        this.dataMaps.putAll(dataMaps);
    }

    @Override
    public Map<DataMapType<T, ?>, Map<ResourceKey<T>, ?>> getDataMaps() {
        return Collections.unmodifiableMap(this.dataMaps);
    }

    @Mixin(targets = "net/minecraft/core/MappedRegistry$1")
    public abstract static class InnerClass<T> implements RegistryLookupExtension<T> {
        @Final
        @Shadow(remap = false)
        MappedRegistry<T> field_36468;

        @Override
        public <T1> @Nullable T1 aetherFabric$getData(DataMapType<T, T1> type, ResourceKey<T> key) {
            return field_36468.aetherFabric$getData(type, key);
        }
    }
}
