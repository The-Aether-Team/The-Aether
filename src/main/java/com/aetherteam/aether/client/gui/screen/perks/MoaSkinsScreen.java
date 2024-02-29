package com.aetherteam.aether.client.gui.screen.perks;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherMoaTypes;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.gui.component.skins.ChangeSkinButton;
import com.aetherteam.aether.client.gui.component.skins.PatreonButton;
import com.aetherteam.aether.client.gui.component.skins.RefreshButton;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.ServerMoaSkinPacket;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.aether.perk.types.MoaSkins;
import com.aetherteam.nitrogen.api.users.User;
import com.aetherteam.nitrogen.api.users.UserData;
import com.aetherteam.nitrogen.network.NitrogenPacketHandler;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.aetherteam.nitrogen.network.packet.serverbound.TriggerUpdateInfoPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MoaSkinsScreen extends Screen {
    public static final ResourceLocation MOA_SKINS_GUI = new ResourceLocation(Aether.MODID, "textures/gui/perks/skins/skins.png");
    private static final String PATREON_LINK = "https://www.patreon.com/TheAetherTeam";
    private static final String HELP_LINK = "https://github.com/The-Aether-Team/.github/wiki/Patreon-Guide";

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

    private boolean userConnectionExists = false;

    public MoaSkinsScreen(Screen lastScreen) {
        super(Component.translatable("gui.aether.moa_skins.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    public void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        // Store the user connection status at the initialization of this screen.
        User user = UserData.Client.getClientUser();
        this.userConnectionExists = user != null;

        // List of registered Moa Skins.
        this.moaSkins = List.copyOf(MoaSkins.getMoaSkins().values());

        // A list of x-positions that the scrollbar element can snap to when moving it with the mouse's scroll wheel.
        this.snapPoints = new ArrayList<>();
        // Can only snap up to the amount of moa skins subtracted from the amount of slots to display at a time.
        // This is because that will determine how many remaining slots there are that could be scrolled to.
        // If there are 14 skins and 9 slots per page, then there are only 5 more slots to view that can be scrolled to.
        // So the snap points will be divided by 5 from the start of the gutter to the end in that instance.
        int remainingSlots = this.moaSkins.size() - this.maxSlots();
        for (int i = 0; i <= remainingSlots; i++) {
            this.snapPoints.add((this.scrollbarGutterWidth() / remainingSlots) * i);
        }

        if (this.getMinecraft().player != null) {
            // Retrieve Moa Skin data for this user.
            UUID uuid = this.getMinecraft().player.getUUID();
            Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();

            // If the user has no selected skin in this screen, set it to whatever their saved skin is according to the server data.
            if (this.getSelectedSkin() == null) {
                this.selectedSkin = userSkinsData.containsKey(uuid) ? userSkinsData.get(uuid).moaSkin() : this.moaSkins.get(0);
            }

            // Button for saving a selected skin as the one that will be applied to the player's Moa.
            this.applyButton = this.addRenderableWidget(new ChangeSkinButton(ChangeSkinButton.ButtonType.APPLY, Button.builder(Component.translatable("gui.aether.moa_skins.button.apply"),
                (pressed) -> AetherPlayer.get(this.getMinecraft().player).ifPresent((aetherPlayer) -> {
                    PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Apply(this.getMinecraft().player.getUUID(), new MoaData(aetherPlayer.getLastRiddenMoa(), this.getSelectedSkin())));
                    this.customizations.setMoaSkin(this.getSelectedSkin().getId());
                    this.customizations.save();
                    this.customizations.load();
                })
            ).bounds((this.leftPos + this.imageWidth) - 20, this.topPos + 13, 7, 7)));

            // Button for removing the player's currently applied Moa Skin.
            this.removeButton = this.addRenderableWidget(new ChangeSkinButton(ChangeSkinButton.ButtonType.REMOVE, Button.builder(Component.translatable("gui.aether.moa_skins.button.remove"),
                (pressed) -> {
                    PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Remove(this.getMinecraft().player.getUUID()));
                    this.customizations.setMoaSkin("");
                    this.customizations.save();
                    this.customizations.load();
                }
            ).bounds((this.leftPos + this.imageWidth) - 20, this.topPos + 22, 7, 7)));

            // Button that forces the server to re-check the status of the player's user info and Patreon connection.
            this.addRenderableWidget(new RefreshButton(Button.builder(Component.literal(""),
                    (pressed) -> {
                        if (RefreshButton.reboundTimer == 0) {
                            PacketRelay.sendToServer(NitrogenPacketHandler.INSTANCE, new TriggerUpdateInfoPacket(this.getMinecraft().player.getId()));
                            RefreshButton.reboundTimer = RefreshButton.reboundMax;
                        }
                    }
            ).bounds(this.leftPos + 7, this.topPos + this.imageHeight - 25, 18, 18).tooltip(Tooltip.create(Component.translatable("gui.aether.moa_skins.button.refresh")))));

            // Button that opens a screen with a redirect to Patreon.
            this.addRenderableWidget(new PatreonButton(Button.builder(Component.translatable("gui.aether.moa_skins.button.donate"),
                (pressed) -> this.getMinecraft().setScreen(new ConfirmLinkScreen((callback) -> {
                    if (callback) {
                        Util.getPlatform().openUri(PATREON_LINK);
                    }
                    this.getMinecraft().setScreen(this);
                }, PATREON_LINK, true))
            ).bounds(this.leftPos + (this.imageWidth / 2) - (54 / 2), this.topPos + this.imageHeight - 25, 54, 18)));

            // Button that opens a screen with a redirect to a guide for how to connect a UUID.
            this.addRenderableWidget(new PatreonButton(Button.builder(Component.translatable("?"),
                    (pressed) -> this.getMinecraft().setScreen(new ConfirmLinkScreen((callback) -> {
                        if (callback) {
                            Util.getPlatform().openUri(HELP_LINK);
                        }
                        this.getMinecraft().setScreen(this);
                    }, HELP_LINK, true))
            ).bounds(this.leftPos + (this.imageWidth / 2) + 63, this.topPos + this.imageHeight - 25, 18, 18).tooltip(Tooltip.create(Component.translatable("gui.aether.moa_skins.button.help"))), true));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.checkUserConnectionStatus();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderWindow(guiGraphics);
        this.renderSlots(guiGraphics, mouseX, mouseY);
        this.renderInterface(guiGraphics, mouseX, mouseY, partialTicks);
    }

    /**
     * Displays the main window GUI. Depending on the player's donation status, the text
     * "Donate to the project to get Moa Skins!" or "Thank you for donating to the project!"
     * will also be displayed.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     */
    private void renderWindow(GuiGraphics guiGraphics) {
        User user = UserData.Client.getClientUser();
        Font font = this.getMinecraft().font;
        guiGraphics.blit(MOA_SKINS_GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        Component component = user == null ? Component.translatable("gui.aether.moa_skins.text.donate") : Component.translatable("gui.aether.moa_skins.text.reward");
        int y = (this.topPos + this.imageHeight - 69) + font.wordWrapHeight(component, this.imageWidth - 20);
        for (FormattedCharSequence sequence : font.split(component, this.imageWidth - 20)) {
            guiGraphics.drawCenteredString(font, sequence, this.leftPos + (this.imageWidth / 2), y, 16777215);
            y += 12;
        }
    }

    /**
     * Renders the slots for selecting different Moa Skins from.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     */
    private void renderSlots(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.getMinecraft().player != null) {
            UUID uuid = this.getMinecraft().player.getUUID();
            Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
            User user = UserData.Client.getClientUser();

            // The list of currently visible slots within the limit of 9 slots per section.
            List<MoaSkins.MoaSkin> visibleSkins = this.moaSkins.size() > this.maxSlots() ? this.moaSkins.subList(this.getSlotOffset(), this.getSlotOffset() + this.maxSlots()) : this.moaSkins;

            // Renders a slot for each skin.
            int slotIndex = 0;
            for (MoaSkins.MoaSkin skin : visibleSkins) {
                int x = this.leftPos + 7 + (slotIndex * 18);
                int y = (this.topPos + (this.imageHeight / 2)) + 9;

                // Either renders a slot overlay for being highlighted blue or darkened grey.
                // This depends on whether the skin is selected in which case it will be highlighted.
                // If a skin slot is not selected, then it will display as darkened only if the user does not have access to that skin.
                if (user == null || !skin.getUserPredicate().test(user) || skin == this.getSelectedSkin() || this.getSlotIndex(mouseX, mouseY) == slotIndex) {
                    int u = skin == this.getSelectedSkin() || this.getSlotIndex(mouseX, mouseY) == slotIndex ? 18 : 0; // Highlighted slot vs. Darkened slot.
                    guiGraphics.blit(MOA_SKINS_GUI, x, y, u, 191, 18, 18); // Render slot.
                }

                // Renders an outline for the player's currently active Moa Skin.
                if (userSkinsData.containsKey(uuid) && userSkinsData.get(uuid).moaSkin() == skin) {
                    guiGraphics.blit(MOA_SKINS_GUI, x, y, 36, 191, 18, 18); // Render golden slot outline.
                }

                guiGraphics.blit(skin.getIconLocation(), x + 1, y + 1, 0, 0, 16, 16, 16, 16); // Render Moa skin icon.

                slotIndex++;
            }
        }
        this.renderScrollbar(guiGraphics);
        this.renderSlotTooltips(guiGraphics, mouseX, mouseY);
    }

    /**
     * Renders the scrollbar based on the leftmost position for it and the current x-offset as determined by {@link MoaSkinsScreen#scrollX}.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     */
    private void renderScrollbar(GuiGraphics guiGraphics) {
        int scrollbarTop = (this.topPos + (this.imageHeight / 2)) + 29;
        int scrollbarLeft = this.leftPos + 8;
        int scrollbarU = this.moaSkins.size() > this.maxSlots() ? 0 : 13;

        guiGraphics.blit(MOA_SKINS_GUI, (int) (scrollbarLeft + this.scrollX), scrollbarTop, scrollbarU, 209, 13, 6); // Render scrollbar.
    }

    /**
     * Using {@link MoaSkinsScreen#getSkinFromSlot(double, double)}, this checks if the mouse is currently hovered over a Moa Skin slot,
     * and if so, then it will display a tooltip with the name of the Moa Skin.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     */
    private void renderSlotTooltips(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        MoaSkins.MoaSkin skin = this.getSkinFromSlot(mouseX, mouseY);
        if (skin != null) {
            Component name = skin.getDisplayName();
            guiGraphics.renderTooltip(this.getMinecraft().font, name, (int) mouseX, (int) mouseY);
        }
    }

    /**
     * Renders elements of the interface over the black section of the GUI.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    private void renderInterface(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        User user = UserData.Client.getClientUser();
        if (user != null && this.getSelectedSkin().getUserPredicate().test(user)) { // If the player has access to the selected skin.
            this.applyButton.active = true;
            this.removeButton.active = true;

            if (this.getSelectedSkin().getInfo().lifetime() || user.getCurrentTier() == null || user.getCurrentTierLevel() < this.getSelectedSkin().getInfo().tier().getLevel()) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 8);
                this.renderLifetimeIcon(guiGraphics, mouseOver);
                if (mouseOver) { // Display a tooltip saying that the player has lifetime access to the skin.
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.lifetime"),
                            Component.translatable("gui.aether.moa_skins.tooltip.lifetime"), guiGraphics, mouseX, mouseY);
                }
            } else if (user.getCurrentTier() != null) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 7);
                this.renderPledgingIcon(guiGraphics, mouseOver);
                if (mouseOver) { // Display a tooltip saying that the player has access to the skin when pledging to the specified tier.
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.pledging"),
                            Component.translatable("gui.aether.moa_skins.tooltip.pledging", user.getCurrentTier().getDisplayName()), guiGraphics, mouseX, mouseY);
                }
            }
        } else { // If the player does not have access to the selected skin.
            this.applyButton.active = false;
            this.removeButton.active = false;

            guiGraphics.blit(MOA_SKINS_GUI, this.leftPos + 13, this.topPos + 13, 54, 191, 10, 14); // Lock Icon

            if (this.getSelectedSkin().getInfo().lifetime()) {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 8);
                this.renderLifetimeIcon(guiGraphics, mouseOver);
                if (mouseOver) { // Display a tooltip saying that the skin comes with lifetime access when pledging to the given tier.
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.lifetime"),
                            Component.translatable("gui.aether.moa_skins.tooltip.access.lifetime", this.getSelectedSkin().getInfo().tier().getDisplayName()), guiGraphics, mouseX, mouseY);
                }
            } else {
                boolean mouseOver = this.isMouseOverIcon(mouseX, mouseY, 7);
                this.renderPledgingIcon(guiGraphics, mouseOver);
                if (mouseOver) { // Display a tooltip saying that the skin comes with access only when pledging to the given tier.
                    this.renderTooltip(Component.translatable("gui.aether.moa_skins.tooltip.title.access.pledging"),
                            Component.translatable("gui.aether.moa_skins.tooltip.access.pledging", this.getSelectedSkin().getInfo().tier().getDisplayName()), guiGraphics, mouseX, mouseY);
                }
            }
        }
        this.renderMoa(guiGraphics, partialTicks); // Renders the spinning Moa with the selected skin.
        guiGraphics.drawCenteredString(this.getMinecraft().font, this.getSelectedSkin().getDisplayName(), this.leftPos + (this.imageWidth / 2), this.topPos + 12, 16777215); // Skin Name
        guiGraphics.drawCenteredString(this.getMinecraft().font, this.getTitle(), this.leftPos + (this.imageWidth / 2), this.topPos - 15, 16777215); // Title
    }

    /**
     * Displays an infinity sign icon in the bottom left corner of the black GUI interface.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseOver Whether the mouse is hovering over this icon, as a {@link Boolean}.
     */
    private void renderLifetimeIcon(GuiGraphics guiGraphics, boolean mouseOver) {
        guiGraphics.blit(MOA_SKINS_GUI, this.leftPos + 13, (this.topPos + (this.imageHeight / 2)) - 9, mouseOver ? 63 : 55, 184, 8, 7); // Lifetime Icon
    }

    /**
     * Displays an hourglass icon in the bottom left corner of the black GUI interface.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseOver Whether the mouse is hovering over this icon, as a {@link Boolean}.
     */
    private void renderPledgingIcon(GuiGraphics guiGraphics, boolean mouseOver) {
        guiGraphics.blit(MOA_SKINS_GUI, this.leftPos + 13, (this.topPos + (this.imageHeight / 2)) - 9, mouseOver ? 49 : 42, 184, 7, 7);
    }

    private boolean isMouseOverIcon(int mouseX, int mouseY, int width) {
        int leftPos = this.leftPos + 13;
        int topPos = (this.topPos + (this.imageHeight / 2)) - 9;
        double mouseXDiff = mouseX - leftPos;
        double mouseYDiff = mouseY - topPos;
        return mouseYDiff <= 7 && mouseYDiff >= 0 && mouseXDiff <= width && mouseXDiff >= 0;
    }

    /**
     * Displays a formatted tooltip with a title and a description.
     * @param title The title {@link MutableComponent} for the tooltip.
     * @param description The description {@link Component} for the tooltip.
     * @param guiGraphics The rendering {@link GuiGraphics}.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     */
    private void renderTooltip(MutableComponent title, Component description, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        List<FormattedText> formattedTextList = new ArrayList<>();
        formattedTextList.add(title.withStyle(ChatFormatting.GOLD));
        formattedTextList.addAll(this.getMinecraft().font.getSplitter().splitLines(description, this.width / 3, Style.EMPTY));
        guiGraphics.renderComponentTooltip(this.getMinecraft().font, formattedTextList, mouseX, mouseY, ItemStack.EMPTY);
    }

    /**
     * Sets up a Moa entity for rendering in the GUI.
     * @param partialTicks The {@link Float} for the game's partial ticks.
     */
    private void renderMoa(GuiGraphics guiGraphics, float partialTicks) {
        if (this.getMinecraft().level != null) {
            if (this.getPreviewMoa() == null) { // Set up preview Moa if it doesn't exist.
                Moa moa = AetherEntityTypes.MOA.get().create(this.getMinecraft().level);
                if (moa != null) {
                    moa.generateMoaUUID();
                    moa.setMoaType(AetherMoaTypes.BLUE.get());
                    moa.setSaddled(true);
                    this.previewMoa = moa;
                }
            } else { // Render and rotate preview Moa once it does exist.
                this.moaRotation = Mth.wrapDegrees(Mth.lerp(partialTicks, this.moaRotation, this.moaRotation + 2.5F));
                int startX = this.leftPos + (this.imageWidth / 2);
                int startY = this.topPos + (this.imageHeight / 2);
                renderRotatingEntity(guiGraphics, startX - 100, startY - 135, startX + 100, startY + 65, 27, 0.1F, this.moaRotation, -20.0F, this.getPreviewMoa());

            }
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.gui.screens.inventory.InventoryScreen#renderEntityInInventoryFollowsAngle(GuiGraphics, int, int, int, int, int, float, float, float, LivingEntity)} (GuiGraphics, int, int, int, float, float, LivingEntity)}.<br><br>
     * Code Modified so that the head rotation follows the body rotation and doesn't rotate separately.<br><br>
     */
    public static void renderRotatingEntity(GuiGraphics guiGraphics, int startX, int startY, int endX, int endY, int scale, float yOffset, float angleXComponent, float angleYComponent, LivingEntity livingEntity) {
        float posX = (float) (startX + endX) / 2.0F;
        float posY = (float) (startY + endY) / 2.0F;
        guiGraphics.enableScissor(startX, startY, endX, endY);
        Quaternionf xQuaternion = new Quaternionf().rotateZ(Mth.PI);
        Quaternionf zQuaternion = new Quaternionf().rotateX(angleYComponent * Mth.DEG_TO_RAD);
        xQuaternion.mul(zQuaternion);
        float yBodyRot = livingEntity.yBodyRot;
        float yRot = livingEntity.getYRot();
        float xRot = livingEntity.getXRot();
        livingEntity.setYBodyRot(180.0F + angleXComponent);
        livingEntity.setYRot(180.0F + angleXComponent);
        livingEntity.setXRot(-angleYComponent);
        livingEntity.setYHeadRot(livingEntity.getYRot());
        livingEntity.yHeadRotO = livingEntity.getYRot();
        Vector3f vector3f = new Vector3f(0.0F, livingEntity.getBbHeight() / 2.0F + yOffset, 0.0F);

        InventoryScreen.renderEntityInInventory(guiGraphics, posX, posY, scale, vector3f, xQuaternion, zQuaternion, livingEntity);
        livingEntity.setYBodyRot(yBodyRot);
        livingEntity.setYRot(yRot);
        livingEntity.setXRot(xRot);
        guiGraphics.disableScissor();
    }

    /**
     * Checks if the user's connection status has changed after they first opened the screen.
     */
    private void checkUserConnectionStatus() {
        User user = UserData.Client.getClientUser();
        if (this.getMinecraft().player != null) {
            if (user == null && this.userConnectionExists) { // Remove skin data if the user no longer exists.
                PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Remove(this.getMinecraft().player.getUUID()));
                this.userConnectionExists = false;
            } else if (user != null && !this.userConnectionExists && MoaSkins.getMoaSkins().get(this.customizations.getMoaSkin()) != null) { // Add skin data if the user has started existing.
                AetherPlayer.get(this.getMinecraft().player).ifPresent((aetherPlayer) ->
                        PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new ServerMoaSkinPacket.Apply(this.getMinecraft().player.getUUID(), new MoaData(aetherPlayer.getLastRiddenMoa(), MoaSkins.getMoaSkins().get(this.customizations.getMoaSkin())))));
                this.userConnectionExists = true;
            }
        }
    }

    /**
     * Handles dragging the scrollbar with the mouse.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @param button The {@link Integer} for the clicked mouse button ID.
     * @param dragX The {@link Double} for the drag amount in the x-direction.
     * @param dragY The {@link Double} for the drag amount in the y-direction.
     * @return Whether the mouse can drag, as a {@link Boolean}.
     */
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0) {
            float scrollbarGutterLeft = this.leftPos + 7.0F;
            float scrollbarGutterTop = (this.topPos + (this.imageHeight / 2.0F)) + 29.0F;
            double mouseXDiff = mouseX - scrollbarGutterLeft;
            double mouseYDiff = mouseY - scrollbarGutterTop;
            if (((mouseYDiff <= 6 && mouseYDiff >= 0) || this.scrolling) && mouseXDiff <= 160 && mouseXDiff >= 0) {
                this.scrolling = true; // Set the scrollbar as currently scrolling.
                this.scrollX = Math.max(0, Math.min((float) mouseXDiff - (this.scrollbarWidth() / 2.0F), this.scrollbarGutterWidth())); // Set the offset for where to render the scrollbar.
                return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    /**
     * Handles moving the scrollbar to snapping points when using the mouse's scroll wheel.
     * @param mouseX The {@link Double} for the mouse's x-position.
     * @param mouseY The {@link Double} for the mouse's y-position.
     * @param scrollX The {@link Double} for the mouse's x-scroll.
     * @param scrollY The {@link Double} for the mouse's y-scroll.
     * @return Whether the mouse can scroll, as a {@link Boolean}.
     */


    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int i = 0;
        int index = this.getSlotOffset();
        if (index != -1) {
            i = index;
        }
        if (scrollY < 0) { // Scroll to the left.
            i = Math.min(i + 1, this.snapPoints.size() - 1);
        } else if (scrollY > 0) { // Scroll to the right.
            i = Math.max(i - 1, 0);
        }
        this.scrollX = this.snapPoints.get(i); // Set the scrollbar offset to a specified snapping point position.
        return true;
    }

    /**
     * Handles clicking on slots in the GUI and changing the selected skin based on what slot was clicked.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @param button The {@link Integer} for the clicked mouse button ID.
     * @return Whether the mouse can click, as a {@link Boolean}.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        MoaSkins.MoaSkin skin = this.getSkinFromSlot(mouseX, mouseY);
        if (skin != null) {
            this.selectedSkin = skin;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Gets a skin from the slot that the mouse is currently hovered over.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @return The {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin} from the corresponding slot.
     */
    @Nullable
    private MoaSkins.MoaSkin getSkinFromSlot(double mouseX, double mouseY) {
        int slot = this.getSlotIndex(mouseX, mouseY);
        if (slot != -1) {
            int trueSlot = slot + this.getSlotOffset(); // Determines the true index to get from the list of Moa Skins, if there is a slot offset from scrolling.
            return this.moaSkins.get(trueSlot);
        }
        return null;
    }

    /**
     * Gets the index from 0-8 for the skin selection slot that is currently hovered over.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @return The {@link Integer} index.
     */
    private int getSlotIndex(double mouseX, double mouseY) {
        int slotLeft = this.leftPos + 7;
        int slotTop = (this.topPos + (this.imageHeight / 2)) + 9;
        double mouseXDiff = mouseX - slotLeft;
        double mouseYDiff = mouseY - slotTop;
        return mouseYDiff <= 18 && mouseYDiff >= 0 && mouseXDiff <= 160 && mouseXDiff >= 0 ? (int) (mouseXDiff / 18) : -1;
    }

    // Gets the current offset of how many slots have been passed based on how far the scrollbar is relative to snap points.
    // Find the closest number in snapPoints to scrollX.

    /**
     * Using {@link MoaSkinsScreen#snapPoints}, this determines how many slots after the starting list to pass by moving the scrollbar,
     * by checking how close the scrollbar is to different snapping points. The snapping points act as position boundaries for the scrollbar
     * that determine when to shift the slot list over to display a new slot.
     * @return The {@link Integer} for the offset amount of slots.
     */
    private int getSlotOffset() {
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

    /**
     * Sets the scrollbar to no longer be scrollable when mouse buttons have been released.
     * @param mouseX The {@link Integer} for the mouse's x-position.
     * @param mouseY The {@link Integer} for the mouse's y-position.
     * @param button The {@link Integer} for the clicked mouse button ID.
     * @return Whether the mouse can be released, as a {@link Boolean}.
     */
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    /**
     * @return The {@link Float} width of the scrollbar element.
     */
    private float scrollbarWidth() {
        return 13.0F;
    }

    /**
     * @return The {@link Float} width of the scrollbar gutter, not including the area of the scrollbar.
     * This is because the scrollbar position when at one side is not at "0" but actually 13.
     */
    private float scrollbarGutterWidth() {
        return 160.0F - this.scrollbarWidth();
    }

    /**
     * @return The {@link Integer} for the maximum amount of skin selection slots to display on a page.
     */
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

    /**
     * @return The {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin} that is currently selected from clicking.
     * This is not the same as the player's active Moa Skin.
     */
    public MoaSkins.MoaSkin getSelectedSkin() {
        return this.selectedSkin;
    }

    /**
     * @return The {@link Moa} entity used for the entity render in the screen.
     */
    public Moa getPreviewMoa() {
        return this.previewMoa;
    }
}
