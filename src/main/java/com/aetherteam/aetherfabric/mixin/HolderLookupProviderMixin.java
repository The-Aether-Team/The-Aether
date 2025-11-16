package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.IHolderLookupProviderExtension;
import net.minecraft.core.HolderLookup;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HolderLookup.Provider.class)
public interface HolderLookupProviderMixin extends IHolderLookupProviderExtension {
}
