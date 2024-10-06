package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.AltarRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.AltarMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AltarScreen extends AbstractAetherFurnaceScreen<AltarMenu> {
    private static final ResourceLocation ALTAR_GUI_TEXTURES = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/altar.png");
    private static final ResourceLocation LIT_PROGRESS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "menu/lit_progress");
    private static final ResourceLocation BURN_PROGRESS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "menu/burn_progress");

    public AltarScreen(AltarMenu menu, Inventory inventory, Component title) {
        super(menu, new AltarRecipeBookComponent(), inventory, title, ALTAR_GUI_TEXTURES, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}
