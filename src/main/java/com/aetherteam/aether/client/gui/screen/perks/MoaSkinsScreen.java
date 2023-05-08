package com.aetherteam.aether.client.gui.screen.perks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.gui.component.skins.ChangeSkinButton;
import com.aetherteam.aether.client.gui.component.skins.PatreonButton;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.server.ServerMoaSkinPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;

import java.util.*;

public class MoaSkinsScreen extends Screen {
    public static final ResourceLocation MOA_SKINS_GUI = new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/skins.png");
    private static final String PATREON_LINK = "https://www.patreon.com/TheAetherTeam";

    private final Screen lastScreen;
    private final int imageWidth = 176;
    private final int imageHeight = 184;
    private int leftPos;
    private int topPos;

    private final CustomizationsOptions customizations = CustomizationsOptions.INSTANCE;
    private List<MoaSkins.MoaSkin> moaSkins;
    private List<Float> snapPoints;
    private MoaSkins.MoaSkin selectedSkin;
    private ChangeSkinButton applyButton;
    private ChangeSkinButton removeButton;

    private boolean scrolling;
    private float scrollX;
    private float moaRotation = 0.0F;
    private Moa previewMoa;

    public MoaSkinsScreen(Screen lastScreen) {
        super(Component.translatable("gui.aether.moa_skins.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        this.moaSkins = List.copyOf(MoaSkins.getMoaSkins().values());

        this.snapPoints = new ArrayList<>();
        for (int i = 0; i <= (this.moaSkins.size() - this.maxSlots()); i++) {
            this.snapPoints.add((this.scrollbarInsetWidth() / (this.moaSkins.size() - this.maxSlots())) * i);
        }

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if (this.getMinecraft().player != null) {
            UUID uuid = this.getMinecraft().player.getUUID();
            Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();

            if (this.selectedSkin == null) {
                this.selectedSkin = userSkinsData.containsKey(uuid) ? userSkinsData.get(uuid).moaSkin() : this.moaSkins.get(0);
            }

            this.applyButton = this.addRenderableWidget(new ChangeSkinButton(ChangeSkinButton.ButtonType.APPLY, Button.builder(Component.translatable("gui.aether.moa_skins.button.apply"),
                    (pressed) -> AetherPlayer.get(this.getMinecraft().player).ifPresent((aetherPlayer) -> {
                        AetherPacketHandler.sendToServer(new ServerMoaSkinPacket.Apply(this.getMinecraft().player.getUUID(), new MoaData(aetherPlayer.getLastRiddenMoa(), this.selectedSkin)));
                        this.customizations.setMoaSkin(this.selectedSkin.getId());
                        this.customizations.save();
                        this.customizations.load();
                    })
            ).pos((this.leftPos + this.imageWidth) - 20, this.topPos + 13).size(7, 7)));
            this.removeButton = this.addRenderableWidget(new ChangeSkinButton(ChangeSkinButton.ButtonType.REMOVE, Button.builder(Component.translatable("gui.aether.moa_skins.button.remove"),
                    (pressed) -> {
                        AetherPacketHandler.sendToServer(new ServerMoaSkinPacket.Remove(this.getMinecraft().player.getUUID()));
                        this.customizations.setMoaSkin("");
                        this.customizations.save();
                        this.customizations.load();
                    }
            ).pos((this.leftPos + this.imageWidth) - 20, this.topPos + 22).size(7, 7)));

            this.addRenderableWidget(new PatreonButton(Button.builder(Component.translatable("gui.aether.moa_skins.button.donate"),
                    (pressed) -> this.getMinecraft().setScreen(new ConfirmLinkScreen((callback) -> {
                        if (callback) {
                            Util.getPlatform().openUri(PATREON_LINK);
                        }
                        this.getMinecraft().setScreen(this);
                    }, PATREON_LINK, true))
            ).pos(this.leftPos + (this.imageWidth / 2) - 66, this.topPos + this.imageHeight - 25).size(54, 18)));

            String link = "http://aether-mod.net/api/verify/" + uuid;
            this.addRenderableWidget(new PatreonButton(Button.builder(Component.translatable("gui.aether.moa_skins.button.connect"),
                    (pressed) -> this.getMinecraft().setScreen(new ConfirmLinkScreen((callback) -> {
                        if (callback) {
                            Util.getPlatform().openUri(link);
                        }
                        this.getMinecraft().setScreen(this);
                    }, link, true))
            ).pos(this.leftPos + (this.imageWidth / 2) + 12, this.topPos + this.imageHeight - 25).size(54, 18)));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        this.renderWindow(poseStack);
        this.renderSlots(poseStack, mouseX, mouseY);
        this.renderInterface(poseStack, mouseX, mouseY, partialTick);
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    private void renderWindow(PoseStack poseStack) {
        User user = UserData.Client.getClientUser();
        Font font = this.getMinecraft().font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        Component component = user == null ? Component.translatable("gui.aether.moa_skins.text.donate") : Component.translatable("gui.aether.moa_skins.text.reward");
        int y = (this.topPos + this.imageHeight - 69) + font.wordWrapHeight(component, this.imageWidth - 20);
        for (FormattedCharSequence sequence : font.split(component, this.imageWidth - 20)) {
            Gui.drawCenteredString(poseStack, font, sequence, this.leftPos + (this.imageWidth / 2), y, 16777215);
            y += 12;
        }
    }

    private void renderInterface(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        User user = UserData.Client.getClientUser();
        if (user != null && this.selectedSkin.getUserPredicate().test(user)) {
            this.applyButton.setIsActive(true);
            this.removeButton.setIsActive(true);

            if (this.selectedSkin.getInfo().lifetime() || !user.isPatron() || !user.isPledging() || user.getPatronTierLevel() < this.selectedSkin.getInfo().tier().getLevel()) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 8);
                this.renderLifetimeIcon(poseStack, mouseOver);
                if (mouseOver) {
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.lifetime"), Component.translatable("gui.aether.moa_skins.tooltip.lifetime"), poseStack, mouseX, mouseY);
                }
            } else if (user.isPatron()) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 7);
                this.renderPledgingIcon(poseStack, mouseOver);
                if (mouseOver) {
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.pledging"), Component.translatable("gui.aether.moa_skins.tooltip.pledging", user.getPatreonTier().getDisplayName()), poseStack, mouseX, mouseY);
                }
            }
        } else {
            this.applyButton.setIsActive(false);
            this.removeButton.setIsActive(false);

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
            this.blit(poseStack, this.leftPos + 13, this.topPos + 13, 54, 191, 10, 14); // Lock Icon

            if (this.selectedSkin.getInfo().lifetime()) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 8);
                this.renderLifetimeIcon(poseStack, mouseOver);
                if (mouseOver) {
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.lifetime"), Component.translatable("gui.aether.moa_skins.tooltip.access.lifetime", this.selectedSkin.getInfo().tier().getDisplayName()), poseStack, mouseX, mouseY);
                }
            } else {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 7);
                this.renderPledgingIcon(poseStack, mouseOver);
                if (mouseOver) {
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.pledging"), Component.translatable("gui.aether.moa_skins.tooltip.access.pledging", this.selectedSkin.getInfo().tier().getDisplayName()), poseStack, mouseX, mouseY);
                }
            }
        }
        this.renderMoa(partialTick);
        Screen.drawCenteredString(poseStack, this.getMinecraft().font, this.selectedSkin.getDisplayName(), this.leftPos + (this.imageWidth / 2), this.topPos + 12, 16777215); // Skin Name
        Screen.drawCenteredString(poseStack, this.getMinecraft().font, this.getTitle(), this.leftPos + (this.imageWidth / 2), this.topPos - 15, 16777215); // Title
    }

    private void renderLifetimeIcon(PoseStack poseStack, boolean mouseOver) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
        blit(poseStack, this.leftPos + 13, (this.topPos + (this.imageHeight / 2)) - 9, mouseOver ? 63 : 55, 184, 8, 7); // Lifetime Icon
    }

    private void renderPledgingIcon(PoseStack poseStack, boolean mouseOver) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
        blit(poseStack, this.leftPos + 13, (this.topPos + (this.imageHeight / 2)) - 9, mouseOver ? 49 : 42, 184, 7, 7);
    }

    private boolean isMouseOverIcon(int mouseX, int mouseY, int width) {
        int leftPos = this.leftPos + 13;
        int topPos = (this.topPos + (this.height / 2)) - 36;
        double mouseXDiff = mouseX - leftPos;
        double mouseYDiff = mouseY - topPos;
        return mouseYDiff <= 7 && mouseYDiff >= 0 && mouseXDiff <= width && mouseXDiff >= 0;
    }

    private void renderTooltip(MutableComponent title, Component description, PoseStack poseStack, int mouseX, int mouseY) {
        List<FormattedText> formattedTextList = new ArrayList<>();
        formattedTextList.add(title.withStyle(ChatFormatting.GOLD));
        formattedTextList.addAll(this.getMinecraft().font.getSplitter().splitLines(description, this.width / 3, Style.EMPTY));
        this.renderComponentTooltip(poseStack, formattedTextList, mouseX, mouseY, this.getMinecraft().font);
    }

    private void renderMoa(float partialTick) {
        if (this.getMinecraft().level != null) {
            if (this.previewMoa == null) {
                Moa moa = AetherEntityTypes.MOA.get().create(this.getMinecraft().level);
                if (moa != null) {
                    moa.generateMoaUUID();
                    moa.setMoaType(AetherMoaTypes.BLUE.get());
                    moa.setSaddled(true);
                    this.previewMoa = moa;
                }
            } else {
                this.moaRotation = Mth.wrapDegrees(Mth.lerp(partialTick, this.moaRotation, this.moaRotation + 2.5F));
                renderRotatingEntity(this.leftPos + (this.imageWidth / 2), this.topPos + (this.imageHeight / 2) - 4, 27, this.moaRotation, -20.0F, this.previewMoa);
            }
        }
    }

    /**
     * Based on {@link net.minecraft.client.gui.screens.inventory.InventoryScreen#renderEntityInInventoryRaw(int, int, int, float, float, LivingEntity)}.
     */
    public static void renderRotatingEntity(int posX, int posY, int scale, float angleXComponent, float angleYComponent, LivingEntity livingEntity) {
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate((float) posX, (float) posY, 1050.0F);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack1 = new PoseStack();
        poseStack1.translate(0.0F, 0.0F, 1000.0F);
        poseStack1.scale((float) scale, (float) scale, (float) scale);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf1 = new Quaternionf().rotateX(angleYComponent * ((float) Math.PI / 180.0F));
        quaternionf.mul(quaternionf1);
        poseStack1.mulPose(quaternionf);
        float f2 = livingEntity.yBodyRot;
        float f3 = livingEntity.getYRot();
        float f4 = livingEntity.getXRot();
        livingEntity.yBodyRot = 180.0F + angleXComponent;
        livingEntity.setYRot(180.0F + angleXComponent);
        livingEntity.setXRot(-angleYComponent);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternionf1.conjugate();
        entityRenderDispatcher.overrideCameraOrientation(quaternionf1);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack1, bufferSource, 15728880));
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        livingEntity.yBodyRot = f2;
        livingEntity.setYRot(f3);
        livingEntity.setXRot(f4);
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    private void renderSlots(PoseStack poseStack, int mouseX, int mouseY) {
        if (this.getMinecraft().player != null) {
            UUID uuid = this.getMinecraft().player.getUUID();
            Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
            User user = UserData.Client.getClientUser();

            List<MoaSkins.MoaSkin> visibleSkins = this.moaSkins.size() > this.maxSlots() ? this.moaSkins.subList(this.getSlotOffset(), this.getSlotOffset() + this.maxSlots()) : this.moaSkins;

            int slotIndex = 0;
            for (MoaSkins.MoaSkin skin : visibleSkins) {
                int x = this.leftPos + 7 + (slotIndex * 18);
                int y = (this.topPos + (this.imageHeight / 2)) + 9;

                if (user == null || !skin.getUserPredicate().test(user) || skin == this.selectedSkin || this.getSlotIndex(mouseX, mouseY) == slotIndex) {
                    int u = skin == this.selectedSkin || this.getSlotIndex(mouseX, mouseY) == slotIndex ? 18 : 0; // Highlighted slot vs. Darkened slot.
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
                    this.blit(poseStack, x, y, u, 191, 18, 18); // Render slot.
                }

                if (userSkinsData.containsKey(uuid) && userSkinsData.get(uuid).moaSkin() == skin) {
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
                    this.blit(poseStack, x, y, 36, 191, 18, 18); // Render golden slot outline.
                }

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, skin.getIconLocation());
                blit(poseStack, x + 1, y + 1, 0, 0, 16, 16, 16, 16); // Render Moa skin icon.

                slotIndex++;
            }
        }

        int scrollbarTop = (this.topPos + (this.imageHeight / 2)) + 29;
        int scrollbarLeft = this.leftPos + 8;
        int scrollbarU = this.moaSkins.size() > this.maxSlots() ? 0 : 13;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MOA_SKINS_GUI);
        this.blit(poseStack, (int) (scrollbarLeft + this.scrollX), scrollbarTop, scrollbarU, 209, 13, 6); // Render scrollbar.

        this.renderSlotTooltips(poseStack, mouseX, mouseY);
    }

    private void renderSlotTooltips(PoseStack poseStack, double mouseX, double mouseY) {
        MoaSkins.MoaSkin skin = this.getSkinFromSlot(mouseX, mouseY);
        if (skin != null) {
            Component name = skin.getDisplayName();
            this.renderTooltip(poseStack, name, (int) mouseX, (int) mouseY);
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0) {
            float scrollbarInsetLeft = this.leftPos + 7.0F;
            float scrollbarInsetTop = (this.topPos + (this.imageHeight / 2.0F)) + 29.0F;
            double mouseXDiff = mouseX - scrollbarInsetLeft;
            double mouseYDiff = mouseY - scrollbarInsetTop;
            if (((mouseYDiff <= 6 && mouseYDiff >= 0) || this.scrolling) && mouseXDiff <= 160 && mouseXDiff >= 0) {
                this.scrolling = true;
                this.scrollX = Math.max(0, Math.min((float) mouseXDiff - (this.scrollbarWidth() / 2.0F), this.scrollbarInsetWidth()));
                return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int i = 0;
        int index = this.getSlotOffset();
        if (index != -1) {
            i = index;
        }
        if (delta < 0) {
            i = Math.min(i + 1, this.snapPoints.size() - 1);
        } else if (delta > 0) {
            i = Math.max(i - 1, 0);
        }
        this.scrollX = this.snapPoints.get(i);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        MoaSkins.MoaSkin skin = this.getSkinFromSlot(mouseX, mouseY);
        if (skin != null) {
            this.selectedSkin = skin;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private MoaSkins.MoaSkin getSkinFromSlot(double mouseX, double mouseY) {
        int slot = this.getSlotIndex(mouseX, mouseY);
        if (slot != -1) {
            int trueSlot = slot + this.getSlotOffset();
            return this.moaSkins.get(trueSlot);
        }
        return null;
    }

    private int getSlotIndex(double mouseX, double mouseY) {
        int slotLeft = this.leftPos + 7;
        int slotTop = (this.topPos + (this.imageHeight / 2)) + 9;
        double mouseXDiff = mouseX - slotLeft;
        double mouseYDiff = mouseY - slotTop;
        return mouseYDiff <= 18 && mouseYDiff >= 0 && mouseXDiff <= 160 && mouseXDiff >= 0 ? (int) (mouseXDiff / 18) : -1;
    }

    private int getSlotOffset() { // Find the closest number in snapPoints to scrollX.
        int offset = 0;
        int index = this.snapPoints.indexOf(this.scrollX);
        if (index != -1) {
            offset = index;
        } else {
            for (int i = 0; i < this.snapPoints.size() - 1; i++) {
                float currentPoint = this.snapPoints.get(i);
                float nextPoint = this.snapPoints.get(i + 1);
                float midway = currentPoint + ((nextPoint - currentPoint) / 2.0F);
                if (this.scrollX > midway && this.scrollX < nextPoint) { // Closer to nextPoint.
                    offset = i + 1;
                } else if (this.scrollX <= midway && this.scrollX > currentPoint) { // Closer to currentPoint.
                    offset = i;
                }
            }
        }
        return offset;
    }

    private float scrollbarWidth() {
        return 13.0F;
    }

    private float scrollbarInsetWidth() {
        return 160.0F - this.scrollbarWidth();
    }

    private int maxSlots() {
        return 9;
    }

    @Override
    public void onClose() {
        this.getMinecraft().setScreen(this.lastScreen);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public MoaSkins.MoaSkin getSelectedSkin() {
        return this.selectedSkin;
    }

    public Moa getPreviewMoa() {
        return this.previewMoa;
    }
}
