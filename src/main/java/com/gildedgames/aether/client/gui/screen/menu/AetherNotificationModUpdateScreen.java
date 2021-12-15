package com.gildedgames.aether.client.gui.screen.menu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.ForgeI18n;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.client.ClientModLoader;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.versions.forge.ForgeVersion;

public class AetherNotificationModUpdateScreen extends NotificationModUpdateScreen
{
	private static final ResourceLocation VERSION_CHECK_ICONS = new ResourceLocation(ForgeVersion.MOD_ID, "textures/gui/version_check_icons.png");
	private VersionChecker.Status showNotification = null;
	private boolean hasCheckedForUpdates = false;
	
	public AetherNotificationModUpdateScreen() {
		super(null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks) {
		if (this.showNotification != null && this.showNotification.shouldDraw() && FMLConfig.runVersionCheck() && Minecraft.getInstance().screen != null) {
			Minecraft.getInstance().getTextureManager().bind(VERSION_CHECK_ICONS);
			RenderSystem.color4f(1, 1, 1, 1);

			this.width = Minecraft.getInstance().screen.width;
			this.height = Minecraft.getInstance().screen.height;
			this.font = Minecraft.getInstance().font;
			int tModCount = ModList.get().size();
			String modLine = ForgeI18n.parseMessage("fml.menu.loadingmods", tModCount);

			blit(mStack, width - font.width(modLine) - 11, height - font.lineHeight - 11, this.showNotification.getSheetOffset() * 8, (this.showNotification.isAnimated() && ((System.currentTimeMillis() / 800 & 1) == 1)) ? 8 : 0, 8, 8, 64, 16);

		}
	}

	@Override
	public void init() {
		if (!this.hasCheckedForUpdates) {
			this.showNotification = ClientModLoader.checkForUpdates();
			this.hasCheckedForUpdates = true;
		}
	}
}
