package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.common.inventory.container.AccessoriesContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.client.gui.RenderButton;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.NetworkHandler;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;

import javax.annotation.Nonnull;

public class AccessoriesScreen extends AbstractContainerScreen<AccessoriesContainer> implements RecipeUpdateListener
{
    public static final ResourceLocation ACCESSORIES_INVENTORY = new ResourceLocation(Aether.MODID, "textures/gui/inventory/accessories.png");
    public static final ResourceLocation CURIO_INVENTORY = new ResourceLocation(Curios.MODID, "textures/gui/inventory.png");

    public static final ResourceLocation ACCESSORIES_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/inventory/button/accessories_button.png");

    private final RecipeBookComponent recipeBookGui = new RecipeBookComponent();

    private boolean buttonClicked;
    private boolean isRenderButtonHovered;

    public AccessoriesScreen(AccessoriesContainer accessoriesContainer, Inventory playerInventory, Component title) {
        super(accessoriesContainer, playerInventory, title);
        this.passEvents = true;
    }

    @Override
    public void init() {
        super.init();
        if (this.minecraft != null) {
            this.updateRenderButtons();
        }
    }

    public void updateRenderButtons() {
        this.buttons.removeIf(widget -> widget instanceof RenderButton);
        this.children.removeIf(widget -> widget instanceof RenderButton);
        for (Slot inventorySlot : this.menu.slots) {
            if (inventorySlot instanceof CurioSlot && !(inventorySlot instanceof CosmeticCurioSlot)) {
                this.addButton(new RenderButton((CurioSlot) inventorySlot, this.getGuiLeft() + inventorySlot.x + 11, this.getGuiTop() + inventorySlot.y - 3, 8, 8, 75, 0, 8,
                        CURIO_INVENTORY, (button) -> NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketToggleRender(((CurioSlot) inventorySlot).getIdentifier(), inventorySlot.getSlotIndex()))));
            }
        }
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        boolean isButtonHovered = false;
        for (AbstractWidget button : this.buttons) {
            if (button instanceof RenderButton) {
                ((RenderButton) button).renderButtonOverlay(matrixStack, mouseX, mouseY, partialTicks);
                if (button.isHovered()) {
                    isButtonHovered = true;
                }
            }
        }
        this.isRenderButtonHovered = isButtonHovered;
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        if (!this.isRenderButtonHovered && clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty() && this.getSlotUnderMouse() != null) {
            Slot slot = this.getSlotUnderMouse();
            if (slot instanceof CurioSlot && !slot.hasItem()) {
                CurioSlot slotCurio = (CurioSlot) slot;
                this.renderTooltip(matrixStack, new TextComponent(slotCurio.getSlotName()), mouseX, mouseY);
            }
        }
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            LocalPlayer clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventory.getCarried().isEmpty()) {
                if (this.isRenderButtonHovered) {
                    this.renderTooltip(matrixStack, new TranslatableComponent("gui.curios.toggle"), mouseX, mouseY);
                } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                    this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (AetherKeys.openAccessoryInventory.isActiveAndMatches(InputConstants.getKey(p_keyPressed_1_, p_keyPressed_2_))) {
            LocalPlayer playerEntity = this.getMinecraft().player;
            if (playerEntity != null) {
                playerEntity.closeContainer();
            }
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            this.font.draw(matrixStack, this.title, 115, 8, 4210752);
        }
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.getMinecraft().getTextureManager().bind(ACCESSORIES_INVENTORY);
            int i = this.getGuiLeft();
            int j = this.getGuiTop();
            this.blit(matrixStack, i, j, 0, 0, this.getXSize(), this.getYSize());
            InventoryScreen.renderEntityInInventory(i + 33, j + 75, 30, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, this.minecraft.player);
        }
    }

    @Override
    protected boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        if (this.isRenderButtonHovered) {
            return false;
        }
        return super.isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    public boolean mouseReleased(double mouseReleased1, double mouseReleased3, int mouseReleased5) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(mouseReleased1, mouseReleased3, mouseReleased5);
        }
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookGui.recipesUpdated();
    }

    @Nonnull
    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookGui;
    }
}
