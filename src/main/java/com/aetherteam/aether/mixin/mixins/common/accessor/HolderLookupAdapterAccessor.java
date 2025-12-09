package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.core.HolderLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net/minecraft/resources/RegistryOps$HolderLookupAdapter")
public interface HolderLookupAdapterAccessor {
    @Accessor("lookupProvider")
    HolderLookup.Provider nitrogen_fabric$lookupProvider();
}
