package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.recipebook.AltarRecipeGui;
import com.gildedgames.aether.inventory.container.AltarContainer;

import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AltarScreen extends AbstractFurnaceScreen<AltarContainer> {
	private static final ResourceLocation ALTAR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/altar.png");
	
	public AltarScreen(AltarContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, new AltarRecipeGui(), inventory, name, ALTAR_GUI_TEXTURES);
	}

}
