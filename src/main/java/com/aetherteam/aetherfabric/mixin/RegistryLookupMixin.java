package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.RegistryLookupExtension;
import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HolderLookup.RegistryLookup.class)
public interface RegistryLookupMixin<T> extends RegistryLookupExtension<T> {
    @Mixin(HolderLookup.RegistryLookup.Delegate.class)
    interface DelegateMixin<T> extends RegistryLookupExtension<T> {
        @Shadow
        HolderLookup.RegistryLookup<T> parent();

        @Override
        default <T1> @Nullable T1 aetherFabric$getData(DataMapType<T, T1> type, ResourceKey<T> key) {
            return this.parent().aetherFabric$getData(type, key);
        }
    }
}
