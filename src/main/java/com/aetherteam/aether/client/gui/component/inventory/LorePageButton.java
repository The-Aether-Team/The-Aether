package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;

public class LorePageButton extends Button {
    private static final WidgetSprites BUTTON_TEXTURES = new WidgetSprites(new ResourceLocation(Aether.MODID, "menu/lore_button"), new ResourceLocation(Aether.MODID, "menu/lore_button_disabled"), new ResourceLocation(Aether.MODID, "menu/lore_button_highlighted"));

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
        ResourceLocation location = BUTTON_TEXTURES.get(this.isActive(), this.isHoveredOrFocused());
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        guiGraphics.blitSprite(location, this.getX(), this.getY(), this.width, this.height);
        int color = this.getFGColor();
        guiGraphics.drawCenteredString(fontRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color | 255 << 24);
    }
}
