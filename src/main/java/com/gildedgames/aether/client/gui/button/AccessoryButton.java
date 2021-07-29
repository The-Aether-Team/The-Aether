package com.gildedgames.aether.client.gui.button;

import com.gildedgames.aether.client.gui.screen.inventory.AccessoriesScreen;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.OpenAccessoriesPacket;
import com.gildedgames.aether.core.network.packet.server.OpenInventoryPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AccessoryButton extends ImageButton
{
    private final ContainerScreen<?> parentGui;

    public AccessoryButton(ContainerScreen<?> parentGui, int xIn, int yIn, ResourceLocation resource) {
        super(xIn, yIn, 12, 8, 0, 0, 8, resource, 12, 16,
                (button) -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    PlayerEntity player = minecraft.player;
                    if (player != null) {
                        if (parentGui instanceof AccessoriesScreen) {
                            InventoryScreen inventory = new InventoryScreen(player);
                            ItemStack stack = player.inventory.getCarried();
                            player.inventory.setCarried(ItemStack.EMPTY);
                            minecraft.setScreen(inventory);
                            player.inventory.setCarried(stack);
                            AetherPacketHandler.sendToServer(new OpenInventoryPacket(player.getId()));
                        } else {
                            AetherPacketHandler.sendToServer(new OpenAccessoriesPacket(player.getId()));
                        }
                    }
                });
        this.parentGui = parentGui;
    }

    @Override
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        if (this.parentGui instanceof CreativeScreen) {
            CreativeScreen screen = (CreativeScreen) this.parentGui;
            if (screen.getSelectedTab() == ItemGroup.TAB_INVENTORY.getId()) {
                super.render(matrixStack, p_230430_2_, p_230430_3_, p_230430_4_);
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
