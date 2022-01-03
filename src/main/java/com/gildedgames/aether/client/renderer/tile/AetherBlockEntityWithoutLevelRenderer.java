package com.gildedgames.aether.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AetherBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private final BlockEntity blockEntity;

    public AetherBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet, BlockEntity blockEntity) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.blockEntity = blockEntity;
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        Item item = pStack.getItem();
        if (item instanceof BlockItem blockItem) {
            if (blockItem.getBlock() == this.blockEntity.getBlockState().getBlock()) {
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(this.blockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            }
        } else {
            super.renderByItem(pStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
