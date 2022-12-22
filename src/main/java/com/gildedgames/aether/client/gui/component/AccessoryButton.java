package com.gildedgames.aether.client.gui.component;

import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.OpenAccessoriesPacket;
import com.gildedgames.aether.network.packet.server.OpenInventoryPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class AccessoryButton extends ImageButton
{
    private final AbstractContainerScreen<?> parentGui;

    public AccessoryButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, ResourceLocation resource) {
        super(xIn, yIn, 12, 8, 0, 0, 8, resource, 12, 16,
                (button) -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    Player player = minecraft.player;
                    if (player != null) {
                        ItemStack stack = player.containerMenu.getCarried();
                        player.containerMenu.setCarried(ItemStack.EMPTY);

                        if (parentGui instanceof AccessoriesScreen) {
                            InventoryScreen inventory = new InventoryScreen(player);
                            minecraft.setScreen(inventory);
                            player.inventoryMenu.setCarried(stack);
                            AetherPacketHandler.sendToServer(new OpenInventoryPacket(stack));
                        } else {
                            AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(stack));
                        }
                    }
                });
        this.parentGui = parentGui;
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(this.parentGui);
        this.setX(this.parentGui.getGuiLeft() + offsets.getA());
        this.setY(this.parentGui.getGuiTop() + offsets.getB());
        if (this.parentGui instanceof CreativeModeInventoryScreen screen) {
            boolean isInventoryTab = screen.isInventoryOpen();
            this.active = isInventoryTab;
            if (isInventoryTab) {
                super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        } else if (this.parentGui instanceof AccessoriesScreen screen) {
            if (screen.getMenu().hasButton) {
                super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        } else {
            super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
    }
}
