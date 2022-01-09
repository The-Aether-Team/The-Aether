package com.gildedgames.aether.common.inventory.provider;

import com.gildedgames.aether.common.inventory.container.AccessoriesContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AccessoriesProvider implements MenuProvider
{
    @Nonnull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.crafting");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new AccessoriesContainer(i, playerInventory);
    }
}
