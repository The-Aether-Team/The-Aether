package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.resources.RegistryOps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RegistryOps.class)
public interface RegistryOpsAccessor {

    @Accessor("lookupProvider")
    RegistryOps.RegistryInfoLookup nitrogen_fabric$lookup();
}
