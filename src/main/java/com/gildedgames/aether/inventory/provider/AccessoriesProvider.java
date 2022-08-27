package com.gildedgames.aether.inventory.provider;

import com.gildedgames.aether.inventory.menu.AccessoriesMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AccessoriesProvider implements MenuProvider
{
    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.crafting");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new AccessoriesMenu(i, playerInventory);
    }
}
