package com.gildedgames.aether.client.renderer.entity;

import com.gildedgames.aether.common.entity.miscellaneous.ParachuteEntity;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ParachuteRenderer extends EntityRenderer<ParachuteEntity>
{
    private final Supplier<Block> parachuteBlock;

    public ParachuteRenderer(EntityRendererProvider.Context renderer, Supplier<Block> parachuteBlock) {
        super(renderer);
        this.parachuteBlock = parachuteBlock;
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(@Nonnull ParachuteEntity parachute, float entityYaw, float partialTicks, PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5, 0.0, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(this.parachuteBlock.get().defaultBlockState(), poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(parachute, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull ParachuteEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
