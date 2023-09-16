package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

public class LorePageButton extends Button {
    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/lore_widgets.png");

    public LorePageButton(Builder builder) {
        super(builder.createNarration(DEFAULT_NARRATION));
        this.active = false;
    }

    public void onPress() {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int textureY = this.getTextureY();
        guiGraphics.blit(BUTTON_TEXTURES, this.getX(), this.getY(), 0, textureY, this.width / 2, this.height);
        guiGraphics.blit(BUTTON_TEXTURES, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, textureY, this.width / 2, this.height);
        int color = this.getFGColor();
        guiGraphics.drawCenteredString(fontRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color | 255 << 24);
    }

    /**
     * [CODE COPY] - {@link AbstractButton#getTextureY()}
     */
    private int getTextureY() {
        int i = 1;
        if (!this.isActive()) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }
        return 46 + i * 20;
    }
}
