package com.aetherteam.aether.client.gui.screen.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;

/**
 * [CODE COPY] - {@link net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen}.<br><br>
 * Cleaned up and made more abstract to mostly only have {@link RecipeBookComponent}-specific code.
 */
public abstract class AbstractRecipeBookScreen<T extends RecipeBookMenu<Container>, S extends RecipeBookComponent> extends AbstractContainerScreen<T> implements RecipeUpdateListener, RecipeBookBehavior<T, AbstractRecipeBookScreen<T, S>> {
    private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
    protected final S recipeBookComponent;
    protected boolean widthTooNarrow;

    public AbstractRecipeBookScreen(T menu, S recipeBook, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.recipeBookComponent = recipeBook;
    }

    protected void initScreen(int leftOffset) {
        this.widthTooNarrow = this.width < 379;
        this.getRecipeBookComponent().init(this.width, this.height, this.getMinecraft(), this.widthTooNarrow, this.getMenu());
        this.leftPos = this.getRecipeBookComponent().updateScreenPosition(this.width, this.getXSize());
        this.addRenderableWidget(new ImageButton(this.getGuiLeft() + leftOffset, this.height / 2 - 49, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
            this.getRecipeBookComponent().toggleVisibility();
            this.leftPos = this.getRecipeBookComponent().updateScreenPosition(this.width, this.getXSize());
            button.setPosition(this.getGuiLeft() + leftOffset, this.height / 2 - 49);
        }));
        this.titleLabelX = (this.getXSize() - this.font.width(this.getTitle())) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        RecipeBookBehavior.super.containerTick(this);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.getRecipeBookComponent().isVisible() && this.widthTooNarrow) {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
            this.getRecipeBookComponent().render(guiGraphics, mouseX, mouseY, partialTicks);
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
            this.getRecipeBookComponent().render(guiGraphics, mouseX, mouseY, partialTicks);
            this.getRecipeBookComponent().renderGhostRecipe(guiGraphics, this.getGuiLeft(), this.getGuiTop(), true, partialTicks);
        }
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.getRecipeBookComponent().renderTooltip(guiGraphics, this.getGuiLeft(), this.getGuiTop(), mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.getRecipeBookComponent().mouseClicked(mouseX, mouseY, button)) {
            return true;
        } else {
            return this.widthTooNarrow && this.getRecipeBookComponent().isVisible() || super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        RecipeBookBehavior.super.slotClicked(this, slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return RecipeBookBehavior.super.keyPressed(this, keyCode, scanCode, modifiers) && super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        return RecipeBookBehavior.super.hasClickedOutside(this, mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return RecipeBookBehavior.super.charTyped(this, codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    @Override
    public void recipesUpdated() {
        RecipeBookBehavior.super.recipesUpdated(this);
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}
