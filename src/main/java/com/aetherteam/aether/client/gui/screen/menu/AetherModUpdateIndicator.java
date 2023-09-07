package com.aetherteam.aether.client.gui.screen.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.minecraftforge.client.loading.ClientModLoader;
import net.minecraftforge.common.ForgeI18n;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.versions.forge.ForgeVersion;

import javax.annotation.Nullable;

public class AetherModUpdateIndicator extends TitleScreenModUpdateIndicator {
	private static final ResourceLocation VERSION_CHECK_ICONS = new ResourceLocation(ForgeVersion.MOD_ID, "textures/gui/version_check_icons.png");
	private final AetherTitleScreen screen;
	@Nullable
	private VersionChecker.Status showNotification = null;
	private boolean hasCheckedForUpdates = false;
	
	public AetherModUpdateIndicator(AetherTitleScreen screen) {
		super(null);
		this.screen = screen;
	}

	@Override
	public void init() {
		if (!this.hasCheckedForUpdates) {
			this.showNotification = ClientModLoader.checkForUpdates();
			this.hasCheckedForUpdates = true;
		}
	}

	/**
	 * Renders Forge's mod update indicator next to the "# mods loaded" text on the title screen.
	 * @param poseStack The rendering {@link PoseStack}.
	 * @param mouseX The {@link Integer} for the mouse's x-position.
	 * @param mouseY The {@link Integer} for the mouse's y-position.
	 * @param partialTicks The {@link Float} for the game's partial ticks.
	 */
	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		if (this.showNotification != null && this.showNotification.shouldDraw() && FMLConfig.runVersionCheck() && Minecraft.getInstance().screen != null) {
			RenderSystem.setShaderTexture(0, VERSION_CHECK_ICONS);

			this.width = Minecraft.getInstance().screen.width;
			this.height = Minecraft.getInstance().screen.height;
			this.font = Minecraft.getInstance().font;
			int modCount = ModList.get().size();
			String modText = ForgeI18n.parseMessage("fml.menu.loadingmods", modCount);

			int x = this.width - this.font.width(modText) - 11;
			int y = this.height - this.font.lineHeight - 11;

			if (!this.screen.isAlignedLeft()) {
				x = this.font.width(modText) + 4;
				y = this.height - this.font.lineHeight - 1;
			}

			GuiComponent.blit(poseStack, x, y, this.showNotification.getSheetOffset() * 8, (this.showNotification.isAnimated() && ((System.currentTimeMillis() / 800 & 1) == 1)) ? 8 : 0, 8, 8, 64, 16);
		}
	}
}
