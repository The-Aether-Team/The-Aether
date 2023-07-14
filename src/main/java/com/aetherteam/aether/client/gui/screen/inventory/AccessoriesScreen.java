package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.inventory.menu.AccessoriesMenu;
import com.aetherteam.aether.mixin.mixins.client.accessor.ScreenAccessor;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.ClearItemPacket;
import com.aetherteam.aether.perk.PerkUtil;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.Curios;
import top.theillusivec4.curios.client.gui.CuriosScreen;
import top.theillusivec4.curios.client.gui.RenderButton;
import top.theillusivec4.curios.common.inventory.CosmeticCurioSlot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.network.NetworkHandler;
import top.theillusivec4.curios.common.network.client.CPacketDestroy;
import top.theillusivec4.curios.common.network.client.CPacketToggleRender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AccessoriesScreen extends EffectRenderingInventoryScreen<AccessoriesMenu> implements RecipeUpdateListener {
    public static final ResourceLocation ACCESSORIES_INVENTORY = new ResourceLocation(Aether.MODID, "textures/gui/inventory/accessories.png");
    public static final ResourceLocation ACCESSORIES_INVENTORY_CREATIVE = new ResourceLocation(Aether.MODID, "textures/gui/inventory/accessories_creative.png");
    public static final ResourceLocation CURIO_INVENTORY = new ResourceLocation(Curios.MODID, "textures/gui/inventory.png");

    public static final ResourceLocation ACCESSORIES_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/inventory/button/accessories_button.png");
    public static final ResourceLocation RECIPE_BUTTON_LOCATION = new ResourceLocation("textures/gui/recipe_button.png");

    public static final ResourceLocation SKINS_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/skins_button.png");
    public static final ResourceLocation CUSTOMIZATION_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/perks/customization/customization_button.png");

    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private boolean widthTooNarrow;
    private boolean buttonClicked;
    private boolean isRenderButtonHovered;
    @Nullable
    private Slot destroyItemSlot;
    private static final SimpleContainer DESTROY_ITEM_CONTAINER = new SimpleContainer(1);

    public AccessoriesScreen(AccessoriesMenu accessoriesMenu, Inventory playerInventory, Component title) {
        super(accessoriesMenu, playerInventory, title);
        this.passEvents = true;
    }

    @Override
    protected void containerTick() {
        this.recipeBookComponent.tick();
    }

    public static Tuple<Integer, Integer> getButtonOffset(Screen screen) {
        int x = 0;
        int y = 0;
        if (screen instanceof InventoryScreen || screen instanceof CuriosScreen) {
            x = AetherConfig.CLIENT.button_inventory_x.get();
            y = AetherConfig.CLIENT.button_inventory_y.get();
        }
        if (screen instanceof CreativeModeInventoryScreen) {
            x = AetherConfig.CLIENT.button_creative_x.get();
            y = AetherConfig.CLIENT.button_creative_y.get();
        }
        if (screen instanceof AccessoriesScreen) {
            x = AetherConfig.CLIENT.button_accessories_x.get();
            y = AetherConfig.CLIENT.button_accessories_y.get();
        }
        return new Tuple<>(x, y);
    }

    @Override
    public void init() {
        super.init();
        if (this.minecraft != null) {
            if (this.minecraft.player != null) {
                this.imageWidth = this.minecraft.player.isCreative() ? 176 + this.creativeXOffset() : 176;
            }
            this.widthTooNarrow = this.width < 379;
            this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
            this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
            this.addRenderableWidget(new ImageButton(this.leftPos + 142, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, (pressed) -> {
                this.recipeBookComponent.toggleVisibility();
                this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
                pressed.setPosition(this.leftPos + 142, this.height / 2 - 22);
                this.buttonClicked = true;
            }));
            this.addWidget(this.recipeBookComponent);
            this.setInitialFocus(this.recipeBookComponent);

            this.updateRenderButtons();

            ImageButton skinsButton = new ImageButton(this.leftPos - 22, this.topPos + 2, 20, 20, 0, 0, 20, SKINS_BUTTON, 20, 40,
                    (pressed) -> this.getMinecraft().setScreen(new MoaSkinsScreen(this)),
                    Component.translatable("gui.aether.accessories.skins_button")) {
                @Override
                public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                    super.render(poseStack, mouseX, mouseY, partialTick);
                    if (!AccessoriesScreen.this.recipeBookComponent.isVisible()) {
                        this.setX(AccessoriesScreen.this.leftPos - 22);
                        this.setY(AccessoriesScreen.this.topPos + 2);
                    } else {
                        this.setX(AccessoriesScreen.this.leftPos + 2);
                        this.setY(AccessoriesScreen.this.topPos - 22);
                    }
                }
            };
            skinsButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.skins_button")));
            this.addRenderableWidget(skinsButton);

            User user = UserData.Client.getClientUser();
            if (user != null && (PerkUtil.hasDeveloperGlow().test(user) || PerkUtil.hasHalo().test(user))) {
                ImageButton customizationButton = new ImageButton(this.leftPos - 22, this.topPos + 24, 20, 20, 0, 0, 20, CUSTOMIZATION_BUTTON, 20, 40,
                        (pressed) -> this.getMinecraft().setScreen(new AetherCustomizationsScreen(this)),
                        Component.translatable("gui.aether.accessories.customization_button")) {
                    @Override
                    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                        super.render(poseStack, mouseX, mouseY, partialTick);
                        if (!AccessoriesScreen.this.recipeBookComponent.isVisible()) {
                            this.setX(AccessoriesScreen.this.leftPos - 22);
                            this.setY(AccessoriesScreen.this.topPos + 24);
                        } else {
                            this.setX(AccessoriesScreen.this.leftPos + 24);
                            this.setY(AccessoriesScreen.this.topPos - 22);
                        }
                    }
                };
                customizationButton.setTooltip(Tooltip.create(Component.translatable("gui.aether.accessories.customization_button")));
                this.addRenderableWidget(customizationButton);
            }
        }
    }

    public void updateRenderButtons() {
        ScreenAccessor screenAccessor = (ScreenAccessor) this;
        screenAccessor.aether$getNarratables().removeIf(widget -> widget instanceof RenderButton);
        this.children().removeIf(widget -> widget instanceof RenderButton);
        this.renderables.removeIf(widget -> widget instanceof RenderButton);
        for (Slot inventorySlot : this.menu.slots) {
            if (inventorySlot instanceof CurioSlot curioSlot && !(inventorySlot instanceof CosmeticCurioSlot)) {
                this.addRenderableWidget(new RenderButton(curioSlot, this.leftPos + inventorySlot.x + 11, this.topPos + inventorySlot.y - 3, 8, 8, 75, 0, 8, CURIO_INVENTORY,
                        (button) -> NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketToggleRender(curioSlot.getIdentifier(), inventorySlot.getSlotIndex())))
                {
                    @Override
                    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                        this.setX(AccessoriesScreen.this.leftPos + inventorySlot.x + 11);
                        this.setY(AccessoriesScreen.this.topPos + inventorySlot.y - 3);
                    }
                });
            }
        }
    }

    @Override
    public boolean canSeeEffects() {
        int i = this.leftPos + this.imageWidth + 2 + this.creativeXOffset();
        int j = this.width - i;
        return j > 13;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
            this.renderBg(poseStack, partialTicks, mouseX, mouseY);
            this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTicks);
        } else {
            this.recipeBookComponent.render(poseStack, mouseX, mouseY, partialTicks);
            super.render(poseStack, mouseX, mouseY, partialTicks);
            this.recipeBookComponent.renderGhostRecipe(poseStack, this.leftPos, this.topPos, false, partialTicks);

            boolean isButtonHovered = false;
            for (Renderable renderable : this.renderables) {
                if (renderable instanceof RenderButton renderButton) {
                    renderButton.renderButtonOverlay(poseStack, mouseX, mouseY, partialTicks);
                    if (renderButton.isHovered()) {
                        isButtonHovered = true;
                    }
                }
            }
            this.isRenderButtonHovered = isButtonHovered;
            LocalPlayer clientPlayer = Minecraft.getInstance().player;
            if (!this.isRenderButtonHovered && clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty() && this.getSlotUnderMouse() != null) {
                Slot slot = this.getSlotUnderMouse();
                if (slot instanceof CurioSlot curioSlot && !slot.hasItem()) {
                    this.renderTooltip(poseStack, Component.literal(curioSlot.getSlotName()), mouseX, mouseY);
                }
            }

            if (this.minecraft != null && this.minecraft.player != null) {
                if (this.minecraft.player.isCreative() && this.destroyItemSlot == null) {
                    this.destroyItemSlot = new Slot(DESTROY_ITEM_CONTAINER, 0, 172, 142);
                    this.menu.slots.add(this.destroyItemSlot);
                } else if (!this.minecraft.player.isCreative() && this.destroyItemSlot != null) {
                    this.menu.slots.remove(this.destroyItemSlot);
                    this.destroyItemSlot = null;
                }
            }

            if (this.destroyItemSlot != null && this.isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, mouseX, mouseY)) {
                this.renderTooltip(poseStack, Component.translatable("inventory.binSlot"), mouseX, mouseY);
            }

            if (this.minecraft != null && this.minecraft.player != null) {
                this.imageWidth = this.minecraft.player.isCreative() ? 176 + this.creativeXOffset() : 176;
            }
        }
        this.renderTooltip(poseStack, mouseX, mouseY);
        this.recipeBookComponent.renderTooltip(poseStack, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, this.minecraft.player.isCreative() ? ACCESSORIES_INVENTORY_CREATIVE : ACCESSORIES_INVENTORY);
            int i = this.getGuiLeft();
            int j = this.getGuiTop();
            blit(matrixStack, i, j, 0, 0, this.getXSize() + this.creativeXOffset(), this.getYSize());
            InventoryScreen.renderEntityInInventoryFollowsMouse(matrixStack, i + 33, j + 75, 30, (float) (i + 31) - mouseX, (float) (j + 75 - 50) - mouseY, this.minecraft.player);
        }
    }

    @Override
    protected void renderTooltip(@Nonnull PoseStack matrixStack, int mouseX, int mouseY) {
        Minecraft mc = this.minecraft;
        if (mc != null) {
            LocalPlayer clientPlayer = mc.player;
            if (clientPlayer != null && clientPlayer.inventoryMenu.getCarried().isEmpty()) {
                if (this.isRenderButtonHovered) {
                    this.renderTooltip(matrixStack, Component.translatable("gui.curios.toggle"), mouseX, mouseY);
                } else if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                    this.renderTooltip(matrixStack, this.hoveredSlot.getItem(), mouseX, mouseY);
                }
            }
        }
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (AetherKeys.OPEN_ACCESSORY_INVENTORY.isActiveAndMatches(InputConstants.getKey(p_keyPressed_1_, p_keyPressed_2_))) {
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
            this.font.draw(matrixStack, this.title, 115, 6, 4210752);
        }
    }

    @Override
    protected boolean isHovering(int rectX, int rectY, int rectWidth, int rectHeight, double pointX, double pointY) {
        if (this.isRenderButtonHovered) {
            return false;
        }
        return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.recipeBookComponent.mouseClicked(pMouseX, pMouseY, pButton)) {
            this.setFocused(this.recipeBookComponent);
            return true;
        } else {
            return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.mouseClicked(pMouseX, pMouseY, pButton);
        }
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
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean flag = mouseX < (double) guiLeft || mouseY < (double) guiTop || mouseX >= (double) (guiLeft + this.imageWidth) || mouseY >= (double) (guiTop + this.imageHeight);
        return this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag;
    }

    @Override
    protected void slotClicked(@Nullable Slot slot, int slotId, int mouseButton, @Nonnull ClickType type) {
        this.recipeBookComponent.slotClicked(slot);
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.gameMode != null) {
            boolean flag = type == ClickType.QUICK_MOVE;
            if (slot != null || type == ClickType.QUICK_CRAFT) {
                if (slot == null || slot.mayPickup(this.minecraft.player)) {
                    if (slot == this.destroyItemSlot && this.destroyItemSlot != null && flag) {
                        for (int j = 0; j < this.minecraft.player.inventoryMenu.getItems().size(); ++j) {
                            this.minecraft.gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, j);
                            NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketDestroy());
                        }
                    } else {
                        if (slot == this.destroyItemSlot && this.destroyItemSlot != null) {
                            this.menu.setCarried(ItemStack.EMPTY);
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ClearItemPacket(this.minecraft.player.getId()));
                        }
                    }
                }
            }
            super.slotClicked(slot, slotId, mouseButton, type);
        }
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookComponent.recipesUpdated();
    }

    @Nonnull
    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }

    public int creativeXOffset() {
        if (this.minecraft != null && this.minecraft.player != null && this.minecraft.player.isCreative()) {
            return 18;
        } else {
            return 0;
        }
    }
}
