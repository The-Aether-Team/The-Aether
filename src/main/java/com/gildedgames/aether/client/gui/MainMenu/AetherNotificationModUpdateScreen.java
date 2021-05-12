package com.gildedgames.aether.client.gui.MainMenu;

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

public class AetherNotificationModUpdateScreen extends NotificationModUpdateScreen{

	private static final ResourceLocation VERSION_CHECK_ICONS = new ResourceLocation(ForgeVersion.MOD_ID, "textures/gui/version_check_icons.png");
	private VersionChecker.Status status = null;
    private boolean hasCheckedForUpdates = false;
	
	public AetherNotificationModUpdateScreen() {
		super(null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks) {
		if (status == null || !status.shouldDraw() || !FMLConfig.runVersionCheck())
        {
            return;
        }

        Minecraft.getInstance().getTextureManager().bind(VERSION_CHECK_ICONS);
        RenderSystem.color4f(1, 1, 1, 1);

        width = Minecraft.getInstance().screen.width;
        height = Minecraft.getInstance().screen.height;
        font = Minecraft.getInstance().font;
        int tModCount = ModList.get().size();
        String modLine = ForgeI18n.parseMessage("fml.menu.loadingmods", tModCount);
        
        blit(mStack, width - font.width(modLine) - 11, height - font.lineHeight - 11, this.status.getSheetOffset() * 8, (this.status.isAnimated() && ((System.currentTimeMillis() / 800 & 1) == 1)) ? 8 : 0, 8, 8, 64, 16);
	}
	
	@Override
	public void init() {
		if (!hasCheckedForUpdates)
	    {
		    this.status = ClientModLoader.checkForUpdates();
			hasCheckedForUpdates = true;
	    }
	}
}
