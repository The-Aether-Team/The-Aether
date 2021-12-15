package com.gildedgames.aether.common.inventory.provider;

import com.gildedgames.aether.common.inventory.container.LoreBookContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LoreBookProvider implements INamedContainerProvider
{
    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.aether.book_of_lore");
    }

    @Nullable
    @Override
    public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
        return new LoreBookContainer(i, playerInventory);
    }
}
