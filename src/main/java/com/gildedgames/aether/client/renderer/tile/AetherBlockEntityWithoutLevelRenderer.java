package com.gildedgames.aether.client.renderer.tile;

import com.gildedgames.aether.common.entity.tile.SkyrootBedBlockEntity;
import com.gildedgames.aether.common.item.block.EntityBlockItem;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class AetherBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    //private final Supplier<ChestMimicTileEntity> chestMimic = () -> new ChestMimicTileEntity(BlockPos.ZERO, AetherBlocks.CHEST_MIMIC.get().defaultBlockState());
    //private final Supplier<TreasureChestTileEntity> treasureChest = () -> new TreasureChestTileEntity(BlockPos.ZERO, AetherBlocks.TREASURE_CHEST.get().defaultBlockState());
    private final Supplier<SkyrootBedBlockEntity> skyrootBed = () -> new SkyrootBedBlockEntity(BlockPos.ZERO, AetherBlocks.SKYROOT_BED.get().defaultBlockState());

    public AetherBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Item item = pStack.getItem();
        if (item instanceof EntityBlockItem blockItem) {
            Block block = blockItem.getBlock();
            BlockEntity blockEntity = null;
            if (block == AetherBlocks.SKYROOT_BED.get()) {
                blockEntity = this.skyrootBed.get();
            }
            if (blockEntity != null) {
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            }
        } else {
            super.renderByItem(pStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        }
    }
}
