package com.aetherteam.aether.client.renderer.blockentity;

import com.aetherteam.aether.item.block.EntityBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AetherBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer
{
    public AetherBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = stack.getItem();
        if (item instanceof EntityBlockItem blockItem && blockItem.getBlockEntity().isPresent()) {
            BlockEntity blockEntity = blockItem.getBlockEntity().orElseThrow(() -> new UnsupportedOperationException("BlockEntity was expected, but not supplied."));
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, poseStack, buffer, packedLight, packedOverlay);
        } else {
            super.renderByItem(stack, context, poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
