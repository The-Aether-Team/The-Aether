package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class RefreshButton extends Button {
    public static final WidgetSprites REFRESH_WIDGET = new WidgetSprites(new ResourceLocation(Aether.MODID, "skins/refresh_button"), new ResourceLocation(Aether.MODID, "skins/refresh_button_highlighted"), new ResourceLocation(Aether.MODID, "skins/refresh_button_highlighted"));

    public static final int reboundMax = 1200;
    public static int reboundTimer = 0;

    public RefreshButton(Builder builder) {
        super(builder);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation location = REFRESH_WIDGET.get(this.isActive(), this.isHoveredOrFocused());
        guiGraphics.blitSprite(location, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        guiGraphics.drawCenteredString(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }

    @Override
    public boolean isActive() {
        return super.isActive() && reboundTimer <= 0;
    }
}
