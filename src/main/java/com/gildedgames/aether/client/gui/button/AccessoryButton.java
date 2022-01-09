package com.gildedgames.aether.client.gui.button;

import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.OpenAccessoriesPacket;
import com.gildedgames.aether.core.network.packet.server.OpenInventoryPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class AccessoryButton extends ImageButton
{
    private final AbstractContainerScreen<?> parentGui;

    public AccessoryButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, ResourceLocation resource) {
        super(xIn, yIn, 12, 8, 0, 0, 8, resource, 12, 16,
                (button) -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    Player player = minecraft.player;
                    if (player != null) {
                        if (parentGui instanceof AccessoriesScreen) {
                            InventoryScreen inventory = new InventoryScreen(player);
                            ItemStack stack = player.inventoryMenu.getCarried();
                            player.inventoryMenu.setCarried(ItemStack.EMPTY);
                            minecraft.setScreen(inventory);
                            player.inventoryMenu.setCarried(stack);
                            AetherPacketHandler.sendToServer(new OpenInventoryPacket(player.getId()));
                        } else {
                            AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(player.getId()));
                        }
                    }
                });
        this.parentGui = parentGui;
    }

    @Override
    public void render(PoseStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        if (this.parentGui instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) this.parentGui;
            if (screen.getSelectedTab() == CreativeModeTab.TAB_INVENTORY.getId()) {
                this.active = true;
                this.visible = true;
                super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
            } else {
                this.active = false;
                this.visible = false;
            }
        } else if (this.parentGui instanceof AccessoriesScreen) {
            AccessoriesScreen screen = (AccessoriesScreen) this.parentGui;
            if (screen.getMenu().hasButton) {
                super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
            }
        } else {
            super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
        }
    }
}
