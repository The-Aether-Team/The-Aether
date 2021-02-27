package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.recipebook.FreezerRecipeGui;
import com.gildedgames.aether.inventory.container.FreezerContainer;

import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FreezerScreen extends AbstractFurnaceScreen<FreezerContainer>
{
	private static final ResourceLocation FREEZER_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/freezer.png");
	
	public FreezerScreen(FreezerContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, new FreezerRecipeGui(), inventory, name, FREEZER_GUI_TEXTURES);
	}
}
