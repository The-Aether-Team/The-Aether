package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.inventory.container.AltarContainer;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AltarScreen extends AetherFurnaceScreen<AltarContainer>
{
	private static final ResourceLocation ALTAR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/altar.png");
	
	public AltarScreen(AltarContainer container, PlayerInventory inventory, ITextComponent name) {
		super(container, inventory, name, ALTAR_GUI_TEXTURES);
	}
}
