package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.IHolderExtension;
import com.aetherteam.aetherfabric.pond.IWithData;
import com.aetherteam.aetherfabric.registries.datamaps.DataMapType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.class)
public interface HolderMixin<T> extends IHolderExtension<T>, IWithData<T> {
    @Mixin(Holder.Reference.class)
    abstract class HolderReferenceMixin<T> implements IHolderExtension<T>, IWithData<T>  {
        @Shadow
        @Nullable
        private ResourceKey<T> key;

        @Shadow
        @Final
        private HolderOwner<T> owner;

        @Override
        public HolderLookup.@Nullable RegistryLookup<T> aetherFabric$unwrapLookup() {
            return this.owner instanceof HolderLookup.RegistryLookup<T> rl ? rl : null;
        }

        @Override
        @Nullable
        public ResourceKey<T> aetherFabric$getKey() {
            return this.key;
        }

        @Override
        public <T1> @Nullable T1 aetherFabric$getData(DataMapType<T, T1> type) {
            if (owner instanceof HolderLookup.RegistryLookup<T> lookup) {
                return lookup.aetherFabric$getData(type, key);
            }
            return null;
        }
    }
}
