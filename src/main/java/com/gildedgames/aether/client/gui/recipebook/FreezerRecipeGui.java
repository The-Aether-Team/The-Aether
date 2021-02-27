package com.gildedgames.aether.client.gui.recipebook;

import java.util.Set;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.entity.tile.FreezerTileEntity;
import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FreezerRecipeGui extends AbstractRecipeBookGui {
    private static final ITextComponent field_243415_i = new TranslationTextComponent("gui.recipebook.toggleRecipes." + Aether.MODID + ".freezable");

    protected ITextComponent func_230479_g_() {
        return field_243415_i;
    }

    protected Set<Item> func_212958_h() {
        return FreezerTileEntity.getFreezingMap().keySet();
    }
}