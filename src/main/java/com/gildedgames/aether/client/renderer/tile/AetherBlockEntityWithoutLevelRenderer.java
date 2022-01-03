package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.common.block.utility.SkyrootBedBlock;
import com.gildedgames.aether.common.entity.tile.SkyrootBedBlockEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AetherBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    private final SkyrootBedBlockEntity skyrootBedEntity = new SkyrootBedBlockEntity(BlockPos.ZERO, AetherBlocks.SKYROOT_BED.get().defaultBlockState());

    public AetherBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        Item item = pStack.getItem();
        if (item instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof SkyrootBedBlock) {
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(skyrootBedEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            }
        } else {
            super.renderByItem(pStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
