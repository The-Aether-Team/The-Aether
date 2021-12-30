package com.gildedgames.aether.common.item.block;

import com.gildedgames.aether.common.entity.tile.SkyrootBedTileEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class SkyrootBedItem extends BlockItem {
	public <B extends Block> SkyrootBedItem(B block, Properties tab) {
		super(block, tab);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			BlockEntityWithoutLevelRenderer myRenderer;

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				if (Minecraft.getInstance().getEntityRenderDispatcher() != null && myRenderer == null) {
					myRenderer = new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
						private SkyrootBedTileEntity blockEntity;

						@Override
						public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
							if (blockEntity == null) {
								blockEntity = new SkyrootBedTileEntity(BlockPos.ZERO, AetherBlocks.SKYROOT_BED.get().defaultBlockState());
							}
							Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, matrix, buffer, x, y);
						}
					};
				}

				return myRenderer;
			}
		});
	}
}
