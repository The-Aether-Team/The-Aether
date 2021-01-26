package com.aether.client.gui.screen.config;

import com.aether.AetherConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherWorldSettingsScreen extends Screen {
    private final Screen lastScreen;

    protected AetherWorldSettingsScreen(Screen parentScreen) {
        super(new TranslationTextComponent("aether.config.worldgen.title"));
        this.lastScreen = parentScreen;
    }

    protected void init() {
        this.addButton(new Button(this.width / 2 - 100, 30, 200, 20, new StringTextComponent("Holiday Generation"), (button) -> {
            AetherConfig.COMMON.worldGen.holidayGeneration.set(!AetherConfig.COMMON.worldGen.holidayGeneration.get());
        }));
        this.addButton(new Button(this.width / 2 - 100, 60, 200, 20, new StringTextComponent("Pink Aercloud Generation"), (button) -> {
            AetherConfig.COMMON.worldGen.pinkAercloudGeneration.set(!AetherConfig.COMMON.worldGen.pinkAercloudGeneration.get());
        }));
        this.addButton(new Button(this.width / 2 - 100, 90, 200, 20, new StringTextComponent("Tall Grass Enabled"), (button) -> {
            AetherConfig.COMMON.worldGen.tallGrassEnabled.set(!AetherConfig.COMMON.worldGen.tallGrassEnabled.get());
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
