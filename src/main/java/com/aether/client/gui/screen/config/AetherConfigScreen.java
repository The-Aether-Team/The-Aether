package com.aether.client.gui.screen.config;

import com.aether.AetherConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherConfigScreen extends Screen {
    private final Screen lastScreen;

    public AetherConfigScreen(Screen parentScreen) {
        super(new TranslationTextComponent("aether.config.title"));
        this.lastScreen = parentScreen;
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 100, 30, 200, 20, new TranslationTextComponent("aether.config.gameplay.title"), (button) -> {
            this.minecraft.displayGuiScreen(new AetherGameplaySettingsScreen(this));
        }));
        this.addButton(new Button(this.width / 2 - 100, 60, 200, 20, new TranslationTextComponent("aether.config.visual.title"), (button) -> {
            this.minecraft.displayGuiScreen(new AetherVisualSettingsScreen(this));
        }));
        this.addButton(new Button(this.width / 2 - 100, 90, 200, 20, new TranslationTextComponent("aether.config.worldgen.title"), (button) -> {
            this.minecraft.displayGuiScreen(new AetherWorldSettingsScreen(this));
        }));
        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, DialogTexts.GUI_DONE, (p_213104_1_) -> {
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
