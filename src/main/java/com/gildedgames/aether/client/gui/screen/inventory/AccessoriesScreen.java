package com.gildedgames.aether.client.gui.screen.inventory;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.screen.perks.CustomizationScreen;
import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.common.inventory.container.AccessoriesContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.client.gui.CuriosScreen;
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

    public static Tuple<Integer, Integer> getButtonOffset(Screen screen) {
        int x = 0;
        int y = 0;
        if (screen instanceof InventoryScreen || screen instanceof CuriosScreen) {
            x = 27;
            y = 68;
        }
        if (screen instanceof CreativeModeInventoryScreen) {
            x = 74;
            y = 40;
        }
        if (screen instanceof AccessoriesScreen) {
            x = 9;
            y = 68;
        }
        return new Tuple<>(x, y);
    }

    @Override
    public void init() {
        super.init();
        if (this.minecraft != null) {
            this.updateRenderButtons();
        }
        this.addRenderableWidget(new Button(this.leftPos - 22, this.topPos + 2, 20, 20, new TextComponent("?"),
                (pressed) -> this.minecraft.setScreen(new CustomizationScreen(this)),
                (button, matrixStack, x, y) -> this.renderTooltip(matrixStack, new TranslatableComponent("gui.aether.accessories.perks_button"), x, y))
        );
    }

    public void updateRenderButtons() {
        this.narratables.removeIf(widget -> widget instanceof RenderButton);
        this.children().removeIf(widget -> widget instanceof RenderButton);
        this.renderables.removeIf(widget -> widget instanceof RenderButton);
        for (Slot inventorySlot : this.menu.slots) {
            if (inventorySlot instanceof CurioSlot curioSlot && !(inventorySlot instanceof CosmeticCurioSlot)) {
                this.addRenderableWidget(new RenderButton(curioSlot, this.leftPos + inventorySlot.x + 11, this.topPos + inventorySlot.y - 3, 8, 8, 75, 0, 8, CURIO_INVENTORY,
                        (button) -> NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketToggleRender(curioSlot.getIdentifier(), inventorySlot.getSlotIndex()))));
            }
        }
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        boolean isButtonHovered = false;
        for (Widget button : this.renderables) {
            if (button instanceof RenderButton renderButton) {
                renderButton.renderButtonOverlay(poseStack, mouseX, mouseY, partialTicks);
                if (renderButton.isHoveredOrFocused()) {
                    isButtonHovered = true;
                }
            }
        }
        this.isRenderButtonHovered = isButtonHovered;
        LocalPlayer clientPlayer = Minecraft.getInstance().player;
        if (!this.isRenderButtonHovered && clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty() && this.getSlotUnderMouse() != null) {
            Slot slot = this.getSlotUnderMouse();
            if (slot instanceof CurioSlot curioSlot && !slot.hasItem()) {
                this.renderTooltip(poseStack, new TextComponent(curioSlot.getSlotName()), mouseX, mouseY);
            }
        }
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            LocalPlayer clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty()) {
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
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ACCESSORIES_INVENTORY);
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
