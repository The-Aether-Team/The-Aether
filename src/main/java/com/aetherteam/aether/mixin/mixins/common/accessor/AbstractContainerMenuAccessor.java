package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerMenu.class)
public interface AbstractContainerMenuAccessor {
    @Accessor("lastSlots")
    NonNullList<ItemStack> aether$getLastSlots();

    @Accessor("remoteSlots")
    NonNullList<ItemStack> aether$getRemoteSlots();

    @Mutable
    @Accessor("containerId")
    void aether$setContainerId(int containerId);

    @Mutable
    @Accessor("menuType")
    void aether$setMenuType(MenuType<?> menuType);
}