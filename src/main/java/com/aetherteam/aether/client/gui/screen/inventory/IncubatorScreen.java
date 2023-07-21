package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.screen.inventory.recipebook.IncubatorRecipeBookComponent;
import com.aetherteam.aether.inventory.menu.IncubatorMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class IncubatorScreen extends AbstractContainerScreen<IncubatorMenu> implements RecipeUpdateListener {
	private static final ResourceLocation INCUBATOR_GUI_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/incubator.png");
	private static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");
	public final IncubatorRecipeBookComponent recipeBookComponent;
	private boolean widthTooNarrow;
	
	public IncubatorScreen(IncubatorMenu container, Inventory inventory, Component name) {
		super(container, inventory, name);
		this.recipeBookComponent = new IncubatorRecipeBookComponent();
	}

	@Override
	public void init() {
		super.init();
		this.widthTooNarrow = this.width < 379;
		this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
		this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
		this.addRenderableWidget(new ImageButton(this.leftPos + 37, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (button) -> {
			this.recipeBookComponent.toggleVisibility();
			this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
			((ImageButton) button).setPosition(this.leftPos + 37, this.height / 2 - 49);
		}));

		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void containerTick() {
		super.containerTick();
		this.recipeBookComponent.tick();
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
			this.renderBg(poseStack, partialTicks, mouseX, mouseY);
			this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTicks);
		} else {
			this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTicks);
			super.render(poseStack, mouseX, mouseY, partialTicks);
			this.recipeBookComponent.renderGhostRecipe(poseStack, this.leftPos, this.topPos, false, partialTicks);
		}
		this.renderTooltip(poseStack, mouseX, mouseY);
		this.recipeBookComponent.renderTooltip(poseStack, this.leftPos, this.topPos, mouseX, mouseY);
	}

	@Override
	protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
		RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, INCUBATOR_GUI_TEXTURES);
		int guiLeft = this.leftPos;
		int guiTop = this.topPos;
		this.blit(poseStack, guiLeft, guiTop, 0, 0, this.imageWidth, this.imageHeight);
		if (this.menu.isIncubating()) {
			int incubationTimeRemaining = this.menu.getIncubationTimeRemaining();
			this.blit(poseStack, guiLeft + 74, (guiTop + 48) - incubationTimeRemaining, 176, 13 - incubationTimeRemaining, 14, incubationTimeRemaining + 1);
		}

		int progressionScaled = this.menu.getIncubationProgressScaled();
		this.blit(poseStack, guiLeft + 103, guiTop + 70 - progressionScaled, 179, 70 - progressionScaled, 10, progressionScaled);
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
	public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
		return !this.recipeBookComponent.keyPressed(pKeyCode, pScanCode, pModifiers) && super.keyPressed(pKeyCode, pScanCode, pModifiers);
	}

	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
		boolean flag = mouseX < (double) guiLeft || mouseY < (double) guiTop || mouseX >= (double) (guiLeft + this.imageWidth) || mouseY >= (double) (guiTop + this.imageHeight);
		return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
	}

	@Override
	public boolean charTyped(char pCodePoint, int pModifiers) {
		return this.recipeBookComponent.charTyped(pCodePoint, pModifiers) || super.charTyped(pCodePoint, pModifiers);
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
