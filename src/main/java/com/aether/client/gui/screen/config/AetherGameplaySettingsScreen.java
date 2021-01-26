package com.aether.client.gui.screen.config;

import com.aether.AetherConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AetherGameplaySettingsScreen extends Screen {
    private final Screen lastScreen;

    protected AetherGameplaySettingsScreen(Screen parentScreen) {
        super(new TranslationTextComponent("aether.config.gameplay.title"));
        this.lastScreen = parentScreen;
    }

    protected void init() {
        this.addButton(new Button(this.width / 2 - 155, this.height / 6, 150, 20, new StringTextComponent("Aether Start"), (button) -> {
            AetherConfig.COMMON.gameplay.aetherStart.set(!AetherConfig.COMMON.gameplay.aetherStart.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6, 150, 20, new StringTextComponent("Edible Ambrosium"), (button) -> {
            AetherConfig.COMMON.gameplay.edibleAmbrosium.set(!AetherConfig.COMMON.gameplay.edibleAmbrosium.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 30, 150, 20, new StringTextComponent("Disable Eternal Day"), (button) -> {
            AetherConfig.COMMON.gameplay.disableEternalDay.set(!AetherConfig.COMMON.gameplay.disableEternalDay.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 30, 150, 20, new StringTextComponent("Disable Portal"), (button) -> {
            AetherConfig.COMMON.gameplay.disablePortal.set(!AetherConfig.COMMON.gameplay.disablePortal.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 60, 150, 20, new StringTextComponent("Disable Startup Loot"), (button) -> {
            AetherConfig.COMMON.gameplay.disableStartupLoot.set(!AetherConfig.COMMON.gameplay.disableStartupLoot.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 60, 150, 20, new StringTextComponent("Golden Feather"), (button) -> {
            AetherConfig.COMMON.gameplay.goldenFeather.set(!AetherConfig.COMMON.gameplay.goldenFeather.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 90, 150, 20, new StringTextComponent("Max Life Shards"), (button) -> {
            AetherConfig.COMMON.gameplay.maxLifeShards.set(AetherConfig.COMMON.gameplay.maxLifeShards.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 90, 150, 20, new StringTextComponent("Repeat Sun Spirit Dialogue"), (button) -> {
            AetherConfig.COMMON.gameplay.repeatSunSpiritDialogue.set(!AetherConfig.COMMON.gameplay.repeatSunSpiritDialogue.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 120, 150, 20, new StringTextComponent("Skyroot Bucket Only"), (button) -> {
            AetherConfig.COMMON.gameplay.skyrootBucketOnly.set(!AetherConfig.COMMON.gameplay.skyrootBucketOnly.get());
        }));
        this.addButton(new Button(this.width / 2 + 15, this.height / 6 + 120, 150, 20, new StringTextComponent("Sun Altar Multiplayer"), (button) -> {
            AetherConfig.COMMON.gameplay.sunAltarMultiplayer.set(!AetherConfig.COMMON.gameplay.sunAltarMultiplayer.get());
        }));
        this.addButton(new Button(this.width / 2 - 155, this.height / 6 + 150, 150, 20, new StringTextComponent("Valkyrie Cape"), (button) -> {
            AetherConfig.COMMON.gameplay.valkyrieCape.set(!AetherConfig.COMMON.gameplay.valkyrieCape.get());
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
