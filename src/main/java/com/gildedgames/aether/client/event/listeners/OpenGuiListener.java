package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.MainMenu.AetherMainMenuScreen;
import com.gildedgames.aether.client.gui.MainMenu.AetherMainMenuScreen.Mode;

import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OpenGuiListener {
	
	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent event) {
		// TODO Replace true with useAetherMenuConfig
		if(true && event.getGui() instanceof  MainMenuScreen &&!(event.getGui() instanceof AetherMainMenuScreen))
			event.setGui(new AetherMainMenuScreen(Mode.AETHER));
		
	}

}
