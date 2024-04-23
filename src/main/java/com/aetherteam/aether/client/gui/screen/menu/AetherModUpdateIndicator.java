//package com.aetherteam.aether.client.gui.screen.menu; TODO: PORT
//
//import io.github.fabricators_of_create.porting_lib.util.ForgeI18n;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiGraphics;
//import net.minecraft.resources.ResourceLocation;
//
//import org.jetbrains.annotations.Nullable;
//
//public class AetherModUpdateIndicator extends TitleScreenModUpdateIndicator {
//	private static final ResourceLocation VERSION_CHECK_ICONS = new ResourceLocation(ForgeVersion.MOD_ID, "textures/gui/version_check_icons.png");
//	private final AetherTitleScreen screen;
//	@Nullable
//	private VersionChecker.Status showNotification = null;
//	private boolean hasCheckedForUpdates = false;
//
//	public AetherModUpdateIndicator(AetherTitleScreen screen) {
//		super(null);
//		this.screen = screen;
//	}
//
//	@Override
//	public void init() {
//		if (!this.hasCheckedForUpdates) {
//			this.showNotification = ClientModLoader.checkForUpdates();
//			this.hasCheckedForUpdates = true;
//		}
//	}
//
//	/**
//	 * Renders Forge's mod update indicator next to the "# mods loaded" text on the title screen.
//	 * @param graphics The rendering {@link GuiGraphics}.
//	 * @param mouseX The {@link Integer} for the mouse's x-position.
//	 * @param mouseY The {@link Integer} for the mouse's y-position.
//	 * @param partialTicks The {@link Float} for the game's partial ticks.
//	 */
//	@Override
//	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
//		if (this.showNotification != null && this.showNotification.shouldDraw() && !FMLConfig.getBoolConfigValue(FMLConfig.ConfigValue.VERSION_CHECK) && Minecraft.getInstance().screen != null) {
//			this.width = Minecraft.getInstance().screen.width;
//			this.height = Minecraft.getInstance().screen.height;
//			this.font = Minecraft.getInstance().font;
//			int modCount = ModList.get().size();
//			String modText = ForgeI18n.parseMessage("fml.menu.loadingmods", modCount);
//
//			int x = this.width - this.font.width(modText) - 11;
//			int y = this.height - this.font.lineHeight - 11;
//
//			if (!this.screen.isAlignedLeft()) {
//				x = this.font.width(modText) + 4;
//				y = this.height - this.font.lineHeight - 1;
//			}
//
//			graphics.blit(VERSION_CHECK_ICONS, x, y, this.showNotification.getSheetOffset() * 8, (this.showNotification.isAnimated() && ((System.currentTimeMillis() / 800 & 1) == 1)) ? 8 : 0, 8, 8, 64, 16);
//		}
//	}
//}
