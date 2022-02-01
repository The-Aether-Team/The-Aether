package com.gildedgames.aether.client.renderer.blockentity;

import com.gildedgames.aether.common.item.block.EntityBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AetherBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    public AetherBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Item item = pStack.getItem();
        if (item instanceof EntityBlockItem blockItem && blockItem.getBlockEntity().isPresent()) {
            BlockEntity blockEntity = blockItem.getBlockEntity().orElseThrow(() -> new UnsupportedOperationException("BlockEntity was expected, but not supplied."));
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        } else {
            super.renderByItem(pStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
