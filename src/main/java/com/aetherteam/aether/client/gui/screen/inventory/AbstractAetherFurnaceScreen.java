package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.inventory.menu.AbstractAetherFurnaceMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class AbstractAetherFurnaceScreen<T extends AbstractAetherFurnaceMenu> extends AbstractContainerScreen<T> implements RecipeUpdateListener {
    private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
    public final AbstractFurnaceRecipeBookComponent recipeBookComponent;
    private boolean widthTooNarrow;
    private final ResourceLocation texture;

    public AbstractAetherFurnaceScreen(T menu, AbstractFurnaceRecipeBookComponent recipeBookComponent, Inventory playerInventory, Component title, ResourceLocation texture) {
        super(menu, playerInventory, title);
        this.recipeBookComponent = recipeBookComponent;
        this.texture = texture;
    }

    @Override
    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new ImageButton(this.leftPos + 20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (p_97863_) -> {
            this.recipeBookComponent.toggleVisibility();
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            ((ImageButton)p_97863_).setPosition(this.leftPos + 20, this.height / 2 - 49);
        }));
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.recipeBookComponent.tick();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBg(poseStack, partialTick, mouseX, mouseY);
            this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTick);
        } else {
            this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTick);
            super.render(poseStack, mouseX, mouseY, partialTick);
            this.recipeBookComponent.renderGhostRecipe(poseStack, this.leftPos, this.topPos, true, partialTick);
        }
        this.renderTooltip(poseStack, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(poseStack, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(poseStack, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        int l = this.menu.getBurnProgress();
        this.blit(poseStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.widthTooNarrow && this.recipeBookComponent.isVisible() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        this.recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return !this.recipeBookComponent.keyPressed(keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean flag = mouseX < (double) guiLeft || mouseY < (double) guiTop || mouseX >= (double) (guiLeft + this.imageWidth) || mouseY >= (double) (guiTop + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return this.recipeBookComponent.charTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}