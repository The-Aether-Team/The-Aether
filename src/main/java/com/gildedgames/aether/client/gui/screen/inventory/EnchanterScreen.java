package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.recipebook.EnchanterRecipeGui;
import com.gildedgames.aether.inventory.container.EnchanterContainer;

import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchanterScreen extends AbstractFurnaceScreen<EnchanterContainer> {
	private static final ResourceLocation ENCHANTER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/enchanter.png");
	
	public EnchanterScreen(EnchanterContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, new EnchanterRecipeGui(), inventory, name, ENCHANTER_GUI_TEXTURES);
	}

}
