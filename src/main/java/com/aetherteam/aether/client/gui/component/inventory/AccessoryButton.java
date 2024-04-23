package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.OpenAccessoriesPacket;
import com.aetherteam.aether.network.packet.serverbound.OpenInventoryPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * [CODE COPY] - {@link top.theillusivec4.curios.client.gui.CuriosButton}.<br><br>
 * Opens the {@link AccessoriesScreen} instead.
 */
public class AccessoryButton extends ImageButton {
    private final AbstractContainerScreen<?> parentScreen;

    public AccessoryButton(AbstractContainerScreen<?> parentScreen, int x, int y, ResourceLocation texture) {
        super(x, y, 12, 8, 0, 0, 8, texture, 12, 16,
                (button) -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    Player player = minecraft.player;
                    if (player != null) {
                        ItemStack stack = player.containerMenu.getCarried();
                        player.containerMenu.setCarried(ItemStack.EMPTY);

                        if (parentScreen instanceof AccessoriesScreen) {
                            InventoryScreen inventory = new InventoryScreen(player);
                            minecraft.setScreen(inventory);
                            player.inventoryMenu.setCarried(stack);
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new OpenInventoryPacket(stack));
                        } else {
                            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new OpenAccessoriesPacket(stack));
                        }
                    }
                });
        this.parentScreen = parentScreen;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
//        Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(this.parentScreen); TODO: PORT
//        this.setX(this.parentScreen.getGuiLeft() + offsets.getA());
//        this.setY(this.parentScreen.getGuiTop() + offsets.getB());
        if (this.parentScreen instanceof CreativeModeInventoryScreen screen) {
            boolean isInventoryTab = screen.isInventoryOpen();
            this.active = isInventoryTab;
            if (isInventoryTab) {
                super.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        } else if (this.parentScreen instanceof AccessoriesScreen screen) {
            if (screen.getMenu().hasButton) {
                super.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
        }
    }
}
