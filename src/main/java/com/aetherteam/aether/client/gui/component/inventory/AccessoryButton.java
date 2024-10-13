package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.client.gui.screen.inventory.AetherAccessoriesScreen;
import com.aetherteam.aether.network.packet.serverbound.OpenAccessoriesPacket;
import com.aetherteam.aether.network.packet.serverbound.OpenInventoryPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.client.gui.CuriosButton}.<br><br>
 * Opens the {@link AetherAccessoriesScreen} instead.
 */
public class AccessoryButton extends ImageButton {
    private final AbstractContainerScreen<?> parentScreen;

    public AccessoryButton(AbstractContainerScreen<?> parentScreen, int x, int y, WidgetSprites sprites) {
        super(x, y, 12, 8, sprites,
                (button) -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    Player player = minecraft.player;
                    if (player != null) {
                        ItemStack stack = player.containerMenu.getCarried();
                        player.containerMenu.setCarried(ItemStack.EMPTY);

                        if (parentScreen instanceof AetherAccessoriesScreen) {
                            InventoryScreen inventory = new InventoryScreen(player);
                            minecraft.setScreen(inventory);
                            player.inventoryMenu.setCarried(stack);
                            PacketDistributor.sendToServer(new OpenInventoryPacket(stack));
                        } else {
                            PacketDistributor.sendToServer(new OpenAccessoriesPacket(stack));
                        }
                    }
                });
        this.parentScreen = parentScreen;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Tuple<Integer, Integer> offsets = AetherAccessoriesScreen.getButtonOffset(this.parentScreen);
        this.setX(this.parentScreen.getGuiLeft() + offsets.getA());
        this.setY(this.parentScreen.getGuiTop() + offsets.getB());
        if (this.parentScreen instanceof CreativeModeInventoryScreen screen) {
            boolean isInventoryTab = screen.isInventoryOpen();
            this.active = isInventoryTab;
            if (isInventoryTab) {
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
            }
        } else if (this.parentScreen instanceof AetherAccessoriesScreen screen) {
            if (screen.getMenu().hasButton) {
                super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
            }
        } else {
            super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
        }
    }
}
