package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import io.github.fabricators_of_create.porting_lib.gui.utils.ModdedButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class RefreshButton extends ModdedButton {
    public static final int reboundMax = 1200;
    public static int reboundTimer = 0;

    public RefreshButton(Builder builder) {
        super(builder);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        int u = 108;
        int v = 215;
        if (this.isHovered() || reboundTimer > 0) { // Checks for reboundTimer to see if it is ticking, if so then this button is on cooldown.
            u = 126;
        }
        guiGraphics.blit(MoaSkinsScreen.MOA_SKINS_GUI, this.getX(), this.getY(), u, v, this.getWidth(), this.getHeight());
        guiGraphics.drawCenteredString(minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }
}
