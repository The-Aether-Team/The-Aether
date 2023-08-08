package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class PatreonButton extends Button {
    public PatreonButton(Builder builder) {
        super(builder);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        int u = 0;
        int v = 215;
        if (this.isHovered()) {
            u = 54;
        }
        guiGraphics.blit(MoaSkinsScreen.MOA_SKINS_GUI, this.getX(), this.getY(), u, v, this.getWidth(), this.getHeight());
        guiGraphics.drawCenteredString(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }
}
