package com.aether.client.gui.screen.config;

import com.aether.AetherConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherVisualSettingsScreen extends Screen {
    private final Screen lastScreen;

    protected AetherVisualSettingsScreen(Screen parentScreen) {
        super(new TranslationTextComponent("aether.config.visual.title"));
        this.lastScreen = parentScreen;
    }

    protected void init() {
        this.addButton(new Button(this.width / 2 - 155, this.height / 6, 150, 20, new StringTextComponent("Install Resource Pack"), (button) -> {
            AetherConfig.CLIENT.visual.installResourcePack.set(!AetherConfig.CLIENT.visual.installResourcePack.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6, 150, 20, new StringTextComponent("Classic Altar Name"), (button) -> {
            AetherConfig.CLIENT.visual.legacyAltarName.set(!AetherConfig.CLIENT.visual.legacyAltarName.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 30, 150, 20, new StringTextComponent("Classic Models"), (button) -> {
            AetherConfig.CLIENT.visual.legacyModels.set(!AetherConfig.CLIENT.visual.legacyModels.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 30, 150, 20, new StringTextComponent("Menu Button"), (button) -> {
            AetherConfig.CLIENT.visual.menuButton.set(!AetherConfig.CLIENT.visual.menuButton.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 60, 150, 20, new StringTextComponent("Menu Enabled"), (button) -> {
            AetherConfig.CLIENT.visual.menuEnabled.set(!AetherConfig.CLIENT.visual.menuEnabled.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 60, 150, 20, new StringTextComponent("Trivia Disabled"), (button) -> {
            AetherConfig.CLIENT.visual.triviaDisabled.set(!AetherConfig.CLIENT.visual.triviaDisabled.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 90, 150, 20, new StringTextComponent("Updated Aercloud Colors"), (button) -> {
            AetherConfig.CLIENT.visual.updatedAercloudColors.set(!AetherConfig.CLIENT.visual.updatedAercloudColors.get());
        }));
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, DialogTexts.GUI_DONE, (button) -> {
            this.minecraft.displayGuiScreen(this.lastScreen);
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
