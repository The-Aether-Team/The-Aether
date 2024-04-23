package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import io.github.fabricators_of_create.porting_lib.gui.utils.ModdedButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;

public class ChangeSkinButton extends ModdedButton {
    private final ButtonType buttonType;

    public ChangeSkinButton(ButtonType buttonType, Builder builder) {
        super(builder);
        this.buttonType = buttonType;
        this.active = false;
    }

    public void onPress() {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int u = 0;
        if (this.buttonType == ButtonType.REMOVE) {
            u += 3;
        }
        if (this.isActive()) {
            u += 1;
            if (this.isHovered()) {
                u += 1;
            }
        }
        guiGraphics.blit(MoaSkinsScreen.MOA_SKINS_GUI, this.getX(), this.getY(), u * 7, 184, 7, 7);
    }

    public enum ButtonType {
        APPLY,
        REMOVE
    }
}
