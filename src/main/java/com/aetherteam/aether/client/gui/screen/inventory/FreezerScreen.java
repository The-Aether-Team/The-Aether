package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.FreezerRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.FreezerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FreezerScreen extends AbstractAetherFurnaceScreen<FreezerMenu> {
    private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/freezer.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE = new ResourceLocation(Aether.MODID, "menu/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_TEXTURE = new ResourceLocation(Aether.MODID, "menu/burn_progress");

    public FreezerScreen(FreezerMenu menu, Inventory inventory, Component title) {
        super(menu, new FreezerRecipeBookComponent(), inventory, title, FREEZER_GUI_TEXTURES, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}
