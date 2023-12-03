package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class PatreonButton extends Button {
    private final boolean small;

    public PatreonButton(Builder builder) {
        this(builder, false);
    }

    public PatreonButton(Builder builder, boolean small) {
        super(builder);
        this.small = small;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        int u = 0;
        int v = !this.small ? 215 : 233;
        if (this.isHovered()) {
            u = 54;
        }
        guiGraphics.blit(MoaSkinsScreen.MOA_SKINS_GUI, this.getX(), this.getY(), u, v, this.getWidth(), this.getHeight());
        guiGraphics.drawCenteredString(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }
}
